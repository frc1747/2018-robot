package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

//import com.frc1747.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.controller.Logitech;
import lib.frc1747.instrumentation.Instrumentation;
import lib.frc1747.instrumentation.Logger;
import lib.frc1747.subsytems.HBRSubsystem;

/**
 *
 */
public class DriveWithJoysticks extends Command {
	private DriveSubsystem drivetrain;
	
	private final double s_v_max = 18;
	private final double a_v_max = 17.28;
	
	Logger logger;
	
	double speedSetpoint;
	double angleSetpoint;

    public DriveWithJoysticks() {
    	requires(drivetrain = DriveSubsystem.getInstance());
    	setInterruptible(true);
    	logger = Instrumentation.getLogger("Robot");
    	logger.registerDouble("Left Speed", true, true);
		logger.registerDouble("Right Speed", true, true);
	  	logger.registerDouble("Left Position", true, true);
		logger.registerDouble("Right Position", true, true);
	 	logger.registerDouble("Left Acceleration", true, true);
		logger.registerDouble("Right Acceleration", true, true);
		logger.registerDouble("Drive Angle", true, true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//setup velocity PID
    	drivetrain.setMode(DriveSubsystem.Follower.DISTANCE, HBRSubsystem.Mode.PID);
    	drivetrain.setPIDMode(DriveSubsystem.Follower.DISTANCE, HBRSubsystem.PIDMode.VELOCITY);
    	drivetrain.setILimit(DriveSubsystem.Follower.DISTANCE, 0);
    	drivetrain.setFeedforward(DriveSubsystem.Follower.DISTANCE, 0, 1 / s_v_max, 0);
    	drivetrain.setFeedback(DriveSubsystem.Follower.DISTANCE, 0, 0, 0);
    	drivetrain.resetIntegrator(DriveSubsystem.Follower.DISTANCE);
    	
    	//setup angle PID
    	drivetrain.setMode(DriveSubsystem.Follower.ANGLE, HBRSubsystem.Mode.PID);
    	drivetrain.setPIDMode(DriveSubsystem.Follower.ANGLE, HBRSubsystem.PIDMode.VELOCITY);
    	drivetrain.setILimit(DriveSubsystem.Follower.ANGLE, 0);
    	drivetrain.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, 0.6 / a_v_max, 0);
    	drivetrain.setFeedback(DriveSubsystem.Follower.ANGLE, 0, 0, 0);
		drivetrain.resetIntegrator(DriveSubsystem.Follower.ANGLE);
		
		drivetrain.setEnabled(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	speedSetpoint = s_v_max * OI.getInstance().getDriver().getAxis(Logitech.LEFT_VERTICAL);
    	drivetrain.setSetpoint(DriveSubsystem.Follower.DISTANCE, speedSetpoint);
    	
    	
    	angleSetpoint = -a_v_max * OI.getInstance().getDriver().getAxis(Logitech.RIGHT_HORIZONTAL);
    	drivetrain.setSetpoint(DriveSubsystem.Follower.ANGLE, angleSetpoint);
    	
    	logger.putDouble("Left Speed", drivetrain.getLeftSpeed());
		logger.putDouble("Right Speed", drivetrain.getRightSpeed());
	 	logger.putDouble("Left Position", drivetrain.getLeftPosition());
		logger.putDouble("Right Position", drivetrain.getRightPosition());
		logger.putDouble("Left Acceleration", drivetrain.getLeftAcceleration());
		logger.putDouble("Right Acceleration", drivetrain.getRightAcceleration());
		logger.putDouble("Drive Angle", drivetrain.getGyro().getAngle());
		//GambeziDashboard.set_double("gambezi/log", drivetrain.getRightSpeed());
		//System.out.println(drivetrain.getGyro().getAngle());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setEnabled(false);
    	drivetrain.setLeftPower(0);
    	drivetrain.setRightPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
