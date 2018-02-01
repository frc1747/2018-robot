package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

public class ElevatorSubsystem extends HBRSubsystem<ElevatorSubsystem.Follower> {

	HBRTalon leftMotor;
	HBRTalon rightMotor;
	HBRTalon wristMotor;
	DigitalInput limitSwitch;
	Encoder encoder;
	double scaling;
	
	private static ElevatorSubsystem elevator;
	
	public enum Follower{
		POSITION;
	}
	
	public ElevatorSubsystem() {
		leftMotor = new HBRTalon(RobotMap.ELEVATOR_MOTOR_PORTS[0]);
		rightMotor = new HBRTalon(RobotMap.ELEVATOR_MOTOR_PORTS[1]);
		wristMotor = new HBRTalon(RobotMap.WRIST_MOTOR_PORT);
		
//		upperLimitSwitch = new DigitalInput(RobotMap.UPPER_LIMIT_SWITCH_ELEVATOR_PORT);
		
		limitSwitch = new DigitalInput(RobotMap.ELEVATOR_LIMIT_SWITCH);
		
		setLeftScaling(RobotMap.ELEVATOR_SCALING);
		setRightScaling(RobotMap.ELEVATOR_SCALING);
		
		encoder = new Encoder(RobotMap.ELEVATOR_ENCODER_A, RobotMap.ELEVATOR_ENCODER_B, RobotMap.ELEVATOR_ENCODER_INVERSION);
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
	public void setWristPower(double power) {
		wristMotor.set(ControlMode.PercentOutput, power);
	}
	public double getWristPosition() {
		return wristMotor.getPosition(0);
	}
	public double getWristSpeed() {
		return wristMotor.getSpeed(0);
	}
	
	
	
	public boolean getLowerSwitch() {
		return limitSwitch.get();
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
	
	public void resetEncoder() {
//		motors[0].setSelectedSensorPosition(0, 0, 0);
		encoder.reset();
	}
	
	public double getSpeed() {
//		return motors[0].getSpeed(0);
		return encoder.getRate() / scaling;
	}
	
	public double getPosition() {
//		return motors[0].getPosition(0);
		return encoder.get() / scaling;
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
