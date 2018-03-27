package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevateDown extends Command {
	
	private ElevatorSubsystem elevator;

    public ElevateDown() {
    	requires(elevator = ElevatorSubsystem.getInstance());
    	setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.setEnabled(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elevator.setElevatorPower(-0.35);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	elevator.setElevatorPower(0);
    	elevator.resetEncoder();
    	elevator.setElevatorStage(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
