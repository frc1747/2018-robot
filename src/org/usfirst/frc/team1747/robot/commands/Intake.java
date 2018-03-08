package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command {
	
	private IntakeSubsystem intake;
	private ElevatorSubsystem elevator;
	
	public Intake() {
		intake = IntakeSubsystem.getInstance();
		elevator = ElevatorSubsystem.getInstance();
		requires (intake);
    	//GambeziDashboard.set_double("Intake/InPower", 0.8);
	}
	
	
	// Called just before this Command runs the first time
		@Override
		protected void initialize() {
	    	//System.out.println("Intake Cube");
		}

		// Called repeatedly when this Command is scheduled to run
		@Override
		protected void execute() {
	    	//System.out.println("Intake Periodic");
			if(!intake.ifCubeCompletelyHeld() && Math.abs(elevator.getWristPosition() - elevator.getWristStages()[0]) < Math.PI/12){
				//intake.setPower(/*GambeziDashboard.get_double("Intake/InPower")*/ 0.6);
				intake.setLeftPower(0.6);
				intake.setRightPower(0.55);
			}
		}
		
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return intake.ifCubeCompletelyHeld();
	}
	// Called once after isFinished returns true
		@Override
		protected void end() {
			intake.setPower(0.0);
		}

		// Called when another command which requires one or more of the same
		// subsystems is scheduled to run
		@Override
		protected void interrupted() {
			end();
		}

	
	}


