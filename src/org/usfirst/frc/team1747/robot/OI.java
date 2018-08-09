
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1747.robot;

import org.usfirst.frc.team1747.robot.commands.AutoIntake;
import org.usfirst.frc.team1747.robot.commands.AutoIntake2;
import org.usfirst.frc.team1747.robot.commands.ElevateDown;
import org.usfirst.frc.team1747.robot.commands.ElevateUp;
import org.usfirst.frc.team1747.robot.commands.ElevatorReset;
import org.usfirst.frc.team1747.robot.commands.Intake;
//import org.usfirst.frc.team1747.robot.commands.TeleopScaleForward;
import org.usfirst.frc.team1747.robot.commands.OpenClaw;
import org.usfirst.frc.team1747.robot.commands.ReleaseBuddyClimb;
import org.usfirst.frc.team1747.robot.commands.ScaleDrop2;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.drive.DriveCurve;
import org.usfirst.frc.team1747.robot.commands.drive.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.macro.ScaleDrop;
import org.usfirst.frc.team1747.robot.commands.macro.TeleopScaleBackward;
import org.usfirst.frc.team1747.robot.commands.macro.TeleopScaleForward;
import org.usfirst.frc.team1747.robot.commands.macro.TeleopSwitch;
import org.usfirst.frc.team1747.robot.commands.macro.TeleopSwitchHigh;
import org.usfirst.frc.team1747.robot.commands.reset.ResetElevatorEncoder;
import org.usfirst.frc.team1747.robot.commands.reset.ResetIndex;
import org.usfirst.frc.team1747.robot.commands.wrist.Outtake;
import org.usfirst.frc.team1747.robot.commands.wrist.WristDown;
import org.usfirst.frc.team1747.robot.commands.wrist.WristUp;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import com.tigerhuang.gambezi.OnUpdateListener;
import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Scheduler;
import lib.frc1747.controller.Logitech;
import lib.frc1747.controller.Xbox;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private static OI instance; Logitech driver, operator;
	public int index;
	
	private OI() {
		driver = new Logitech(RobotMap.DRIVER);
//		System.out.println(driver.get);
		operator = new Logitech(RobotMap.OPERATOR);
		
		index = 0;
		
		createDriver();
		createOperator();
		
		DriveProfile drive10ft = new DriveProfile("/home/lvuser/10ft.csv");
		DriveProfile curve_left = new DriveProfile("/home/lvuser/curve_left.csv");
		DriveProfile curve_right = new DriveProfile("/home/lvuser/curve_right.csv");
		DriveProfile s_curve_left = new DriveProfile("/home/lvuser/S-Curve-Left.csv");
		DriveProfile s_curve_right = new DriveProfile("/home/lvuser/S-Curve-Right.csv");
		
		GambeziDashboard.set_boolean("AutonStop", false);
		GambeziDashboard.listen_button("AutonStop", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Robot.fatalError("Auton is now dead");
				IntakeSubsystem.getInstance().setLED(true);
			}
		});
		
		GambeziDashboard.listen_button("Commands2/No", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Scheduler.getInstance().add(drive10ft);
			}
		});
		
		GambeziDashboard.set_boolean("Commands2/DriveForward", false);
		GambeziDashboard.listen_button("Commands2/DriveForward", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Scheduler.getInstance().add(drive10ft);
			}
		});

		GambeziDashboard.set_boolean("Commands2/CurveLeft", false);
		GambeziDashboard.listen_button("Commands2/CurveLeft", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Scheduler.getInstance().add(curve_left);
				System.out.println("Testing");
			}
		});
		GambeziDashboard.set_boolean("Commands2/CurveRight", false);
		GambeziDashboard.listen_button("Commands2/CurveRight", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Scheduler.getInstance().add(curve_right);
			}
		});
		GambeziDashboard.set_boolean("Commands2/S-Curve_left", false);
		GambeziDashboard.listen_button("Commands2/S-Curve_left", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Scheduler.getInstance().add(s_curve_left);
				System.out.println("S-Curve_left");
			}
		});
		GambeziDashboard.set_boolean("Commands2/S-Curve_right", false);
		GambeziDashboard.listen_button("Commands2/S-Curve_right", new OnUpdateListener() {
			@Override
			public void on_update(Object arg0) {
				Scheduler.getInstance().add(s_curve_right);
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
		driver.getButton(Logitech.LT).whenPressed(new AutoIntake());
		driver.getButton(Logitech.LB).whileHeld(new OpenClaw());	
		driver.getButton(Logitech.RT).whileHeld(new AutoIntake2());
		driver.getButton(Logitech.RB).whileHeld(new Intake());
		
		driver.getDPad(Logitech.UP).whenPressed(new WristUp());
		//driver.getDPad(Logitech.UP).whenPressed(new DriveProfile("/home/lvuser/SCLrT.csv"));
		driver.getDPad(Logitech.DOWN).whenPressed(new WristDown());
		
		driver.getButton(Logitech.X).whenPressed(new ResetIndex(new ElevatorReset()));
		driver.getButton(Logitech.A).whenPressed(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.LOWEST_SCALE));
		driver.getButton(Logitech.Y).whenPressed(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP));
		driver.getButton(Logitech.B).whileHeld(new Outtake(0.3));
		
		driver.getDPad(Logitech.LEFT).whenPressed(new TeleopScaleForward());
		driver.getDPad(Logitech.RIGHT).whenPressed(new TeleopSwitch());
		
		//driver.getButton(Logitech.START).whenPressed(new Drive2Cube(.1, 0));
		driver.getButton(Logitech.START).whenPressed(new TeleopScaleBackward());
		driver.getButton(Logitech.BACK).whileHeld(new ScaleDrop2());
	}
	
	public void createOperator(){
		operator.getButton(Logitech.A).whenPressed(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
		operator.getButton(Logitech.B).whenPressed(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.LOW_SCALE));
		operator.getButton(Logitech.Y).whenPressed(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP));
		operator.getButton(Logitech.X).whenPressed(new ResetIndex(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)));
		operator.getButton(Logitech.RB).whileHeld(new Intake());
		operator.getButton(Logitech.RT).whileHeld(new Outtake(0));
		operator.getButton(Logitech.LB).whenPressed(new WristUp());
		operator.getButton(Logitech.LT).whenPressed(new WristDown());
		operator.getButton(Logitech.START).whileHeld(new ElevateDown());
		operator.getDPad(Logitech.RIGHT).whenPressed(new TeleopSwitch());
		operator.getDPad(Logitech.LEFT).whenPressed(new TeleopScaleForward());
		operator.getButton(Logitech.BACK).whileHeld(new ElevateUp());
//		operator.getDPad(Logitech.UP).whenPressed(new TeleopSwitchHigh());
		operator.getDPad(Logitech.UP).whenPressed(new DriveProfile("/home/lvuser/S-Curve-Left.csv"));
//		operator.getDPad(Logitech.DOWN).whenPressed(new DriveCurve(2.3, 0));
		operator.getDPad(Logitech.DOWN).whileHeld(new ReleaseBuddyClimb());
	}
	
	public Logitech getDriver() {
		return driver;
	}
	
	public Logitech getOperator(){
		return operator;
	}
}
