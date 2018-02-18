/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1747.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import java.util.logging.Level;

import org.usfirst.frc.team1747.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

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
	int counter = 0;
	@Override
	public void robotInit() {
		initSubsystems();
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
		Scheduler.getInstance().run();
		GambeziDashboard.set_double("Robot/Battery_Voltage", RobotController.getBatteryVoltage());
		GambeziDashboard.set_double("Robot/Counter", counter++);
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
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		GambeziDashboard.set_double("Robot/Battery_Voltage", RobotController.getBatteryVoltage());
		GambeziDashboard.set_double("Robot/Counter", counter++);
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
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
	}
	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	
	}
	
	public void initSubsystems(){
		GambeziDashboard.set_double("Elevator/kF", 0.04); //0.04
		GambeziDashboard.set_double("Elevator/kV", 0.0066); //0
    	GambeziDashboard.set_double("Elevator/kA", 0.0009); //0
    	GambeziDashboard.set_double("Elevator/kP", 0.01); //0.025
    	GambeziDashboard.set_double("Elevator/kI", 0.00); //0.005
    	GambeziDashboard.set_double("Elevator/kD", 0); //0
		GambeziDashboard.set_double("Wrist/kF", 0.35);
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
    	
    	// NO! Do not do this anywhere else
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
		drive = DriveSubsystem.getInstance();
		intake = IntakeSubsystem.getInstance();
		elevator = ElevatorSubsystem.getInstance();
		climber = ClimberSubsystem.getInstance();
		OI.getInstance();
	}
	
	public enum AutonRobotPosition{
		LEFT, CENTER, RIGHT;
	}
}
