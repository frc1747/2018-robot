package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ClawSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;

public class DropCube extends Command {

	private IntakeSubsystem intake;
	private ClawSubsystem claw;
	
    public DropCube() {
    	intake = IntakeSubsystem.getInstance();
    	claw = ClawSubsystem.getInstance();
		requires (intake);
		requires(claw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	claw.setSolenoid(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return !intake.ifCubePartiallyHeld();
    }

    // Called once after isFinished returns true
    protected void end() {
    	claw.setSolenoid(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}