package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import lib.frc1747.instrumentation.Instrumentation;
import lib.frc1747.instrumentation.Logger;
import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

public class ElevatorSubsystem extends HBRSubsystem<ElevatorSubsystem.Follower> {

	HBRTalon leftMotor;
	HBRTalon rightMotor;
	HBRTalon wristMotor;
	DigitalInput limitSwitch;
	Encoder elevatorEncoder;
	AnalogInput wristEncoder;
	double scaling;
	private Logger eLogger;
	private Logger wLogger;
	int elevatorIndex = 0;
	int wristIndex =  0;
	
	private static ElevatorSubsystem elevator;
	
	public enum Follower{
		ELEVATOR, WRIST;
	}
	
	public ElevatorSubsystem() {
		leftMotor = new HBRTalon(RobotMap.ELEVATOR_MOTOR_PORTS[0]);
		rightMotor = new HBRTalon(RobotMap.ELEVATOR_MOTOR_PORTS[1]);
		leftMotor.setInverted(RobotMap.ELEVATOR_MOTOR_INVERSIONS[0]);
		rightMotor.setInverted(RobotMap.ELEVATOR_MOTOR_INVERSIONS[1]);

		wristMotor = new HBRTalon(RobotMap.WRIST_MOTOR_PORT);
		wristEncoder = new AnalogInput(RobotMap.WRIST_ENCODER);
		elevatorIndex = 0;
		
		limitSwitch = new DigitalInput(RobotMap.ELEVATOR_LIMIT_SWITCH);
		
		setElevatorScaling(RobotMap.ELEVATOR_SCALING);
		
		elevatorEncoder = new Encoder(RobotMap.ELEVATOR_ENCODER_A, RobotMap.ELEVATOR_ENCODER_B, RobotMap.ELEVATOR_ENCODER_INVERSION);
		eLogger = Instrumentation.getLogger("Elevator");
		wLogger = Instrumentation.getLogger("Wrist");
		eLogger.registerDouble("Position Setpoint", true, true);
		eLogger.registerDouble("Actual Position", true, true);
		wLogger.registerDouble("Position Setpoint", true, true);
		wLogger.registerDouble("Actual Position", true, true);
		
		GambeziDashboard.set_double("Wrist/kF", 1);
		GambeziDashboard.set_double("Elevator/kF", 1);
	}
	
	
	public static ElevatorSubsystem getInstance() {
		if(elevator == null) {
			elevator = new ElevatorSubsystem();
		}
		return elevator;
	}
	
	public void setElevatorPower(double power) {
		setLeftPower(power);
		setRightPower(power);
	}
	public void setWristPower(double power) {
		wristMotor.set(ControlMode.PercentOutput, power);
	}
	public double getWristPosition() {
		if(wristEncoder.getVoltage() - RobotMap.WRIST_OFFSET >= 0){
			return wristEncoder.getVoltage() - RobotMap.WRIST_OFFSET;
		}else{
			return (5 + wristEncoder.getVoltage() - RobotMap.WRIST_OFFSET) * 2 * Math.PI/5;
		}
		
	}
	public void setElevatorStage(int index){
		this.elevatorIndex = index;
	}
	public int getElevatorStage(){
		return elevatorIndex;
	}
	
	public void setWristStage(int index){
		this.wristIndex = index;
	}
	public int getWristStage(){
		return wristIndex;
	}
	
	
	
	public boolean getLowerSwitch() {
		return limitSwitch.get();
	}
	
	//Left motor
	public void setLeftPower(double power) {
		leftMotor.set(ControlMode.PercentOutput, power);
	}
	public void setElevatorScaling(double scaling) {
		leftMotor.setScaling(scaling);
	}
	public double getElevatorSpeed() {
		return elevatorEncoder.getRate() / scaling;
	}
	public double getElevatorPosition() {
		return elevatorEncoder.get() / scaling;
	}
	
	//Right motor
	public void setRightPower(double power) {
		rightMotor.set(ControlMode.PercentOutput, power);
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
		inputs[0][1] = getWristPosition();
		inputs[1][1] = 0;

		return inputs;
	}

	@Override
	public void pidWrite(double[] output) {
		setElevatorPower(output[0] + GambeziDashboard.get_double("Elevator/kF"));
		setWristPower(output[1] + Math.sin(getWristPosition()) * GambeziDashboard.get_double("Wrist/kF"));
	}
	
	public void resetEncoder() {
		elevatorEncoder.reset();
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	@Override
	public void internalVariablesWrite(double[] output) {
		// TODO Auto-generated method stub
		eLogger.putDouble("Position Setpoint", output[0]);
		eLogger.putDouble("Actual Position", output[1]);
		wLogger.putDouble("Position Setpoint", output[2]);
		wLogger.putDouble("Actual Position", output[3]);
		
	}
}
