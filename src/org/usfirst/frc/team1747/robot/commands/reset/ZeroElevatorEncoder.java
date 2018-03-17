package org.usfirst.frc.team1747.robot.commands.reset;

import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class ZeroElevatorEncoder extends Command {
	
	ElevatorSubsystem elevator;
	
	long startTime;

    public ZeroElevatorEncoder() {
        requires(elevator = ElevatorSubsystem.getInstance());
        setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    	elevator.resetEncoder();
 
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elevator.setElevatorPower(0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime >= 10;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
