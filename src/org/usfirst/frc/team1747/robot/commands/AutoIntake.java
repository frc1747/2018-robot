package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.subsystems.ClawSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.controller.Logitech;
import lib.frc1747.subsytems.HBRSubsystem;

public class AutoIntake extends Command {

	private IntakeSubsystem intake;
	private ElevatorSubsystem elevator;
	private ClawSubsystem claw;
	
	public AutoIntake(){
		intake = IntakeSubsystem.getInstance();
		elevator = ElevatorSubsystem.getInstance();
		claw = ClawSubsystem.getInstance();
		requires(intake);
		requires(elevator);
		requires(claw);
	}
	
	@Override
	protected void initialize(){
		elevator.setWristStage(0);
    	
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
		
		claw.setSolenoid(true);
	}
	
	@Override
	protected void execute(){
		System.out.println(OI.getInstance().getDriver().getButton(Logitech.LT).get());
		if(!intake.ifCubeCompletelyHeld() && Math.abs(elevator.getWristPosition() - elevator.getWristStages()[0]) < Math.PI/12){
			if(intake.ifCubePartiallyHeld()){
				claw.setSolenoid(false);
			}else{
				claw.setSolenoid(true);
			}
			intake.setPower(/*GambeziDashboard.get_double("Intake/InPower")*/ 0.8);
		}else{
			intake.setPower(0.0);
			claw.setSolenoid(false);
			intake.setLED(true);
		}
	}
	
	@Override
	protected void end(){
		if(intake.ifCubeCompletelyHeld()){
			elevator.setWristStage(elevator.getWristStages().length - 2);
		}else{
			intake.setPower(0.0);
			claw.setSolenoid(false);
			elevator.setWristStage(elevator.getWristStages().length - 1);
		}
		elevator.setSetpoint(ElevatorSubsystem.Follower.WRIST, elevator.getWristStages()[elevator.getWristStage()]);
		elevator.setSetpoint(ElevatorSubsystem.Follower.ELEVATOR, elevator.getElevatorStages()[elevator.getElevatorStage()]);
		intake.setLED(false);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return !OI.getInstance().getDriver().getButton(Logitech.LT).get();
	}

}
