package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

//import com.frc1747.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.controller.Logitech;
import lib.frc1747.instrumentation.Instrumentation;
import lib.frc1747.instrumentation.Logger;
import lib.frc1747.subsytems.HBRSubsystem;

/**
 *
 */
public class WristDown extends Command {
	private ElevatorSubsystem elevator;
	
	private final double s_v_max = 18;
	private final double a_v_max = 17.28;
	private final double[] elevatorPositions = {0, 24, 48};
	private final double[] wristPositions = {0, 90, 135};
	private final double wristScaling = 5/360;
	
	

	
	double elevatorSetpoint;
	double wristSetpoint;

    public WristDown() {
    	requires(elevator = ElevatorSubsystem.getInstance());
    	setInterruptible(true);
    	GambeziDashboard.set_double("Elevator/Position/kV", 0);
    	GambeziDashboard.set_double("Elevator/Position/kA", 0);
    	GambeziDashboard.set_double("Elevator/Position/kP", 0);
    	GambeziDashboard.set_double("Elevator/Position/kI", 0);
    	GambeziDashboard.set_double("Elevator/Position/kD", 0);
    	GambeziDashboard.set_double("Wrist/kA", 0);
    	GambeziDashboard.set_double("Wrist/kV", 0);
    	GambeziDashboard.set_double("Wrist/kP", 0);
    	GambeziDashboard.set_double("Wrist/kI", 0);
    	GambeziDashboard.set_double("Wrist/kD", 0);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//setup velocity PID
    	elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.PID);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.PIDMode.VELOCITY);
    	elevator.setILimit(ElevatorSubsystem.Follower.ELEVATOR, 0);
    	elevator.setFeedforward(ElevatorSubsystem.Follower.ELEVATOR, 0, GambeziDashboard.get_double("Elevator/Position/kV"), GambeziDashboard.get_double("Elevator/Position/kA"));
    	elevator.setFeedback(ElevatorSubsystem.Follower.ELEVATOR, GambeziDashboard.get_double("Elevator/Position/kP"), GambeziDashboard.get_double("Elevator/Position/kI"), GambeziDashboard.get_double("Elevator/Position/kD"));
    	elevator.resetIntegrator(ElevatorSubsystem.Follower.ELEVATOR);
    	
    	//setup angle PID
    	elevator.setMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.Mode.PID);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.PIDMode.VELOCITY);
    	elevator.setILimit(ElevatorSubsystem.Follower.WRIST, 0);
    	elevator.setFeedforward(ElevatorSubsystem.Follower.WRIST, 0, GambeziDashboard.get_double("Wrist/kV"), GambeziDashboard.get_double("Wrist/kA"));
    	elevator.setFeedback(ElevatorSubsystem.Follower.WRIST, GambeziDashboard.get_double("Wrist/kP"), GambeziDashboard.get_double("Wrist/kI"), GambeziDashboard.get_double("Wrist/kD"));
		elevator.resetIntegrator(ElevatorSubsystem.Follower.WRIST);

		elevator.setEnabled(true);

		if(elevator.getWristStage() != 0){	
			elevator.setSetpoint(ElevatorSubsystem.Follower.WRIST, (wristPositions[elevator.getWristStage()] - 1) * wristScaling);
			elevator.setElevatorStage(elevator.getElevatorStage() - 1);
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	elevator.setEnabled(false);
    	elevator.setLeftPower(0);
    	elevator.setRightPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
