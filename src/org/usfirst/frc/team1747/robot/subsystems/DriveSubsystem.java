package org.usfirst.frc.team1747.robot.subsystems;

import lib.frc1747.speed_controller.HBRTalon;
import lib.frc1747.subsytems.HBRSubsystem;

import org.usfirst.frc.team1747.robot.Robot;
import org.usfirst.frc.team1747.robot.RobotMap;
import org.usfirst.frc.team1747.robot.RobotType;
import org.usfirst.frc.team1747.robot.commands.drive.ArcadeDrive;
import org.usfirst.frc.team1747.robot.commands.drive.DriveWithJoysticks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem extends HBRSubsystem<DriveSubsystem.Follower> {

	DriveSide left, right;

	private static DriveSubsystem drivetrain;
	
	private AnalogInput thermistor;
	
	private AHRS gyro;
	
	private ADXRS450_Gyro gyro2;
	
	private int counter;
	
	public enum Follower {
		DISTANCE, ANGLE
	}
	
	private DriveSubsystem() {
		super(RobotMap.DT);
		thermistor = new AnalogInput(RobotMap.FUSE_THERMISTOR);
		gyro = new AHRS(SPI.Port.kMXP);
		gyro2 = new ADXRS450_Gyro();
		left = new DriveSide(RobotMap.LEFT_MOTOR_PORTS,RobotMap.LEFT_MOTOR_INVERSION, RobotMap.LEFT_ENCODER_INVERSION, RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B);
		right = new DriveSide(RobotMap.RIGHT_MOTOR_PORTS,RobotMap.RIGHT_MOTOR_INVERSION, RobotMap.RIGHT_ENCODER_INVERSION, RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B);
		if(!RobotType.getInstance().getJumper().get()){
			left.setScaling(RobotMap.LEFT_SCALING);
			right.setScaling(RobotMap.RIGHT_SCALING);
    	}else{
    		left.setScaling(RobotMap.LEFT_PRACTICE_SCALING);
    		right.setScaling(RobotMap.RIGHT_PRACTICE_SCALING);
    	}
				
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
	public ADXRS450_Gyro getGyro2(){
		return gyro2;
	}
	//in degrees C
	public double getTempC(){
		return (thermistor.getVoltage()-0.1555)/0.0442;
	}
	//in degrees F
	public double getTempF(){
		return 32+(((thermistor.getVoltage()-0.1555)/0.0442)*9/5);
	}
	//degrees K
	public double getTempK(){
		return 273+(thermistor.getVoltage()-0.1555)/0.0442;
	}
	//degrees R
	public double getTempR(){
		return getTempF() + 459.67;
	}
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
	public double getLeftAcceleration(){
		return left.getAcceleretion();
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
	public double getRightAcceleration(){
		return right.getAcceleretion();
	}

	private void driveArcadeMode(double leftvert, double righthoriz) {
		right.setPower(leftvert - righthoriz);
		left.setPower(leftvert + righthoriz);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}
	
	public class DriveSide {
		
		HBRTalon talon;
		VictorSPX[] motors;
		Encoder encoder;
		private double scaling;
		private double velocity;
		private double acceleration;
		
		public DriveSide(int[] ports, boolean[] inverted, boolean sensInverted, int encA, int encB) {
			velocity = 0;
			motors = new VictorSPX[3];
			talon = new HBRTalon(ports[0]);
			talon.setInverted(inverted[0]);
			encoder = new Encoder(encA, encB, sensInverted);
			for(int i = 0; i < 3; i++) {
				motors[i] = new VictorSPX(ports[i + 1]);
				motors[i].setInverted(inverted[i + 1]);
			}
		}
		
		public void setPower(double power) {
			talon.set(ControlMode.PercentOutput, power);
			for(int i = 0; i < 3; i++) {
				motors[i].set(ControlMode.PercentOutput, power);
//				victor 4 might go bad soon
			}			
		}
		
		public void resetEncoder() {
			encoder.reset();
		}
		
		public double getSpeed() {
			return encoder.getRate() / scaling;
		}
		
		public double getAcceleretion(){
			double oldVelocity = velocity;
			velocity = getSpeed();
			double oldAcceleration = acceleration;
			acceleration = (velocity - oldVelocity)/0.01;
			acceleration = 0.95*oldAcceleration + (0.05)*acceleration;
			return acceleration;
		}
		
		public double getPosition() {
			return encoder.get() / scaling;
		}
		
		public void setScaling(double scaling) {
			this.scaling = scaling;
		}
		
	}
	
	@Override
	public double[][] pidRead() {
		double[][] output = new double[2][2];
		output[0][0] = getAveragePosition();
		output[1][0] = getAverageSpeed();
		output[0][1] = (2 * Math.PI) * ((-getGyro().getAngle()) / 360);
		output[1][1] = (2 * Math.PI) * (getGyro().getRawGyroZ() / 360);
		
		return output;
	}

	@Override
	public void pidWrite(double[] output) {
		//GambeziDashboard.set_double("Drive/output[0]", output[0]);
		GambeziDashboard.set_double("Drive/angleOutput", output[1]);
		driveArcadeMode(output[0], -output[1]);
	}

	@Override
	public void internalVariablesWrite(double[] output) {
		GambeziDashboard.set_double("Drive/Distance/Profile", output[0]);
		GambeziDashboard.set_double("Drive/Distance/Actual", output[1]);
		GambeziDashboard.set_double("Drive/Angle/Profile", output[2]);
		GambeziDashboard.set_double("Drive/Angle/Actual", output[3]);
		GambeziDashboard.set_double("Drive/counter", counter++);
		
		
		SmartDashboard.putNumber("Drive/Distance/Profile", output[0]);
		SmartDashboard.putNumber("Drive/Distance/Actual", output[1]);
		SmartDashboard.putNumber("Drive/Angle/Profile", output[2]);
		SmartDashboard.putNumber("Drive/Angle/Actual", output[3]);
		SmartDashboard.putNumber("Drive/counter", counter++);
		SmartDashboard.updateValues();
	}

	@Override
	public void errorsWrite(double[] error) {
		if(DriverStation.getInstance().isAutonomous() && RobotMap.ENABLE_AUTON_ERROR_DETECTION) {
			if(Math.abs(error[0]) >= RobotMap.DRIVE_LINEAR_MAX_ERROR_POWER) {
				Robot.fatalError("Linear drive encoder fault>>"
						+ " Left Encoder: " + getLeftPosition() + " Right Encoder: " + getRightPosition()
						+ " Error: " + error[0]);
			}
			if(Math.abs(error[1]) >= RobotMap.DRIVE_ANGULAR_MAX_ERROR_POWER) {
				Robot.fatalError("Angular drive gyro fault>>"
						+ " Gyro Angle: " + (2 * Math.PI) * ((-getGyro().getAngle()) / 360)
						+ " Error: " + error[1]);
			}
		}
	}
}
