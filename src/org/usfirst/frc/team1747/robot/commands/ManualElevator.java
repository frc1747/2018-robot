package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.controller.Logitech;
import lib.frc1747.subsytems.HBRSubsystem;

/**
 *
 */
public class ManualElevator extends Command {
	
	private ElevatorSubsystem elevator;
	private double input;
	private boolean PIDEnabled;

    public ManualElevator() {
    	requires(elevator = ElevatorSubsystem.getInstance());
    	setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	PIDEnabled = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	input = OI.getInstance().getOperator().getAxis(Logitech.LEFT_VERTICAL);
    	
    	if (Math.abs(input) < 0.01 && !PIDEnabled){
    		elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.PID);
    		elevator.setSetpoint(ElevatorSubsystem.Follower.ELEVATOR, elevator.getElevatorPosition());
    		elevator.setEnabled(true);
    		PIDEnabled = true;
    	}else if(Math.abs(input) >= 0.01){
    		PIDEnabled = false;
    		elevator.setEnabled(false);
    		elevator.setElevatorPower(input * 0.2);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.PID);
		elevator.setSetpoint(ElevatorSubsystem.Follower.ELEVATOR, elevator.getElevatorPosition());
		elevator.setEnabled(true);
//    	elevator.resetEncoder();
//    	elevator.setElevatorStage(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
