package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.Robot;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.controller.Logitech;

public class ArcadeDrive extends Command {
	
	DriveSubsystem drivetrain;
	Logitech driver;
	
	public ArcadeDrive() {
		// Use requires() here to declare subsystem dependencies
		drivetrain = DriveSubsystem.getInstance();
		requires(drivetrain);
		setInterruptible(true);
		driver = OI.getInstance().getDriver();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		drivetrain.setLeftPower(driver.getAxis(Logitech.LEFT_VERTICAL) + driver.getAxis(Logitech.RIGHT_HORIZONTAL));
		drivetrain.setRightPower(driver.getAxis(Logitech.LEFT_VERTICAL) - driver.getAxis(Logitech.RIGHT_HORIZONTAL));
//		System.out.println(drivetrain.getLeftPosition(0, 0));
		System.out.println(drivetrain.getTempF());
		
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		drivetrain.setLeftPower(0.0);
		drivetrain.setRightPower(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}

}
