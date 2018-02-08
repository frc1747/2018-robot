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
import org.usfirst.frc.team1747.robot.commands.ElevateDown;
import org.usfirst.frc.team1747.robot.commands.ElevateUp;
import org.usfirst.frc.team1747.robot.commands.ElevatorDown;
import org.usfirst.frc.team1747.robot.commands.ElevatorUp;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.OpenIntake;
import org.usfirst.frc.team1747.robot.commands.Outtake;
import org.usfirst.frc.team1747.robot.commands.TestDown;
import org.usfirst.frc.team1747.robot.commands.TestUp;
import org.usfirst.frc.team1747.robot.commands.WristDown;
import org.usfirst.frc.team1747.robot.commands.WristUp;
import org.usfirst.frc.team1747.robot.commands.ZeroedSensorDriveCurve;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;

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
	}
	
	public static OI getInstance() {
		
		if(instance == null) {
			
			instance = new OI();
		}
		return instance;
	}
	
	public void createDriver() {
		driver.getButton(Logitech.RT).whenPressed(new ElevateUp());
		driver.getButton(Logitech.RB).whenPressed(new ElevateDown());
		driver.getButton(Logitech.Y).whenPressed(new TestUp());
		driver.getButton(Logitech.A).whenPressed(new TestDown());
		driver.getButton(Logitech.X).whenPressed(new Intake());
		driver.getButton(Logitech.B).whenPressed(new Outtake());
		driver.getButton(Logitech.UP).whileHeld(new ClimbUp());
		driver.getButton(Logitech.DOWN).whileHeld(new ClimbDown());
		driver.getButton(Logitech.LB).whileHeld(new OpenIntake());
		//driver.getButton(Logitech.X).whenPressed(new ZeroedSensorDriveCurve(4,-60));
		//driver.getButton(Logitech.X).whenPressed(new ResetEncoders());
		//driver.getButton(Logitech.Y).whileHeld(new AutonOutake());
		//driver.getButton(Logitech.A).whileHeld(new IntakeIn());
		//driver.getButton(Logitech.B).whileHeld(new ClimbDown());
		//driver.getButton(Logitech.Y).whileHeld(new ClimbUp());
		//driver.getDPADButton(0);
	}
	
	public Logitech getDriver() {
		return driver;
	}
}
