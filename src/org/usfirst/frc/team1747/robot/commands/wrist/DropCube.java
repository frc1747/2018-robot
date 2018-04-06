package org.usfirst.frc.team1747.robot.commands.wrist;

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
    	intake.setPower(-0.8);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return !intake.ifCubeCompletelyHeld();
    }

    // Called once after isFinished returns true
    protected void end() {
    	claw.setSolenoid(false);
    	intake.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
