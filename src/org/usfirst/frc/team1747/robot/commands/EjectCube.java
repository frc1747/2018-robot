package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;

public class EjectCube extends Command {

	private IntakeSubsystem intake;
	
    public EjectCube() {
    	intake = IntakeSubsystem.getInstance();
		requires (intake);
    	GambeziDashboard.set_double("Intake/OutPower", -0.8);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake.setPower(GambeziDashboard.get_double("Intake/OutPower"));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//TODO: !!!!Make this not stop immediately when cube isn't all the way in. Possibly a different method in IntakeSubsystem is needed
    	return !intake.ifCube();
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
