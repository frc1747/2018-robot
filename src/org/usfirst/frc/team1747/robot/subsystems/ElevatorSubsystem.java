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
	int wristIndex =  3;
	double[] elevatorPositions = {0, 24, 48, 60, 67};
	double[] wristPositions = {Math.PI / 2, 3 * Math.PI / 4, Math.PI, 3.8};
	
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
				
		elevatorEncoder = new Encoder(RobotMap.ELEVATOR_ENCODER_A, RobotMap.ELEVATOR_ENCODER_B, RobotMap.ELEVATOR_ENCODER_INVERSION);
		eLogger = Instrumentation.getLogger("Elevator");
		wLogger = Instrumentation.getLogger("Wrist");
		eLogger.registerDouble("Position Setpoint", true, true);
		eLogger.registerDouble("Actual Position", true, true);
		wLogger.registerDouble("Position Setpoint", true, true);
		wLogger.registerDouble("Actual Position", true, true);
		
		scaling = RobotMap.ELEVATOR_SCALING;

		GambeziDashboard.set_double("Elevator/kF", 0.2);
		GambeziDashboard.set_double("Elevator/kV", 0);
    	GambeziDashboard.set_double("Elevator/kA", 0);
    	GambeziDashboard.set_double("Elevator/kP", 0.02);
    	GambeziDashboard.set_double("Elevator/kI", 0);
    	GambeziDashboard.set_double("Elevator/kD", 0);
		GambeziDashboard.set_double("Wrist/kF", 0.35);
    	GambeziDashboard.set_double("Wrist/kA", 0);
    	GambeziDashboard.set_double("Wrist/kV", 0);
    	GambeziDashboard.set_double("Wrist/kP", 0.59);
    	GambeziDashboard.set_double("Wrist/kI", 0);
    	GambeziDashboard.set_double("Wrist/kD", 0);
		/*
		//setup velocity PID
    	elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.PID);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.PIDMode.VELOCITY);
    	elevator.setILimit(ElevatorSubsystem.Follower.ELEVATOR, 0);
    	elevator.setFeedforward(ElevatorSubsystem.Follower.ELEVATOR, 0, GambeziDashboard.get_double("Elevator/kV"), GambeziDashboard.get_double("Elevator/kA"));
    	elevator.setFeedback(ElevatorSubsystem.Follower.ELEVATOR, GambeziDashboard.get_double("Elevator/kP"), GambeziDashboard.get_double("Elevator/kI"), GambeziDashboard.get_double("Elevator/kD"));
    	elevator.resetIntegrator(ElevatorSubsystem.Follower.ELEVATOR);
    	
    	//setup angle PID
    	elevator.setMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.Mode.PID);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.PIDMode.VELOCITY);
    	elevator.setILimit(ElevatorSubsystem.Follower.WRIST, 0);
    	elevator.setFeedforward(ElevatorSubsystem.Follower.WRIST, 0, GambeziDashboard.get_double("Wrist/kV"), GambeziDashboard.get_double("Wrist/kA"));
    	elevator.setFeedback(ElevatorSubsystem.Follower.WRIST, GambeziDashboard.get_double("Wrist/kP"), GambeziDashboard.get_double("Wrist/kI"), GambeziDashboard.get_double("Wrist/kD"));
		elevator.resetIntegrator(ElevatorSubsystem.Follower.WRIST);
		*/
//		elevator.setEnabled(true);
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
		if (RobotMap.WRIST_ENCODER_GEAR * (-wristEncoder.getVoltage()) * 2 * Math.PI/5 - RobotMap.WRIST_OFFSET >= 0) {
			return RobotMap.WRIST_ENCODER_GEAR * (-wristEncoder.getVoltage()) * 2 * Math.PI/5 - RobotMap.WRIST_OFFSET;
		} else {
			return RobotMap.WRIST_ENCODER_GEAR * (5 + -wristEncoder.getVoltage()) * 2 * Math.PI/5 - RobotMap.WRIST_OFFSET;
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
		setElevatorPower(output[0] + GambeziDashboard.get_double("Elevator/kF"));
		setWristPower(output[1] + Math.sin(getWristPosition()) * GambeziDashboard.get_double("Wrist/kF"));
		GambeziDashboard.set_double("Wrist/PIDOutput", output[1]);
		GambeziDashboard.set_double("Elevator/PIDOutput", output[0]);
		GambeziDashboard.set_double("Wrist/Angle", getWristPosition());
		GambeziDashboard.set_double("Wrist/Setpoint", wristPositions[wristIndex]);
		GambeziDashboard.set_double("Elevator/Position", getElevatorPosition());
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
		//GambeziDashboard.log_string("Internal Variables Write");
	}
}
