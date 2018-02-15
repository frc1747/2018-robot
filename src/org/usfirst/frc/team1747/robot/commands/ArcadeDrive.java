package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.controller.Logitech;
//import lib.frc1747.instrumentation.Instrumentation;
//import lib.frc1747.instrumentation.Logger;

public class ArcadeDrive extends Command {
	
	DriveSubsystem drivetrain;
	Logitech driver;
	
	public ArcadeDrive() {
		// Use requires() here to declare subsystem dependencies
		drivetrain = DriveSubsystem.getInstance();
		requires(drivetrain);
		setInterruptible(true);
		driver = OI.getInstance().getDriver();
		/*logger.registerDouble("Temp", true, true);
		logger.registerDouble("Left Speed", true, true);
		logger.registerDouble("Right Speed", true, true);*/
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
		/*logger.putDouble("Temp", drivetrain.getTempF());
		logger.putDouble("Left Speed", drivetrain.getLeftSpeed());
		logger.putDouble("Right Speed", drivetrain.getRightSpeed());*/
		
		GambeziDashboard.set_double("Drive/Temp", drivetrain.getTempF());
		GambeziDashboard.set_double("Drive/Left_Speed", drivetrain.getLeftSpeed());
		GambeziDashboard.set_double("Drive/Right_Speed", drivetrain.getRightSpeed());

		
		
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
