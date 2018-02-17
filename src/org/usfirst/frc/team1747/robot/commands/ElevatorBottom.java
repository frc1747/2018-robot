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
public class ElevatorBottom extends Command {
	private ElevatorSubsystem elevator;
	
	double elevatorSetpoint;
	double distance;

	public final double POSITION = elevator.getElevatorStages()[0];
	
    public ElevatorBottom() {
    	requires(elevator = ElevatorSubsystem.getInstance());
    	setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	distance = POSITION - elevator.getElevatorPosition();
    	double[][][] profiles = HBRSubsystem.generateSkidSteerPseudoProfile(distance, 0, Parameters.I_SAMPLE_LENGTH, 120, 200, 9000.1, Parameters.W_WIDTH, Parameters.DT, true, true);

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
		
		elevator.setEnabled(true);
			
		if(elevator.getElevatorStage() < elevator.getElevatorStages().length - 1){	
			elevator.setElevatorStage(elevator.getElevatorStage() + 1);
			GambeziDashboard.log_string(elevator.getElevatorStage() +"");
		}
		elevator.setSetpoint(ElevatorSubsystem.Follower.ELEVATOR, (elevator.getElevatorStages()[elevator.getElevatorStage()]));
		elevator.setSetpoint(ElevatorSubsystem.Follower.WRIST, elevator.getWristStages()[elevator.getWristStage()]);
		
		GambeziDashboard.set_double("Elevator/Index", elevator.getElevatorStage());
		GambeziDashboard.set_double("Wrist/Index", elevator.getWristStage());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
