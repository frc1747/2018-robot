package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.controller.Logitech;

public class ClimbUp extends Command {
	
	ClimberSubsystem climber;
	DriverStation ds = DriverStation.getInstance();
	
	public ClimbUp() {
		climber = ClimberSubsystem.getInstance();
		ds = DriverStation.getInstance();
		requires(climber);
		setInterruptible(true);
	}
	
	@Override
	protected void initialize() {}
	
	@Override
	protected void execute() {
//		if(ds.getMatchTime() < 32){
			climber.setPower(-Math.abs(OI.getInstance().getOperator().getAxis(Logitech.RIGHT_VERTICAL)));
//		}
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
