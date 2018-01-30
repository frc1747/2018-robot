package org.usfirst.frc.team1747.robot.subsystems;

import lib.frc1747.instrumentation.Instrumentation;
import lib.frc1747.instrumentation.Logger;
import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

import org.usfirst.frc.team1747.robot.RobotMap;
import org.usfirst.frc.team1747.robot.commands.ArcadeDrive;
import org.usfirst.frc.team1747.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

public class DriveSubsystem extends HBRSubsystem<DriveSubsystem.Follower> {

	DriveSide left, right;

	private static DriveSubsystem drivetrain;
	
	private AnalogInput thermistor;
	
	private AHRS gyro;
	
	private Logger logger;
	
	public enum Follower {
		DISTANCE, ANGLE
	}
	
	private DriveSubsystem() {
		thermistor = new AnalogInput(RobotMap.FUSE_THERMISTOR);
		gyro = new AHRS(SPI.Port.kMXP);
		left = new DriveSide(RobotMap.LEFT_MOTOR_PORTS,RobotMap.LEFT_MOTOR_INVERSION, RobotMap.LEFT_ENCODER_INVERSION, RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B);
		right = new DriveSide(RobotMap.RIGHT_MOTOR_PORTS,RobotMap.RIGHT_MOTOR_INVERSION, RobotMap.RIGHT_ENCODER_INVERSION, RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B);
		left.setScaling(RobotMap.LEFT_SCALING);
		right.setScaling(RobotMap.RIGHT_SCALING);
		
		logger = Instrumentation.getLogger("Drive");
		logger.registerDouble("ProfileDistance", true, true);
		logger.registerDouble("ActualDistance", true, true);
		logger.registerDouble("ProfileAngle", true, true);
		logger.registerDouble("ActualAngle", true, true);
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
	public AHRS getGyro(){
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

	private void driveArcadeMode(double leftvert, double righthoriz) {
		right.setPower(leftvert - righthoriz);
		left.setPower(leftvert + righthoriz);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new ArcadeDrive());
	}
	
	public class DriveSide {
		
		HBRTalon[] motors;
		Encoder encoder;
		private double scaling;
		
		public DriveSide(int[] ports, boolean[] inverted, boolean sensInverted, int encA, int encB) {
			motors = new HBRTalon[4];
			encoder = new Encoder(encA, encB, sensInverted);
			for(int i = 0; i < 4; i++) {
				motors[i] = new HBRTalon(ports[i]);
				motors[i].setInverted(inverted[i]);
			}
//			motors[0].setSensorPhase(sensInverted);
		}
		
		public void setPower(double power) {
			for(int i = 0; i < 4; i++) {
				motors[i].set(ControlMode.PercentOutput, power);
			}
		}
		
		public void resetEncoder() {
//			motors[0].setSelectedSensorPosition(0, 0, 0);
			encoder.reset();
		}
		
		public double getSpeed() {
//			return motors[0].getSpeed(0);
			return encoder.getRate() / scaling;
		}
		
		public double getPosition() {
//			return motors[0].getPosition(0);
			return encoder.get() / scaling;
		}
		
		public void setScaling(double scaling) {
//			motors[0].setScaling(scaling);
			this.scaling = scaling;
		}
		
	}
	
	@Override
	public double[][] pidRead() {
		double[][] output = new double[2][2];
		output[1][0] = getAverageSpeed();
		output[0][0] = getAveragePosition();
		output[0][1] = (2 * Math.PI) * ((-getGyro().getAngle()) / 360);
		output[1][1] = (2 * Math.PI) * (-getGyro().getRate() / 360);
		return output;
	}

	@Override
	public void pidWrite(double[] output) {
		driveArcadeMode(output[0], -output[1]);
	}

	@Override
	public void internalVariablesWrite(double[] output) {
		logger.putDouble("ProfileDistance", output[0]);
		logger.putDouble("ActualDistance", output[1]);
		logger.putDouble("ProfileAngle", output[2]);
		logger.putDouble("ActualAngle", output[3]);
	}
}
