package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

public class ElevatorSubsystem extends HBRSubsystem {

	HBRTalon leftMotor;
	HBRTalon rightMotor;
	
	private static ElevatorSubsystem elevator;
	
	public ElevatorSubsystem() {
		leftMotor = new HBRTalon(RobotMap.ELEVATOR__MOTOR_PORTS[0]);
		rightMotor = new HBRTalon(RobotMap.ELEVATOR__MOTOR_PORTS[1]);
	}
	
	public static ElevatorSubsystem getInstance() {
		if(elevator == null) {
			elevator = new ElevatorSubsystem();
		}
		return elevator;
	}
	
	public void setPower(double power) {
		setLeftPower(power);
		setRightPower(power);
	}
	
	//Left motor
	public void setLeftPower(double power) {
		leftMotor.set(ControlMode.PercentOutput, power);
	}
	public void setLeftScaling(double scaling) {
		leftMotor.setScaling(scaling);
	}
	public double getLeftSpeed() {
		return leftMotor.getSpeed(0);
	}
	public double getLeftPosition() {
		return leftMotor.getPosition(0);
	}
	
	//Right motor
	public void setRightPower(double power) {
		rightMotor.set(ControlMode.PercentOutput, power);
	}
	public void setRightScaling(double scaling) {
		rightMotor.setScaling(scaling);
	}
	public double getRightPower() {
		return rightMotor.getSpeed(0);
	}
	public double getRightPosition() {
		return rightMotor.getPosition(0);
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

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
