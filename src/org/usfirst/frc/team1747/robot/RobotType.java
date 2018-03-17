package org.usfirst.frc.team1747.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RobotType extends Subsystem {

	private static DigitalInput jumper;
	private static RobotType instance;
	
	public RobotType(){
		jumper = new DigitalInput(RobotMap.JUMPER_DIGITAL_INPUT);
	}
	
	public DigitalInput getJumper(){
		return jumper;
	}
	
	public static RobotType getInstance(){
		if(instance == null){
			instance = new RobotType();
		}
		return instance;
	}
	
	@Override
	protected void initDefaultCommand() {
	}

}
