package org.usfirst.frc.team1747.robot.commands.reset;


import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class AutonStopMotors extends Command {
	
	DriveSubsystem drive;
	long startTime;

    public AutonStopMotors() {
        requires(drive = DriveSubsystem.getInstance());
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.setLeftPower(0.0);
    	drive.setRightPower(0.0);
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
