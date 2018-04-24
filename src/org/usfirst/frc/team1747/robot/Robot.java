/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1747.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lib.frc1747.controller.Logitech;
import lib.frc1747.subsytems.HBRSubsystem;

import java.util.logging.Level;

import org.usfirst.frc.team1747.robot.commands.Autonomous;
import org.usfirst.frc.team1747.robot.commands.Autonomous2;

//import java.util.logging.Level;

import org.usfirst.frc.team1747.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

//import com.frc1747.commands.auton.AutonTemplate;
//import com.frc1747.Robot.Autons;
//import com.frc1747.OI;
//import com.frc1747.commands.auton.AutonTemplate;
import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	DriveSubsystem drive;
	IntakeSubsystem intake;
	ElevatorSubsystem elevator;
	ClimberSubsystem climber;
	RobotType botType;
	Command auton;
	int counter = 0;
	boolean aButtonState, bButtonState;
	int index;
	AutonRobotPosition [] modes;
	
	Command autonChoice;
	boolean xButtonState, yButtonState;
	int index2;
	AutonChoice [] states;
	
	public class Interval implements Runnable {
		int counter2 = 0;
		
		@Override
		public void run() {
			for(;;) {
				try {
					Thread.sleep(20);
				}
				catch(InterruptedException ex) {
					ex.printStackTrace();
				}
				//GambeziDashboard.set_double("Robot/Counter2", counter2++);
				//System.out.println("Counter2: " + counter2++);
			}
		}
	}
	
	@Override
	public void robotInit() {
		initSubsystems();
		
		Thread thread = new Thread(new Interval());
		thread.start();
		
		aButtonState = false;
		bButtonState = false;
		
		xButtonState = false;
		yButtonState = false;
		
		modes = AutonRobotPosition.class.getEnumConstants();
		index = 1;
		states = AutonChoice.class.getEnumConstants();
		index2 = 1;
		
		DriveSubsystem.getInstance().getGyro2().calibrate();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		//for robot position
		if(!aButtonState && OI.getInstance().getDriver().getButton(Logitech.A).get()){
			nextAuton();
		}
		aButtonState = OI.getInstance().getDriver().getButton(Logitech.A).get();
		if(!bButtonState && OI.getInstance().getDriver().getButton(Logitech.B).get()){
			prevAuton();
		}
		bButtonState = OI.getInstance().getDriver().getButton(Logitech.B).get();
		Scheduler.getInstance().run();
		GambeziDashboard.set_string("auton/pos", modes[index].toString());
		SmartDashboard.putString("AutonPos", modes[index].toString());
		//for strategy choice
		if(!xButtonState && OI.getInstance().getDriver().getButton(Logitech.X).get()){
			nextChoice();
		}
		xButtonState = OI.getInstance().getDriver().getButton(Logitech.X).get();
		if(!yButtonState && OI.getInstance().getDriver().getButton(Logitech.Y).get()){
			prevChoice();
		}
		yButtonState = OI.getInstance().getDriver().getButton(Logitech.Y).get();
		Scheduler.getInstance().run();
		GambeziDashboard.set_double("Robot/Battery_Voltage", RobotController.getBatteryVoltage());
		GambeziDashboard.set_double("Robot/Counter", counter++);
		GambeziDashboard.set_string("auton/choice", states[index2].toString());
		SmartDashboard.putString("AutonChoice", states[index2].toString());
		GambeziDashboard.set_double("Robot/thermistor", drive.getTempF());
		GambeziDashboard.set_double("Robot/Brownout", RobotController.isBrownedOut() ? 1 : 0);
		GambeziDashboard.set_double("Robot/wristPosition", elevator.getWristPosition());

		GambeziDashboard.set_double("navx/yaw", DriveSubsystem.getInstance().getGyro().getYaw());
		GambeziDashboard.set_double("navx/rawgyroz", DriveSubsystem.getInstance().getGyro().getRawGyroZ());
		GambeziDashboard.set_double("navx/rate", DriveSubsystem.getInstance().getGyro().getRate());
		GambeziDashboard.set_double("navx/angle", DriveSubsystem.getInstance().getGyro().getAngle());
		GambeziDashboard.set_boolean("navx/connected", DriveSubsystem.getInstance().getGyro().isConnected());
		GambeziDashboard.set_double("gyro2/angle", DriveSubsystem.getInstance().getGyro2().getAngle());
		GambeziDashboard.set_double("gyro2/rate", DriveSubsystem.getInstance().getGyro2().getRate());

		GambeziDashboard.set_double("Robot/voltage5v", RobotController.getVoltage5V());
		GambeziDashboard.set_double("Robot/current5v", RobotController.getCurrent5V());
		GambeziDashboard.set_double("Robot/voltage3v3", RobotController.getVoltage3V3());
		GambeziDashboard.set_double("Robot/current3v3", RobotController.getCurrent3V3());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		elevator.setElevatorStage(0);
//		GambeziDashboard.get_double("auton/start_pos", );
		//(auton = new Autonomous(AutonRobotPosition.RIGHT)).start();
		(auton = new Autonomous2(modes[index], states[index2])).start();
//		(autonChoice = new Autonomous(states[index2])).start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		GambeziDashboard.set_double("Robot/Battery_Voltage", RobotController.getBatteryVoltage());
		GambeziDashboard.set_double("Robot/Counter", counter++);
		GambeziDashboard.set_double("Robot/thermistor", drive.getTempF());
		GambeziDashboard.set_double("Robot/Brownout", RobotController.isBrownedOut() ? 1 : 0);

		GambeziDashboard.set_double("navx/yaw", DriveSubsystem.getInstance().getGyro().getYaw());
		GambeziDashboard.set_double("navx/rawgyroz", DriveSubsystem.getInstance().getGyro().getRawGyroZ());
		GambeziDashboard.set_double("navx/rate", DriveSubsystem.getInstance().getGyro().getRate());
		GambeziDashboard.set_double("navx/angle", DriveSubsystem.getInstance().getGyro().getAngle());
		GambeziDashboard.set_double("gyro2/angle", DriveSubsystem.getInstance().getGyro2().getAngle());
		GambeziDashboard.set_double("gyro2/rate", DriveSubsystem.getInstance().getGyro2().getRate());
	}

	@Override
	public void teleopInit() {
		Scheduler.getInstance().removeAll();
		elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.PID);
		elevator.setSetpoint(ElevatorSubsystem.Follower.ELEVATOR, elevator.getElevatorPosition());
	}
	
	/**
	 * This function is called periodically during operator control.
	 */
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//logger.putDouble("Battery voltage", RobotController.getBatteryVoltage());
		GambeziDashboard.set_double("Intake/IntakeSensor", intake.getSwitch());
		GambeziDashboard.set_double("Robot/Battery_Voltage", RobotController.getBatteryVoltage());
		GambeziDashboard.set_double("Robot/Counter", counter++);
		GambeziDashboard.set_double("Robot/elevatorPosition", elevator.getElevatorPosition());
		GambeziDashboard.set_double("Robot/wristPosition", elevator.getWristPosition());
		GambeziDashboard.set_double("Robot/thermistor", drive.getTempF());
		GambeziDashboard.set_double("Robot/Brownout", RobotController.isBrownedOut() ? 1 : 0);

		GambeziDashboard.set_double("navx/yaw", DriveSubsystem.getInstance().getGyro().getYaw());
		GambeziDashboard.set_double("navx/rawgyroz", DriveSubsystem.getInstance().getGyro().getRawGyroZ());
		GambeziDashboard.set_double("navx/rate", DriveSubsystem.getInstance().getGyro().getRate());
		GambeziDashboard.set_double("navx/angle", DriveSubsystem.getInstance().getGyro().getAngle());
		GambeziDashboard.set_double("gyro2/angle", DriveSubsystem.getInstance().getGyro2().getAngle());
		GambeziDashboard.set_double("gyro2/rate", DriveSubsystem.getInstance().getGyro2().getRate());
	}
	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	
	}
	
	public void initSubsystems(){
		try { GambeziDashboard.get_instance(); } catch(Exception ex) {}
		try { GambeziDashboard.get_instance(); } catch(Exception ex) {}
		try { GambeziDashboard.get_instance(); } catch(Exception ex) {}
		try { GambeziDashboard.get_instance(); } catch(Exception ex) {}
		try { GambeziDashboard.get_instance(); } catch(Exception ex) {}

    	// NO! Do not do this anywhere else
		try {
			Thread.sleep(100);
		}
		catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
    	GambeziDashboard.set_double("Intake/OutPower", -0.8);
		GambeziDashboard.set_double("Elevator/kF", 0.04); //0.04
		GambeziDashboard.set_double("Elevator/kV", 0.0068); //0.0066
    	GambeziDashboard.set_double("Elevator/kA", 0.0009); //0
    	GambeziDashboard.set_double("Elevator/kP", 0.18); //0.1
    	GambeziDashboard.set_double("Elevator/kI", 0.00); //0.005
    	GambeziDashboard.set_double("Elevator/kD", 0); //0
		GambeziDashboard.set_double("Wrist/kF", 0.07);
    	GambeziDashboard.set_double("Wrist/kA", 0);
    	GambeziDashboard.set_double("Wrist/kV", 0);
    	GambeziDashboard.set_double("Wrist/kP", 0.55);
    	GambeziDashboard.set_double("Wrist/kI", 0);
    	GambeziDashboard.set_double("Wrist/kD", 0);
    	
    	GambeziDashboard.set_double("Drive/Distance/kA", 0.02);
    	GambeziDashboard.set_double("Drive/Distance/kV", 0.075);
    	GambeziDashboard.set_double("Drive/Distance/kP", 1);
    	GambeziDashboard.set_double("Drive/Distance/kI", 0);
    	GambeziDashboard.set_double("Drive/Distance/kD", 0);
    	GambeziDashboard.set_double("Drive/Angle/kA", 0.02);
    	GambeziDashboard.set_double("Drive/Angle/kV", 0.18);
    	GambeziDashboard.set_double("Drive/Angle/kP", 1.7);
    	GambeziDashboard.set_double("Drive/Angle/kI", 0.0);
    	GambeziDashboard.set_double("Drive/Angle/kD", 0.07);
//    	GambeziDashboard.set_string("auton/start_pos", "RIGHT");
    	
    	GambeziDashboard.set_string("auton/gamedata", "LLL");
    	SmartDashboard.putString("AutonGameData", "LLL");
    	
    	// NO! Do not do this anywhere else
		try {
			Thread.sleep(100);
		}
		catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
		botType = RobotType.getInstance();
		drive = DriveSubsystem.getInstance();
		intake = IntakeSubsystem.getInstance();
		elevator = ElevatorSubsystem.getInstance();
		climber = ClimberSubsystem.getInstance();
		OI.getInstance();
	}
	
	public enum AutonRobotPosition{
		CENTER, LEFT, RIGHT;
	}
	
	public enum AutonChoice{
		SCALE_SCALE, SCALE_SWITCH, COMPLIANT_SCALE;
	}
	// Auton Chooser handling
    public void nextAuton() {
    	
    	index++;
    	if(index >= modes.length) index -= modes.length;
    }
    
    public void prevAuton() {
    	
    	index--;
    	if(index < 0) index += modes.length;
    }
    
    public void nextChoice() {
    	
    	index2++;
    	if(index2 >= states.length) index2 -= states.length;
    }
    
    public void prevChoice() {
    	
    	index2--;
    	if(index2 < 0) index2 += states.length;
    }
    
    public static void fatalError(String message) {
    	Scheduler.getInstance().removeAll();
    	System.out.println("FATAL ERROR: " + message);
    	GambeziDashboard.log_string("FATAL ERROR: " + message);
    }
}
