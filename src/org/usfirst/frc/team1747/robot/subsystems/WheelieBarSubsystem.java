package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class WheelieBarSubsystem extends Subsystem {
	
	private static WheelieBarSubsystem wheeliebar;
	private Solenoid solenoid;
	
	public WheelieBarSubsystem() {
		solenoid = new Solenoid(RobotMap.WHEELIE_BAR_SOLENOID);
	}
	
	public void set(Boolean bool) {
		solenoid.set(bool);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	
	public WheelieBarSubsystem getInstance() {
		if (wheeliebar == null) {
			wheeliebar = new WheelieBarSubsystem();
		}
		return wheeliebar;
	}

}
