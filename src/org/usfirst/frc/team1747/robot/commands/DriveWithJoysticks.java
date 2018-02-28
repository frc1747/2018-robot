package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.RobotMap;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

//import com.frc1747.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.controller.Logitech;
//import lib.frc1747.instrumentation.Instrumentation;
//import lib.frc1747.instrumentation.Logger;
import lib.frc1747.subsytems.HBRSubsystem;

/**
 *
 */
public class DriveWithJoysticks extends Command {
	private DriveSubsystem drivetrain;
	
	private ElevatorSubsystem elevator = ElevatorSubsystem.getInstance();
	
	private final double s_v_max = 18;
	private final double a_v_max = 8.6;
	
	private final double lowFilter = -0.005;			//TODO: Tune these values
	private final double highFilter = -0.0075;

	private final double a_kp = -0.02;
	
	double speedSetpoint;
	double angleSetpoint;

    public DriveWithJoysticks() {
    	requires(drivetrain = DriveSubsystem.getInstance());
    	setInterruptible(true);
    	/*logger.registerDouble("Left Speed", true, true);
		logger.registerDouble("Right Speed", true, true);
	  	logger.registerDouble("Left Position", true, true);
		logger.registerDouble("Right Position", true, true);
	 	logger.registerDouble("Left Acceleration", true, true);
		logger.registerDouble("Right Acceleration", true, true);
		logger.registerDouble("Drive Angle", true, true);*/
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//setup velocity PID
    	drivetrain.setMode(DriveSubsystem.Follower.DISTANCE, HBRSubsystem.Mode.PID);
    	drivetrain.setPIDMode(DriveSubsystem.Follower.DISTANCE, HBRSubsystem.PIDMode.VELOCITY);
    	drivetrain.setILimit(DriveSubsystem.Follower.DISTANCE, 0);
    	drivetrain.setFeedforward(DriveSubsystem.Follower.DISTANCE, 0, 1 / s_v_max, 0);
    	drivetrain.setFeedback(DriveSubsystem.Follower.DISTANCE, lowFilter, 0, 0);
    	drivetrain.resetIntegrator(DriveSubsystem.Follower.DISTANCE);
    	
    	//setup angle PID
    	drivetrain.setMode(DriveSubsystem.Follower.ANGLE, HBRSubsystem.Mode.PID);
    	drivetrain.setPIDMode(DriveSubsystem.Follower.ANGLE, HBRSubsystem.PIDMode.VELOCITY);
    	drivetrain.setILimit(DriveSubsystem.Follower.ANGLE, 0);
    	drivetrain.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, 1 / a_v_max, 0);
    	drivetrain.setFeedback(DriveSubsystem.Follower.ANGLE, a_kp, 0, 0);
		drivetrain.resetIntegrator(DriveSubsystem.Follower.ANGLE);
		
		drivetrain.setEnabled(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	speedSetpoint = s_v_max * OI.getInstance().getDriver().getAxis(Logitech.LEFT_VERTICAL);
       	angleSetpoint = -a_v_max * OI.getInstance().getDriver().getAxis(Logitech.RIGHT_HORIZONTAL);
    	
       	//Limits robot speed when elevator is up
    	if (elevator.getElevatorPosition() > RobotMap.ELEVATOR_SPEED_LIMIT_POSITION)  {
    		drivetrain.setFeedforward(DriveSubsystem.Follower.DISTANCE, 0, 1 / s_v_max, 0);
        	drivetrain.setFeedback(DriveSubsystem.Follower.DISTANCE, highFilter, 0, 0);
        	
        	drivetrain.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, 1/ a_v_max, 0);
        	drivetrain.setFeedback(DriveSubsystem.Follower.ANGLE, a_kp, 0, 0);
        	
        	speedSetpoint = speedSetpoint / 7;
        	angleSetpoint = angleSetpoint / 3;
    	} else {
    		drivetrain.setFeedforward(DriveSubsystem.Follower.DISTANCE, lowFilter, 1 / s_v_max, 0);
        	drivetrain.setFeedback(DriveSubsystem.Follower.DISTANCE, 0, 0, 0);
        	
        	drivetrain.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, 1 / a_v_max, 0);
        	drivetrain.setFeedback(DriveSubsystem.Follower.ANGLE, a_kp, 0, 0);
        	
        	angleSetpoint = angleSetpoint * 0.55;
    	}
    	
    	GambeziDashboard.set_double("Drive/Left Encoder", drivetrain.getLeftPosition());
    	GambeziDashboard.set_double("Drive/Right Encoder", drivetrain.getRightPosition());
    	
    	GambeziDashboard.set_double("Drive/Encoder Average", drivetrain.getAveragePosition());
    	
    	GambeziDashboard.set_double("Drive/angleSetpoint", angleSetpoint);
    	drivetrain.setSetpoint(DriveSubsystem.Follower.DISTANCE, speedSetpoint);
    	drivetrain.setSetpoint(DriveSubsystem.Follower.ANGLE, angleSetpoint);
    	
    	/*
    	GambeziDashboard.set_double("Drive/Left_Speed", drivetrain.getLeftSpeed());
    	GambeziDashboard.set_double("Drive/Right_Speed", drivetrain.getRightSpeed());
    	GambeziDashboard.set_double("Drive/Left_Position", drivetrain.getLeftPosition());
    	GambeziDashboard.set_double("Drive/Right_Position", drivetrain.getRightPosition());
    	GambeziDashboard.set_double("Drive/Left_Acceleration", drivetrain.getLeftAcceleration());
    	GambeziDashboard.set_double("Drive/Right_Acceleration", drivetrain.getRightAcceleration());
    	GambeziDashboard.set_double("Drive/Drive_Angle", drivetrain.getGyro().getAngle());


		GambeziDashboard.set_double("navx/yaw", drivetrain.getGyro().getYaw());
		GambeziDashboard.set_double("navx/rawgyroz", drivetrain.getGyro().getRawGyroZ());
		Gamb9eziDashboard.set_double("navx/rate", drivetrain.getGyro().getRate());
		GambeziDashboard.set_double("navx/angle", drivetrain.getGyro().getAngle());
		*/
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
