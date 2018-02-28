package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ClawSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class OpenClaw extends Command {
	
	private ClawSubsystem claw;
	private IntakeSubsystem intake;
	
	public OpenClaw() {
		claw = ClawSubsystem.getInstance();
		requires(claw);
		intake = IntakeSubsystem.getInstance();
	}
	
	
	// Called just before this Command runs the first time
		@Override
		protected void initialize() {
	    	//System.out.println("Open Claw");
		}

		// Called repeatedly when this Command is scheduled to run
		@Override
		protected void execute() {
	    	//System.out.println("Claw Periodic");
			claw.setSolenoid(true);
		}
		
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	// Called once after isFinished returns true
	@Override
	protected void end() {
		claw.setSolenoid(false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

	
	}


