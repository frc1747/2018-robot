package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem.DriveSide;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

public class IntakeSubsystem extends HBRSubsystem<IntakeSubsystem.Follower> {
	
	private HBRTalon leftIntakeMotor;
	private HBRTalon rightIntakeMotor;
	private static IntakeSubsystem intake;
	private DigitalInput limitSwitch;
	
	public enum Follower{
		POSITION;
	}
	
	public IntakeSubsystem() {
		leftIntakeMotor = new HBRTalon(RobotMap.LEFT_INTAKE_PORT);
		rightIntakeMotor = new HBRTalon(RobotMap.RIGHT_INTAKE_PORT);
		limitSwitch = new DigitalInput(RobotMap.INTAKE_SWITCH);
	}
	
	
	public void setPower(double power){
		setLeftPower(power);
		setRightPower(power);
	}
	
	public boolean getSwitch() {
		return limitSwitch.get();
	}
	
	//left motor
	public void setLeftPower(double power) {
		leftIntakeMotor.set(ControlMode.PercentOutput, power);
	}
	public void setLeftScaling(double scaling) {
		leftIntakeMotor.setScaling(scaling);
	}
	public double getLeftSpeed() {
		return leftIntakeMotor.getSpeed(0);
	}
	public double getLeftPosition() {
		return leftIntakeMotor.getPosition(0);
	}
	
	//Right motor
	public void setRightPower(double power) {
		rightIntakeMotor.set(ControlMode.PercentOutput, power);
	}
	public void setRightScaling(double scaling) {
		rightIntakeMotor.setScaling(scaling);
	}
	public double getRightSpeed() {
		return rightIntakeMotor.getSpeed(0);
	}
	public double getRightPosition() {
		return rightIntakeMotor.getPosition(0);
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
		
	}


public static IntakeSubsystem getInstance() {
	if(intake == null) {
		intake = new IntakeSubsystem();
	}
	return intake;
}


@Override
public void internalVariablesWrite(double[] output) {
	// TODO Auto-generated method stub
	
}
}


