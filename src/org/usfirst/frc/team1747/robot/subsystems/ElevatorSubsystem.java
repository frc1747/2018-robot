package org.usfirst.frc.team1747.robot.subsystems;

import org.usfirst.frc.team1747.robot.Robot;
import org.usfirst.frc.team1747.robot.RobotMap;
import org.usfirst.frc.team1747.robot.RobotType;
import org.usfirst.frc.team1747.robot.commands.ManualElevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
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
	int elevatorIndex = 0;
	int wristIndex =  3;
	double[] elevatorPositions = {0, 12, 24, 36, 51, 60, 72};
	double[] wristPositions = {Math.PI / 2, 3 * Math.PI / 4, Math.PI, 3.8};
	double wristOffset;
	
	private static ElevatorSubsystem elevator;
	
	public enum ElevatorPositions{
		BOTTOM, CUBE_PILE, SWITCH, HIGH_SWITCH, LOWEST_SCALE, LOW_SCALE, TOP;
	}
	
	public enum Follower{
		ELEVATOR, WRIST;
	}
	
	public ElevatorSubsystem() {
		super(RobotMap.DT);
		
		//		constants are now in Robot.java to fix bad issues
		
		leftMotor = new HBRTalon(RobotMap.ELEVATOR_MOTOR_PORTS[0]);
		rightMotor = new HBRTalon(RobotMap.ELEVATOR_MOTOR_PORTS[1]);
		leftMotor.setInverted(RobotMap.ELEVATOR_MOTOR_INVERSIONS[0]);
		rightMotor.setInverted(RobotMap.ELEVATOR_MOTOR_INVERSIONS[1]);

		wristMotor = new HBRTalon(RobotMap.WRIST_MOTOR_PORT);
		wristEncoder = new AnalogInput(RobotMap.WRIST_ENCODER);
		elevatorIndex = 0;
		
		limitSwitch = new DigitalInput(RobotMap.ELEVATOR_LIMIT_SWITCH);
				
		elevatorEncoder = new Encoder(RobotMap.ELEVATOR_ENCODER_A, RobotMap.ELEVATOR_ENCODER_B, RobotMap.ELEVATOR_ENCODER_INVERSION);
		
		scaling = RobotMap.ELEVATOR_SCALING;
    	
    	if(!RobotType.getInstance().getJumper().get()){
    		wristOffset = RobotMap.WRIST_OFFSET_COMP;
    	}else{
    		wristOffset = RobotMap.WRIST_OFFSET_PRACTICE;
    	}
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
		if (RobotMap.WRIST_ENCODER_GEAR * (-wristEncoder.getVoltage()) * 2 * Math.PI/5 - wristOffset >= 0) {
			return RobotMap.WRIST_ENCODER_GEAR * (-wristEncoder.getVoltage()) * 2 * Math.PI/5 - wristOffset;
		} else {
			return RobotMap.WRIST_ENCODER_GEAR * (5 + -wristEncoder.getVoltage()) * 2 * Math.PI/5 - wristOffset;
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
	
	public double[] getWristStages() {
		return wristPositions;
	}
	
	public double[] getElevatorStages() {
		return elevatorPositions;
	}
	
	public boolean getLowerSwitch() {
		return limitSwitch.get();
	}
	
	//Left motor
	public void setLeftPower(double power) {
		leftMotor.set(ControlMode.PercentOutput, power);
	}

	//Inches per second
	public double getElevatorSpeed() {
		return elevatorEncoder.getRate()/scaling;
	}
	
	//Inches
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
		
		//Sets motor power to 0 if we are not changing the stage and wrist is all the way down/up
		if(((getWristStage() == 0) && (Math.abs(getWristPosition() - getWristStages()[0]) < Math.PI/12)) ){
			if(output[1] + Math.sin(getWristPosition()) * 0.07 /*GambeziDashboard.get_double("Wrist/kF")*/ > 0){
				setWristPower(output[1] + Math.sin(getWristPosition()) * 0.07 /*GambeziDashboard.get_double("Wrist/kF")*/);
			}else{
				setWristPower(0);	
			}
			
		}else if(((getWristStage() == (getWristStages().length - 1))	&& (Math.abs(getWristPosition() - getWristStages()[getWristStages().length - 1]) < Math.PI/12))){
			if(output[1] + Math.sin(getWristPosition()) * 0.07 /*GambeziDashboard.get_double("Wrist/kF")*/ < 0){
				setWristPower(output[1] + Math.sin(getWristPosition()) * 0.07 /*GambeziDashboard.get_double("Wrist/kF")*/);
			}else{
				setWristPower(0);	
			}
		}else{
			setWristPower(output[1] + Math.sin(getWristPosition()) * 0.07 /*GambeziDashboard.get_double("Wrist/kF")*/);

		}
		
		if((getElevatorStage() == 0) && (Math.abs(getElevatorPosition() - getElevatorStages()[0]) < 3)){
			if(output[0] + 0.04 /*GambeziDashboard.get_double("Elevator/kF")*/ < 0){
				setElevatorPower(output[0] + 0.04 /*GambeziDashboard.get_double("Elevator/kF")*/);
			}else{
				setElevatorPower(0);	
			}
			setElevatorPower(0);
			
		}else{
			setElevatorPower(output[0] + 0.04 /*GambeziDashboard.get_double("Elevator/kF")*/);
		}
		
		/*
		GambeziDashboard.set_double("Wrist/PIDOutput", output[1]);
		GambeziDashboard.set_double("Elevator/PIDOutput", output[0]);
		GambeziDashboard.set_double("Wrist/Angle", getWristPosition());
		GambeziDashboard.set_double("Wrist/Setpoint", wristPositions[wristIndex]);
		GambeziDashboard.set_double("Elevator/Position", getElevatorPosition());
		*/
	}
	
	public void resetEncoder() {
		elevatorEncoder.reset();
	}
	
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ManualElevator());
	}

	@Override
	public void internalVariablesWrite(double[] output) {
		// TODO Auto-generated method stub
		GambeziDashboard.set_double("Elevator/Position_Setpoint", output[0]);
		GambeziDashboard.set_double("Elevator/Actual_Position", output[1]);
		GambeziDashboard.set_double("Wrist/Position_Setpoint", output[2]);
		GambeziDashboard.set_double("Wrist/Actual_Position", output[3]);
	}


	@Override
	public void errorsWrite(double[] error) {
//		if(DriverStation.getInstance().isAutonomous()) {
//			if(Math.abs(error[0]) >= RobotMap.ELEVATOR_MAX_ERROR_POWER) {
//				Robot.fatalError("Elevator encoder fault>>"
//						+ " Encoder: " + getElevatorPosition()
//						+ " Error: " + error[0]);
//			}
//		}
	}
}
