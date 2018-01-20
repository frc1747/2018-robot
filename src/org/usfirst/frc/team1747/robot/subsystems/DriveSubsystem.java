package org.usfirst.frc.team1747.robot.subsystems;

import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

import org.usfirst.frc.team1747.robot.RobotMap;
import org.usfirst.frc.team1747.robot.commands.ArcadeDrive;
import org.usfirst.frc.team1747.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends HBRSubsystem<DriveSubsystem.Follower> {

	DriveSide left, right;

	private static DriveSubsystem drivetrain;
	
	private AnalogInput thermistor;
	
	private AHRS gyro;
	
	public enum Follower {
		DISTANCE, ANGLE
	}
	
	private DriveSubsystem() {
		thermistor = new AnalogInput(RobotMap.FUSE_THERMISTOR);
		gyro = new AHRS(SPI.Port.kMXP);
		left = new DriveSide(RobotMap.LEFT_MOTOR_PORTS,RobotMap.LEFT_MOTOR_INVERSION);
		right = new DriveSide(RobotMap.RIGHT_MOTOR_PORTS,RobotMap.RIGHT_MOTOR_INVERSION);
		left.setScaling(RobotMap.LEFT_SCALING);
		right.setScaling(RobotMap.RIGHT_SCALING);
	}
	
	public static DriveSubsystem getInstance() {
		if(drivetrain == null) {
			drivetrain = new DriveSubsystem();
		}
		return drivetrain;
	}
	
	public double getAverageSpeed(){
		//averages speed of sides
		return (left.getSpeed() + right.getSpeed()) / 2;
	}
	public double getAveragePosition(){
		//averages position of sides
		return (left.getPosition() + right.getPosition()) / 2;
	}
	public AHRS getGryo(){
		return gyro;
	}
	//in degrees C
	public double getTempC(){
		return (thermistor.getVoltage()-0.1555)/0.0442;
	}
	//in degrees F
	public double getTempF(){
		return 32+(((thermistor.getVoltage()-0.1555)/0.0442)*9/5);
	}
//	//degrees K
//	public double getTempK(){
//		return 273+(thermistor.getVoltage()-0.1555)/0.0442;
//	}
	public void resetEncoders(){
		left.resetEncoder();
		right.resetEncoder();
	}
	//Left drive side
	public void setLeftPower(double power) {
		left.setPower(power);
	}
	public double getLeftSpeed() {
		return left.getSpeed();
	}
	public double getLeftPosition() {
		return left.getPosition();
	}
	public void resetLeftEncoder(){
		left.resetEncoder();
	}
	
	//Right drive side
	public void setRightPower(double power) {
		right.setPower(power);
	}
	public double getRightSpeed() {
		return right.getSpeed();
	}
	public double getRightPosition() {
		return right.getPosition();
	}
	public void resetRightEncoder(){
		left.resetEncoder();
	}
	
	@Override
	public double[][] pidRead() {
		double[][] output = new double[2][2];
		output[1][0] = getAverageSpeed();
		output[0][0] = getAveragePosition();
		output[0][1] = (2 * Math.PI) * ((-getGryo().getAngle()) / 360);
		output[1][1] = (2 * Math.PI) * (-getGryo().getRate() / 360);
		return output;
	}

	@Override
	public void pidWrite(double[] output) {
		// TODO Auto-generated method stub
		driveArcadeMode(output[0], -output[1]);
	}

	private void driveArcadeMode(double leftvert, double righthoriz) {
		right.setPower(leftvert - righthoriz);
		left.setPower(leftvert + righthoriz);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DriveWithJoysticks());
	}
	
	public class DriveSide {
		
		HBRTalon[] motors;
		
		public DriveSide(int[] ports, boolean[] inverted) {
			motors = new HBRTalon[4];
			for(int i = 0; i < 4; i++) {
				motors[i] = new HBRTalon(ports[i]);
				motors[i].setInverted(inverted[i]);
			}
		}
		
		public void setPower(double power) {
			for(int i = 0; i < 4; i++) {
				motors[i].set(ControlMode.PercentOutput, power);
			}
		}
		
		public void resetEncoder(){
			motors[0].setSelectedSensorPosition(0, 0, 0);
		}
		
		public double getSpeed() {
			return motors[0].getSpeed(0);
		}
		
		public double getPosition() {
			return motors[0].getPosition(0);
		}
		
		public void setScaling(double scaling){
			motors[0].setScaling(scaling);
		}
		
	}
}
