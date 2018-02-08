package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import lib.frc1747.subsytems.HBRSubsystem;
import com.tigerhuang.gambezi.dashboard.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveProfile extends Command {
	private DriveSubsystem drive;
	String filename;
	
    public DriveProfile(String filename) {
    	// Command initialization
    	requires(drive = DriveSubsystem.getInstance());
    	setInterruptible(true);
    	GambeziDashboard.set_double("Drive/Distance/kA", 0);
    	GambeziDashboard.set_double("Drive/Distance/kV", 0);
    	GambeziDashboard.set_double("Drive/Distance/kP", 0);
    	GambeziDashboard.set_double("Drive/Distance/kI", 0);
    	GambeziDashboard.set_double("Drive/Distance/kD", 0);
    	GambeziDashboard.set_double("Drive/Angle/kA", 0);
    	GambeziDashboard.set_double("Drive/Angle/kV", 0);
    	GambeziDashboard.set_double("Drive/Angle/kP", 0);
    	GambeziDashboard.set_double("Drive/Angle/kI", 0);
    	GambeziDashboard.set_double("Drive/Angle/kD", 0);
    	this.filename = filename;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	double[][][] profiles = HBRSubsystem.readProfilesFromFile(filename);

    	// Setup left side
//    	drive.resetEncoders();
//    	drive.getGyro().zeroYaw();

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
    	drive.setRightPower(0);
    	drive.setLeftPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
