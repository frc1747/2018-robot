package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestUp extends Command {
	ElevatorSubsystem elevator;
    public TestUp() {
    	requires(elevator = ElevatorSubsystem.getInstance());
    	setInterruptible(true);
    	GambeziDashboard.set_double("Wrist/UpPower", 0.5);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.setWristPower(GambeziDashboard.get_double("Wrist/UpPower"));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after is Finished returns true
    protected void end() {
    	elevator.setWristPower(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}