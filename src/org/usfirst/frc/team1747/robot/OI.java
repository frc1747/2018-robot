/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1747.robot;

import org.usfirst.frc.team1747.robot.commands.ArcadeDrive;
import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.ClimbDown;
import org.usfirst.frc.team1747.robot.commands.ClimbUp;
import org.usfirst.frc.team1747.robot.commands.DriveCurve;
import org.usfirst.frc.team1747.robot.commands.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.ElevateDown;
import org.usfirst.frc.team1747.robot.commands.ElevateUp;
import org.usfirst.frc.team1747.robot.commands.ElevatorBottom;
import org.usfirst.frc.team1747.robot.commands.ElevatorDown;
import org.usfirst.frc.team1747.robot.commands.ElevatorSwitch;
import org.usfirst.frc.team1747.robot.commands.ElevatorTop;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.OpenClaw;
import org.usfirst.frc.team1747.robot.commands.Outtake;
import org.usfirst.frc.team1747.robot.commands.TestDown;
import org.usfirst.frc.team1747.robot.commands.TestUp;
import org.usfirst.frc.team1747.robot.commands.WristDown;
import org.usfirst.frc.team1747.robot.commands.WristUp;
import org.usfirst.frc.team1747.robot.commands.ZeroedSensorDriveCurve;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;

import com.tigerhuang.gambezi.OnUpdateListener;
import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Scheduler;
import lib.frc1747.controller.Logitech;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private static OI instance; Logitech driver;
	
	private OI() {
		driver = new Logitech(RobotMap.DRIVER);
		
		createDriver();
		
		DriveProfile drive10ft = new DriveProfile("/home/lvuser/10ft.csv");
		DriveProfile curve_test_left = new DriveProfile("/home/lvuser/curve_test_left.csv");
		DriveProfile curve_test_right = new DriveProfile("/home/lvuser/curve_test_right.csv");
		DriveProfile s_curve_left = new DriveProfile("/home/lvuser/S-Curve-Left.csv");
		
		GambeziDashboard.listen_button("Commands/Drive10ft", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Scheduler.getInstance().add(drive10ft);
			}
		});
		
		GambeziDashboard.listen_button("Commands/CurveTestLeft", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Scheduler.getInstance().add(curve_test_left);
			}
		});
		GambeziDashboard.listen_button("Commands/CurveTestRight", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Scheduler.getInstance().add(curve_test_right);
			}
		});
		GambeziDashboard.listen_button("Commands/S-Curve_left", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Scheduler.getInstance().add(s_curve_left);
			}
		});
	}
	
	public static OI getInstance() {
		
		if(instance == null) {
			
			instance = new OI();
		}
		return instance;
	}
	
	public void createDriver() {
		
		driver.getButton(Logitech.X).whenPressed(new ElevatorBottom());
		driver.getButton(Logitech.B).whenPressed(new ElevatorSwitch());
		driver.getButton(Logitech.Y).whenPressed(new WristUp());
		driver.getButton(Logitech.A).whenPressed(new WristDown());
//		driver.getButton(Logitech.UP).whileHeld(new ClimbUp());
//		driver.getButton(Logitech.DOWN).whileHeld(new ClimbDown());
		driver.getButton(Logitech.RB).whileHeld(new Intake());
		driver.getButton(Logitech.RT).whileHeld(new Outtake());
		driver.getButton(Logitech.LB).whileHeld(new OpenClaw());
		
		//Test Commands for wrist and elevator without PID loops
//		driver.getButton(Logitech.Y).whileHeld(new TestUp());
//		driver.getButton(Logitech.A).whileHeld(new TestDown());
//		driver.getButton(Logitech.RT).whenPressed(new ElevateDown());
//		driver.getButton(Logitech.B).whileHeld(new ElevateUp());
//		driver.getButton(Logitech.X).whileHeld(new TestUp());
//		driver.getDpad(Logitech.DOWN).whileHeld(new TestDown());

	}
	
	public Logitech getDriver() {
		return driver;
	}
}
