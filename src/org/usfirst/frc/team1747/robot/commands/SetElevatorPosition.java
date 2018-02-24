package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

//import com.frc1747.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.controller.Logitech;
import lib.frc1747.motion_profile.Parameters;
//import lib.frc1747.instrumentation.Instrumentation;
//import lib.frc1747.instrumentation.Logger;
import lib.frc1747.subsytems.HBRSubsystem;

/**
 *
 */
public class SetElevatorPosition extends Command {
	private ElevatorSubsystem elevator;
	
	double elevatorSetpoint;
	double distance;
	ElevatorSubsystem.ElevatorPositions stage;

	public final double position;
	
    public SetElevatorPosition(ElevatorSubsystem.ElevatorPositions stage) {
    	requires(elevator = ElevatorSubsystem.getInstance());
    	position = elevator.getElevatorStages()[stage.ordinal()];
    	setInterruptible(false);
    	this.stage = stage;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double currentPosition = elevator.getElevatorPosition();
    	distance = position - currentPosition;
    	double[][][] profiles = HBRSubsystem.generateSkidSteerPseudoProfile(distance, 0, Parameters.I_SAMPLE_LENGTH * 12, 120, 150, 9000.1, Parameters.W_WIDTH, Parameters.DT, true, true);
    	for(int i = 0; i < profiles[0].length; i++){
    		profiles[0][i][0] += currentPosition;
    	}
    	
    	//setup elevator PID
    	elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.FOLLOWER);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.PIDMode.POSITION);
    	elevator.setILimit(ElevatorSubsystem.Follower.ELEVATOR, 0);
    	elevator.setFeedforward(ElevatorSubsystem.Follower.ELEVATOR, 0, GambeziDashboard.get_double("Elevator/kV"), GambeziDashboard.get_double("Elevator/kA"));
    	elevator.setFeedback(ElevatorSubsystem.Follower.ELEVATOR, GambeziDashboard.get_double("Elevator/kP"), GambeziDashboard.get_double("Elevator/kI"), GambeziDashboard.get_double("Elevator/kD"));
    	elevator.resetIntegrator(ElevatorSubsystem.Follower.ELEVATOR);
    	elevator.setProfile(ElevatorSubsystem.Follower.ELEVATOR, profiles[0]);
    	
    	//setup wrist PID
    	elevator.setMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.Mode.PID);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.PIDMode.POSITION);
    	elevator.setILimit(ElevatorSubsystem.Follower.WRIST, 0);
    	elevator.setFeedforward(ElevatorSubsystem.Follower.WRIST, 0, GambeziDashboard.get_double("Wrist/kV"), GambeziDashboard.get_double("Wrist/kA"));
    	elevator.setFeedback(ElevatorSubsystem.Follower.WRIST, GambeziDashboard.get_double("Wrist/kP"), GambeziDashboard.get_double("Wrist/kI"), GambeziDashboard.get_double("Wrist/kD"));
		elevator.resetIntegrator(ElevatorSubsystem.Follower.WRIST);

    	elevator.resume(ElevatorSubsystem.Follower.ELEVATOR);
		elevator.setEnabled(true);
			
		elevator.setElevatorStage(stage.ordinal());
		elevator.setSetpoint(ElevatorSubsystem.Follower.WRIST, elevator.getWristStages()[elevator.getWristStage()]);
		
		GambeziDashboard.set_double("Elevator/Index", elevator.getElevatorStage());
		GambeziDashboard.set_double("Wrist/Index", elevator.getWristStage());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !elevator.isRunning(ElevatorSubsystem.Follower.ELEVATOR);
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(!(stage == ElevatorSubsystem.ElevatorPositions.BOTTOM)){
    		elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.PID);
    		elevator.setSetpoint(ElevatorSubsystem.Follower.ELEVATOR, elevator.getElevatorStages()[stage.ordinal()]);
    	}else{
    		elevator.setFeedforward(ElevatorSubsystem.Follower.ELEVATOR, 0, 0, 0);
    		elevator.setFeedback(ElevatorSubsystem.Follower.ELEVATOR, 0, 0, 0);
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
