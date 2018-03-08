package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Outtake extends Command {

	public IntakeSubsystem intake;
	long startTime;
	double power;
	
    public Outtake(double power) {
    	intake = IntakeSubsystem.getInstance();
		requires (intake);
		if(power == 0){this.power = 0.8;}
		else{this.power = power;}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    	intake.setPower(-power);
    	//intake.setPower(-0.8);
    	//intake.setPower(GambeziDashboard.get_double("Intake/OutPower"));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return (System.currentTimeMillis() - startTime) >= 1000;
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		intake.setPower(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
