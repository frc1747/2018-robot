package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

public class ElevatorSubsystem extends HBRSubsystem<ElevatorSubsystem.Follower> {

	HBRTalon leftMotor;
	HBRTalon rightMotor;
	DigitalInput limitSwitch;
	
	private static ElevatorSubsystem elevator;
	
	public enum Follower{
		ELEVATOR, WRIST;
	}
	
	public ElevatorSubsystem() {
		leftMotor = new HBRTalon(RobotMap.ELEVATOR_MOTOR_PORTS[0]);
		rightMotor = new HBRTalon(RobotMap.ELEVATOR_MOTOR_PORTS[1]);
//		upperLimitSwitch = new DigitalInput(RobotMap.UPPER_LIMIT_SWITCH_ELEVATOR_PORT);
		
		limitSwitch = new DigitalInput(RobotMap.ELEVATOR_LIMIT_SWITCH);
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
	public double getElevatorSpeed() {
		return leftMotor.getSpeed(0);
	}
	public double getElevatorPosition() {
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
	
	//TODO: just uses left side, but the move together? possibly? It is writing to both sides.
	@Override
	public double[][] pidRead() {
		double[][] inputs = new double[2][2];
		inputs[0][0] = getElevatorPosition();
		inputs[1][0] = getElevatorSpeed();

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
