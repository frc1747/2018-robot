package org.usfirst.frc.team1747.robot.commands.wrist;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.subsytems.HBRSubsystem;

public class WristTop extends Command {

	private ElevatorSubsystem elevator;
	private IntakeSubsystem intake;

	double elevatorSetpoint;
	double wristSetpoint;

    public WristTop() {
    	requires(elevator = ElevatorSubsystem.getInstance());
    	setInterruptible(true); 	
    	intake = IntakeSubsystem.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!(intake.ifCubePartiallyHeld())){
        	elevator.setWristStage(elevator.getWristStages().length - 1);
    	}else{
    		elevator.setWristStage(elevator.getWristStages().length - 2);
    	}
    	
    	//setup elevator PID
    	elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.PID);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.PIDMode.POSITION);
    	elevator.setILimit(ElevatorSubsystem.Follower.ELEVATOR, 0);
    	elevator.resetIntegrator(ElevatorSubsystem.Follower.ELEVATOR);
    	
    	//setup wrist PID
    	elevator.setMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.Mode.PID);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.PIDMode.POSITION);
    	elevator.setILimit(ElevatorSubsystem.Follower.WRIST, 0);
    	elevator.setFeedforward(ElevatorSubsystem.Follower.WRIST, 0, GambeziDashboard.get_double("Wrist/kV"), GambeziDashboard.get_double("Wrist/kA"));
    	elevator.setFeedback(ElevatorSubsystem.Follower.WRIST, GambeziDashboard.get_double("Wrist/kP"), GambeziDashboard.get_double("Wrist/kI"), GambeziDashboard.get_double("Wrist/kD"));
		elevator.resetIntegrator(ElevatorSubsystem.Follower.WRIST);
		
		elevator.setEnabled(true);
		
	
		elevator.setSetpoint(ElevatorSubsystem.Follower.WRIST, elevator.getWristStages()[elevator.getWristStage()]);
		elevator.setSetpoint(ElevatorSubsystem.Follower.ELEVATOR, (elevator.getElevatorStages()[elevator.getElevatorStage()]));

		GambeziDashboard.set_double("Elevator/Index", elevator.getElevatorStage());
		GambeziDashboard.set_double("Wrist/Index", elevator.getWristStage());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(!(intake.ifCubeCompletelyHeld())){
    		return Math.abs(elevator.getWristPosition() - elevator.getWristStages()[elevator.getWristStages().length - 1]) < Math.PI/18;
    	}else{
    		return Math.abs(elevator.getWristPosition() - elevator.getWristStages()[elevator.getWristStages().length - 2]) < Math.PI/18;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
