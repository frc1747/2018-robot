package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class ZeroSensors extends Command {
	
	DriveSubsystem drive;
	
	long startTime;

    public ZeroSensors() {
        requires(drive = DriveSubsystem.getInstance());
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    	drive.resetEncoders();
 
    	drive.getGyro().zeroYaw();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.setLeftPower(0.0);
    	drive.setRightPower(0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime >= 100;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
