package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;

public class EjectCube extends Command {

	private IntakeSubsystem intake;
	double power;
	long time;
	boolean cubeGone;
	
    public EjectCube() {
    	intake = IntakeSubsystem.getInstance();
		requires (intake);
    	power = -0.8;

    }
    
    public EjectCube(double power){
    	this.power = - power;
    	intake = IntakeSubsystem.getInstance();
		requires (intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		cubeGone = false;
    		intake.setPower(power);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(cubeGone == false && !intake.ifCubePartiallyHeld()){
    		cubeGone = true;
    		time = System.currentTimeMillis();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return cubeGone && System.currentTimeMillis() - time > 0;
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
