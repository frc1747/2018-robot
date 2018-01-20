package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command {
	
	public IntakeSubsystem intake;
	
	public Intake() {
		intake = IntakeSubsystem.getInstance();
		requires (intake);
	}
	
	
	// Called just before this Command runs the first time
		@Override
		protected void initialize() {
		}

		// Called repeatedly when this Command is scheduled to run
		@Override
		protected void execute() {
		}
		
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	// Called once after isFinished returns true
		@Override
		protected void end() {
			intake.setLeftPower(0.0);
			intake.setRightPower(0.0);
		}

		// Called when another command which requires one or more of the same
		// subsystems is scheduled to run
		@Override
		protected void interrupted() {
		}

	
	}


