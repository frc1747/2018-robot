package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.subsytems.HBRSubsystem;

public class WristVertical extends Command {
	private ElevatorSubsystem elevator;
	
	private final double s_v_max = 18;
	private final double a_v_max = 17.28;
	private final double[] elevatorPositions = {0, 24, 48};
	private final double[] wristPositions = {0, 90, 135};
	private final double wristScaling = 5/360;
	
	

	
	double elevatorSetpoint;
	double wristSetpoint;

    public WristVertical() {
    	requires(elevator = ElevatorSubsystem.getInstance());
    	setInterruptible(true); 	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.setWristStage(elevator.getWristStages().length - 2);
    	
    	//setup elevator PID
    	elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.PID);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.PIDMode.POSITION);
    	elevator.setILimit(ElevatorSubsystem.Follower.ELEVATOR, 0);
    	elevator.setFeedforward(ElevatorSubsystem.Follower.ELEVATOR, 0, GambeziDashboard.get_double("Elevator/kV"), GambeziDashboard.get_double("Elevator/kA"));
    	elevator.setFeedback(ElevatorSubsystem.Follower.ELEVATOR, GambeziDashboard.get_double("Elevator/kP"), GambeziDashboard.get_double("Elevator/kI"), GambeziDashboard.get_double("Elevator/kD"));
    	elevator.resetIntegrator(ElevatorSubsystem.Follower.ELEVATOR);
    	
    	//setup wrist PID
    	elevator.setMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.Mode.PID);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.PIDMode.POSITION);
    	elevator.setILimit(ElevatorSubsystem.Follower.WRIST, 0);
    	elevator.setFeedforward(ElevatorSubsystem.Follower.WRIST, 0, GambeziDashboard.get_double("Wrist/kV"), GambeziDashboard.get_double("Wrist/kA"));
    	elevator.setFeedback(ElevatorSubsystem.Follower.WRIST, GambeziDashboard.get_double("Wrist/kP"), GambeziDashboard.get_double("Wrist/kI"), GambeziDashboard.get_double("Wrist/kD"));
		elevator.resetIntegrator(ElevatorSubsystem.Follower.WRIST);
		
		elevator.setEnabled(true);
		
	
		elevator.setSetpoint(ElevatorSubsystem.Follower.WRIST, elevator.getWristStages()[elevator.getWristStage()]);
		elevator.setSetpoint(ElevatorSubsystem.Follower.ELEVATOR, (elevator.getElevatorStages()[elevator.getElevatorStage()]));

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
