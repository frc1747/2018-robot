package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ClawSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ScaleDrop2 extends Command{
	
	long startTime;
	IntakeSubsystem intake;
	ClawSubsystem claw;
	
	public ScaleDrop2(){
		intake = IntakeSubsystem.getInstance();
		claw = ClawSubsystem.getInstance();
		requires(intake);
		requires(claw);
	}
	
	protected void initialize(){
		startTime = System.currentTimeMillis();
		intake.setPower(-0.3);
	}
	
	protected void execute(){
		if(System.currentTimeMillis() - startTime > 100){
			intake.setPower(0);
			claw.setSolenoid(true);
		}	
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void end(){
		claw.setSolenoid(false);
		intake.setPower(0);
	}
	
	protected void interrupted(){
		end();
	}

}
