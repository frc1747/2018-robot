package org.usfirst.frc.team1747.robot.commands.drive;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.RobotMap;
import org.usfirst.frc.team1747.robot.RobotType;
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
	private final double a_v_max = 8;
	
	private final double lowFilter = -0.011;			//TODO: Tune these values
	private final double highFilter = -0.0075;
	private double a_kp = 0;
	

	
	
	double speedSetpoint;
	double angleSetpoint;
//	boolean operator;

    public DriveWithJoysticks() {
    	requires(drivetrain = DriveSubsystem.getInstance());
    	setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	if(RobotType.getInstance().getJumper().get()){
    		a_kp = -0.02;
    	}else{
    		a_kp = 0.04;
    	}
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
    	if(RobotType.getInstance().getJumper().get()){
        	drivetrain.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, 1/a_v_max, 0); //should be 0.07
    	}else{
        	drivetrain.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, 0.06, 0); //should be 0.07
    	}
    	drivetrain.setFeedback(DriveSubsystem.Follower.ANGLE, a_kp, 0, 0);
		drivetrain.resetIntegrator(DriveSubsystem.Follower.ANGLE);
		
		drivetrain.setEnabled(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//speedSetpoint = s_v_max * OI.getInstance().getDriver().getAxis(Logitech.LEFT_VERTICAL);
       	//angleSetpoint = -a_v_max * OI.getInstance().getDriver().getAxis(Logitech.RIGHT_HORIZONTAL);
    	
    	speedSetpoint = s_v_max * Math.max(-1.0, Math.min(1.0, OI.getInstance().getDriver().getAxis(Logitech.LEFT_VERTICAL)));
    	angleSetpoint = -a_v_max * Math.max(-1.0, Math.min(1.0, OI.getInstance().getDriver().getAxis(Logitech.RIGHT_HORIZONTAL))); // + 0.5 * OI.getInstance().getOperator().getAxis(Logitech.RIGHT_HORIZONTAL)
       	
       	//Limits robot speed when elevator is up
    	/*if (elevator.getElevatorPosition() > RobotMap.ELEVATOR_SPEED_LIMIT_POSITION)  {
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
        	
        	angleSetpoint = angleSetpoint * (0.55 + 0.45/20 * Math.abs(drivetrain.getAverageSpeed()));
    	}*/
    	
//    	if(elevator.getElevatorPosition() > 24.5) {
    		//scale up based on height
   		double mult = 1 - elevator.getElevatorPosition() / 70;
   		drivetrain.setFeedforward(DriveSubsystem.Follower.DISTANCE, 0, 1 / s_v_max, 0);
       	drivetrain.setFeedback(DriveSubsystem.Follower.DISTANCE, lowFilter, 0, 0);
       	
       	//drivetrain.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, 1 / a_v_max, 0);
       	//drivetrain.setFeedback(DriveSubsystem.Follower.ANGLE, a_kp, 0, 0);
       	
       	speedSetpoint *= (mult * (6. / 7)) + (1. / 7);
        angleSetpoint *= ((mult * (1. / 2)) + (1. / 2));
//      angleSetpoint *= (0.55 + 0.45 / 20 * Math.abs(drivetrain.getAverageSpeed())) * ((mult * (1. / 2)) + (1. / 2));
    	/*} else {
    		drivetrain.setFeedforward(DriveSubsystem.Follower.DISTANCE, 0, 1 / s_v_max, 0);
        	drivetrain.setFeedback(DriveSubsystem.Follower.DISTANCE, lowFilter, 0, 0);
        	
        	drivetrain.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, 1 / a_v_max, 0);
        	drivetrain.setFeedback(DriveSubsystem.Follower.ANGLE, a_kp, 0, 0);
        	
        	angleSetpoint = angleSetpoint * (0.55 + 0.45/20 * Math.abs(drivetrain.getAverageSpeed()));
    	}*/
    	
    	
    	GambeziDashboard.set_double("Drive/Left Encoder", drivetrain.getLeftPosition());
    	GambeziDashboard.set_double("Drive/Right Encoder", drivetrain.getRightPosition());
    	
    	GambeziDashboard.set_double("Drive/Encoder Average", drivetrain.getAveragePosition());
    	
    	GambeziDashboard.set_double("Drive/angleSetpoint", angleSetpoint);
//    	if(operator){
    		
//    	}
    	drivetrain.setSetpoint(DriveSubsystem.Follower.DISTANCE, speedSetpoint);
    	drivetrain.setSetpoint(DriveSubsystem.Follower.ANGLE, angleSetpoint);
    	
    	
    	GambeziDashboard.set_double("Drive/Left_Speed", drivetrain.getLeftSpeed());
    	GambeziDashboard.set_double("Drive/Right_Speed", drivetrain.getRightSpeed());
    	GambeziDashboard.set_double("Drive/Left_Position", drivetrain.getLeftPosition());
    	GambeziDashboard.set_double("Drive/Right_Position", drivetrain.getRightPosition());
    	GambeziDashboard.set_double("Drive/Left_Acceleration", drivetrain.getLeftAcceleration());
    	GambeziDashboard.set_double("Drive/Right_Acceleration", drivetrain.getRightAcceleration());
    	GambeziDashboard.set_double("Drive/Drive_Angle", drivetrain.getGyro().getAngle());
		
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
