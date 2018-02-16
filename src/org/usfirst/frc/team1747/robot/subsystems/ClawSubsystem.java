package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ClawSubsystem extends Subsystem {

	private Solenoid solenoid;
	private static ClawSubsystem instance;
	
	public ClawSubsystem(){
		solenoid = new Solenoid(RobotMap.CLAW_SOLENOID);
	}
	
	public void setSolenoid(boolean state){
		solenoid.set(state);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	
	public static ClawSubsystem getInstance(){
		if(instance == null){
			instance = new ClawSubsystem();
		}
		return instance;
	}

}
