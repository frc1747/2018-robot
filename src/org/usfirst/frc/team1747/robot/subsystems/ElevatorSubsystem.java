package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

public class ElevatorSubsystem extends HBRSubsystem<ElevatorSubsystem.Follower> {

	HBRTalon leftMotor;
	HBRTalon rightMotor;
	DigitalInput upperLimitSwitch;
	DigitalInput lowerLimitSwitch;
	
	private static ElevatorSubsystem elevator;
	
	public enum Follower{
		POSITION;
	}
	
	public ElevatorSubsystem() {
		leftMotor = new HBRTalon(RobotMap.ELEVATOR__MOTOR_PORTS[0]);
		rightMotor = new HBRTalon(RobotMap.ELEVATOR__MOTOR_PORTS[1]);
		upperLimitSwitch = new DigitalInput(RobotMap.UPPER_LIMIT_SWITCH_ELEVATOR_PORT);
		lowerLimitSwitch = new DigitalInput(RobotMap.LOWER_LIMIT_SWITCH_ELEVATOR_PORT);
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
	
	public boolean getUpperSwitch() {
		return upperLimitSwitch.get();
	}
	
	public boolean getLowerSwitch() {
		return lowerLimitSwitch.get();
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
	
	//TODO: just uses left side, but the move together? possibly? It is writing to both sides.
	@Override
	public double[][] pidRead() {
		double[][] inputs = new double[2][1];
		inputs[0][0] = getLeftPosition();
		inputs[1][0] = getLeftSpeed();
		return inputs;
	}

	@Override
	public void pidWrite(double[] output) {
		setPower(output[0]);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalVariablesWrite(double[] output) {
		// TODO Auto-generated method stub
		
	}
}
