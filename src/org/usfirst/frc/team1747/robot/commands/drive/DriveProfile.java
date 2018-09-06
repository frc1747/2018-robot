package org.usfirst.frc.team1747.robot.commands.drive;

import org.usfirst.frc.team1747.robot.RobotType;
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
    	this.filename = filename;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	double[][][] profiles = HBRSubsystem.readProfilesFromFile(filename);
    	double linearOffset = drive.getAveragePosition();
    	double angularOffset = (2 * Math.PI) * ((-drive.getGyro().getAngle()) / 360);
    	for(int i = 0;i < profiles[0].length;i++) {
    		profiles[0][i][0] += linearOffset;
    		profiles[1][i][0] += angularOffset;
    	}
    	
    	//Setup Distance
    	drive.setMode(DriveSubsystem.Follower.DISTANCE, HBRSubsystem.Mode.FOLLOWER);
    	drive.setPIDMode(DriveSubsystem.Follower.DISTANCE, HBRSubsystem.PIDMode.POSITION);
    	drive.setILimit(DriveSubsystem.Follower.DISTANCE, 0);
//    	drive.setFeedforward(DriveSubsystem.Follower.DISTANCE, 0, GambeziDashboard.get_double("Drive/Distance/kV"), GambeziDashboard.get_double("Drive/Distance/kA"));
    	drive.setFeedforward(DriveSubsystem.Follower.DISTANCE, 0, .075, .02);
//    	drive.setFeedback(DriveSubsystem.Follower.DISTANCE, GambeziDashboard.get_double("Drive/Distance/kP"), GambeziDashboard.get_double("Drive/Distance/kI"), GambeziDashboard.get_double("Drive/Distance/kD"));
    	drive.setFeedback(DriveSubsystem.Follower.DISTANCE, 1, 0, 0);
    	drive.resetIntegrator(DriveSubsystem.Follower.DISTANCE);
    	drive.setProfile(DriveSubsystem.Follower.DISTANCE, profiles[0]);
    	
    	//Setup Angle
    	drive.setMode(DriveSubsystem.Follower.ANGLE, HBRSubsystem.Mode.FOLLOWER);
    	drive.setPIDMode(DriveSubsystem.Follower.ANGLE, HBRSubsystem.PIDMode.POSITION);
    	drive.setILimit(DriveSubsystem.Follower.ANGLE, 0);
//    	drive.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, GambeziDashboard.get_double("Drive/Angle/kV"), GambeziDashboard.get_double("Drive/Angle/kA"));
    	if(RobotType.getInstance().getJumper().get()){
        	drive.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, .18, .02);
    	}else{
        	drive.setFeedforward(DriveSubsystem.Follower.ANGLE, 0, .09, .02);
    	}
//    	drive.setFeedback(DriveSubsystem.Follower.ANGLE, GambeziDashboard.get_double("Drive/Angle/kP"), GambeziDashboard.get_double("Drive/Angle/kI"), GambeziDashboard.get_double("Drive/Angle/kD"));
    	if(RobotType.getInstance().getJumper().get()){
        	drive.setFeedback(DriveSubsystem.Follower.ANGLE, 1.7, 0, 0.07);
    	}else{
        	drive.setFeedback(DriveSubsystem.Follower.ANGLE, 1.7, 0, 0.10);
    	}
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
    	this.end();
    }
}
