package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbUp extends Command {
	
	ClimberSubsystem climber;
	
	public ClimbUp() {
		climber = ClimberSubsystem.getInstance();
		requires(climber);
		setInterruptible(true);
	}
	
	@Override
	protected void initialize() {
		climber.setPower(0.3);
	}
	
	@Override
	protected void execute() {
		
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
		climber.setPower(0.0);
	}
	
	@Override
	protected void interrupted() {
		end();
	}
}
