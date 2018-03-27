package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Solenoid;
import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

public class ClimberSubsystem extends HBRSubsystem {
	
	static ClimberSubsystem climber;
	
	HBRTalon motor1;
	HBRTalon motor2;
	private Solenoid solenoid;
	
	public ClimberSubsystem() {
		super(RobotMap.DT);
		motor1 = new HBRTalon(RobotMap.CLIMB_MOTOR_1);
		motor2 = new HBRTalon(RobotMap.CLIMB_MOTOR_2);
		
		motor1.setInverted(RobotMap.CLIMB_1_INVERT);
		motor2.setInverted(RobotMap.CLIMB_2_INVERT);
		
		solenoid = new Solenoid(RobotMap.CLIMBER_SOLENOID);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	@Override
	public double[][] pidRead() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pidWrite(double[] output) {
		// TODO Auto-generated method stub
		
	}
	
	public void setPower(double power) {
		motor1.set(ControlMode.PercentOutput, power);
		motor2.set(ControlMode.PercentOutput, power);
	}
	
	public double getSpeed() {
		return motor1.getSpeed(0);
	}
	
	public static ClimberSubsystem getInstance() {
		if (climber == null) {
			climber = new ClimberSubsystem();
		}
		return climber;
	}

	@Override
	public void internalVariablesWrite(double[] output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorsWrite(double[] error) {
		// TODO Auto-generated method stub
		
	}
}
