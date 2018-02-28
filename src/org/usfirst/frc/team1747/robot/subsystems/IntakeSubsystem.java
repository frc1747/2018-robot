package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

public class IntakeSubsystem extends HBRSubsystem<IntakeSubsystem.Follower> {
	
	private HBRTalon leftIntakeMotor;
	private HBRTalon rightIntakeMotor;
	private static IntakeSubsystem intake;
	private AnalogInput sensor;
	private final double fullyHeldSensorVoltage = 2.25;
	//just over .5 when just inside robot, just over .4 when just outside robot (around .45 and .55 respectively)
	private final double partiallyHeldSensorVoltage = 0.9;
	private Solenoid led;
	
	public enum Follower{
		POSITION;
	}
	
	public IntakeSubsystem() {
		super(RobotMap.DT);
		leftIntakeMotor = new HBRTalon(RobotMap.LEFT_INTAKE_PORT);
		rightIntakeMotor = new HBRTalon(RobotMap.RIGHT_INTAKE_PORT);
		leftIntakeMotor.setInverted(RobotMap.INTAKE_MOTOR_INVERSIONS[0]);
		rightIntakeMotor.setInverted(RobotMap.INTAKE_MOTOR_INVERSIONS[1]);
		sensor = new AnalogInput(RobotMap.INTAKE_CUBE_SENSOR);
		led = new Solenoid(RobotMap.CUBE_LED_SOLENOID);
	}
	
	public void setLED(boolean state){
		led.set(state);
	}
	
	public boolean getLED(){
		return led.get();
	}
	
	public void setPower(double power){
		setLeftPower(power);
		setRightPower(power);
	}
	
	public double getSwitch() {
		return sensor.getVoltage();
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
	
	/**Uses IR sensor to returns true if cube is all the way in
	 * 
	 * @return Whether cube is all the way in
	 */
	public boolean ifCubeCompletelyHeld(){
		return (sensor.getVoltage() > fullyHeldSensorVoltage);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean ifCubePartiallyHeld(){
		return (sensor.getVoltage() > partiallyHeldSensorVoltage);
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


