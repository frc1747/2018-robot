package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.RobotMap;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.motion_profile.Parameters;
import lib.frc1747.subsytems.HBRSubsystem;


/**
 *
 */
public class Drive2Cube extends Command {
	
	private DriveSubsystem drive;
	double angle, distance, d, x;
	
    public Drive2Cube(double distance, double angle) {
    	requires(drive = DriveSubsystem.getInstance());
    	drive = DriveSubsystem.getInstance();
    	this.angle = (angle / 360) * 2 * Math.PI;
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double camPxHeight = 640*2;
    	double camPxWidth = 480*2;
    	
    	
    	
    	double count = GambeziDashboard.get_double("pi_vision/count");
    	int boxIndex = -1;
    	for (int i = 0; i < count; i++) {
    		if (boxIndex > -1) {
    			double newY2 = GambeziDashboard.get_double("pi_vision/y2/" + i);
    			double oldY2 = GambeziDashboard.get_double("pi_vision/y2/" + boxIndex);
    			if (newY2 > oldY2) {
    				boxIndex = i;
    			}
			} else {
				boxIndex = i;
			}
    	}
		if (boxIndex != -1) {
			double x1 = GambeziDashboard.get_double("pi_vision/x1/" + boxIndex);
			double x2 = GambeziDashboard.get_double("pi_vision/x2/" + boxIndex);
			double y1 = GambeziDashboard.get_double("pi_vision/y1/" + boxIndex);
			double y2 = GambeziDashboard.get_double("pi_vision/y2/" + boxIndex);
			
			
			double centerX = (x1 + x2) / 2;
			double centerY = (y1 + y2) / 2;
			
			
			double centerXPx = centerX;
			double centerYPx = centerY;
			
			double vh, vd, vx, h, viewAngleY, viewAngleX, phi, theta;
			h = 46.5 - 9; // In Inches
			viewAngleY = 1.085595; // 62.2 Degrees in Radians
			viewAngleX = 0.8517207; // 48.8 Degrees in Radians
			theta = 0.296706; // 17 Degrees in Radians
			vh = -(camPxHeight/2 - centerYPx); // Displacement Y in Px
			vd = (camPxHeight/2)/Math.tan(viewAngleY/2);
			phi = Math.atan2(vh, vd);
			d = h / Math.tan(phi+theta) - 24; // Distance from the robot forward/back
			
			vx = -(542 - centerXPx);
			x = (vx / vd) * d; // Distance from robot horizontally
			System.out.println("d: " + d);
			System.out.println("x: " + x);
			
			
		}
		
		if (Double.isNaN(x)) {
			x = 0;
			d = 0;
		}
		
		angle = -Math.atan2(x, d)*1.5;
		System.out.println("angle: " + angle);
		distance = Math.sqrt((d*d + x*x)) / 12;
    	
    	double[][][] profiles = HBRSubsystem.generateSkidSteerPseudoProfile(distance, angle, Parameters.I_SAMPLE_LENGTH, 14, 20, 26, Parameters.W_WIDTH, RobotMap.DT, true, true);
    	double linearOffset = drive.getAveragePosition();
    	double angularOffset = (2 * Math.PI) * ((-drive.getGyro().getAngle()) / 360);
    	for(int i = 0;i < profiles[0].length;i++) {
    		profiles[0][i][0] += linearOffset;
    		profiles[1][i][0] += angularOffset;
    	}
    	
    	// Setup left side
    	drive.setMode(DriveSubsystem.Follower.DISTANCE, HBRSubsystem.Mode.FOLLOWER);
    	drive.setPIDMode(DriveSubsystem.Follower.DISTANCE, HBRSubsystem.PIDMode.POSITION);
    	drive.setILimit(DriveSubsystem.Follower.DISTANCE, 0);
    	drive.setFeedforward(DriveSubsystem.Follower.DISTANCE, 0, GambeziDashboard.get_double("Drive/Distance/kV"), GambeziDashboard.get_double("Drive/Distance/kA"));
    	drive.setFeedback(DriveSubsystem.Follower.DISTANCE, GambeziDashboard.get_double("Drive/Distance/kP"), GambeziDashboard.get_double("Drive/Distance/kI"), GambeziDashboard.get_double("Drive/Distance/kD"));
    	drive.resetIntegrator(DriveSubsystem.Follower.DISTANCE);
    	drive.setProfile(DriveSubsystem.Follower.DISTANCE, profiles[0]);
    	
    	// Setup right side
    	drive.setMode(DriveSubsystem.Follower.ANGLE, HBRSubsystem.Mode.FOLLOWER);
    	drive.setPIDMode(DriveSubsystem.Follower.ANGLE, HBRSubsystem.PIDMode.POSITION);
    	drive.setILimit(DriveSubsystem.Follower.ANGLE, 0);
    	drive.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, GambeziDashboard.get_double("Drive/Angle/kV"), GambeziDashboard.get_double("Drive/Angle/kA"));
    	drive.setFeedback(DriveSubsystem.Follower.ANGLE, GambeziDashboard.get_double("Drive/Angle/kP"), GambeziDashboard.get_double("Drive/Angle/kI"), GambeziDashboard.get_double("Drive/Angle/kD"));
    	drive.resetIntegrator(DriveSubsystem.Follower.ANGLE);
    	drive.setProfile(DriveSubsystem.Follower.ANGLE, profiles[1]);
    	    	
    	// Enable the pids
    	drive.resume(DriveSubsystem.Follower.DISTANCE);
    	drive.resume(DriveSubsystem.Follower.ANGLE);
    	drive.setEnabled(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//System.out.println("PROFILE FINISHED?");
        return !drive.isRunning(DriveSubsystem.Follower.DISTANCE) && !drive.isRunning(DriveSubsystem.Follower.ANGLE);
    }

    // Called when the command ends
    protected void end() {
    	drive.setEnabled(false);
    	drive.setLeftPower(0);
    	drive.setRightPower(0);
    }

    // Called when the command is interrupted
    protected void interrupted() {
    	end();
    }
}
