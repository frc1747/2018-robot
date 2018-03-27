package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ClawSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class OpenClawTimed extends Command {
	
	private ClawSubsystem claw;
	private IntakeSubsystem intake;
	long startTime;
	long delay;

	
	public OpenClawTimed(long delay) {
		claw = ClawSubsystem.getInstance();
		requires(claw);
		intake = IntakeSubsystem.getInstance();
		this.delay = delay;
	}
	
	
	// Called just before this Command runs the first time
		@Override
		protected void initialize() {
	    	startTime = System.currentTimeMillis();
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
        return (System.currentTimeMillis() - startTime) >= delay;
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


