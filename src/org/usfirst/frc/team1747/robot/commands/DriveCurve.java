package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.motion_profile.Parameters;
import lib.frc1747.subsytems.HBRSubsystem;

/**
 *
 */
public class DriveCurve extends Command {
	
	private DriveSubsystem drive;
	double angle;
	double distance;
	
    public DriveCurve(double distance, double angle) {
    	requires(drive = DriveSubsystem.getInstance());
    	drive = DriveSubsystem.getInstance();
    	this.angle = (angle / 360) * 2 * Math.PI;
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double[][][] profiles = HBRSubsystem.generateSkidSteerPseudoProfile(distance, angle, Parameters.I_SAMPLE_LENGTH, 10.4, 40, 9000.1, Parameters.W_WIDTH, Parameters.DT, true, true);
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
