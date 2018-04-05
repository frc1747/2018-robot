package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonOutake extends Command {

	public IntakeSubsystem intake;
	long startTime;
	double power;
	long time;
	
	public AutonOutake() {
		this(-0.8);
	}
    public AutonOutake(double power) {
    	this(power, 250);
    }
    
    public AutonOutake(double power, long timeMillis){
    	this.power = power;
    	this.time = timeMillis;
    	intake = IntakeSubsystem.getInstance();
    	requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    	intake.setPower(power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (System.currentTimeMillis() - startTime) >= time;
    	//return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.setLeftPower(0.0);
		intake.setRightPower(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
