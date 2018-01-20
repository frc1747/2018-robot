package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem.DriveSide;

import com.ctre.phoenix.motorcontrol.ControlMode;

import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

public class IntakeSubsystem extends HBRSubsystem {
	
	private HBRTalon leftIntakeMotor;
	private HBRTalon rightIntakeMotor;
	private static IntakeSubsystem intake;
	
	
	public IntakeSubsystem() {
		leftIntakeMotor = new HBRTalon(RobotMap.LEFT_INTAKE_PORT);
		rightIntakeMotor = new HBRTalon(RobotMap.RIGHT_INTAKE_PORT);
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


public static IntakeSubsystem getInstance() {
	if(intake == null) {
		intake = new IntakeSubsystem();
	}
	return intake;
}
}


