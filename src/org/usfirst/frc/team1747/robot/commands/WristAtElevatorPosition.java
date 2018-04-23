package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.RobotMap;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem.ElevatorPositions;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.motion_profile.Parameters;
import lib.frc1747.subsytems.HBRSubsystem;

public class WristAtElevatorPosition extends Command {

	private ElevatorSubsystem elevator;
	
	private ElevatorPositions d_elevatorposition;
	private double elevatorTriggerTolerance;
	private int wristIndex;
	private double distance;
	private IntakeSubsystem intake;
	private boolean wristSet;
	
	public WristAtElevatorPosition(ElevatorPositions elevatorsetpoint,
									double elevatorTriggerTolerance,
									int wristindex){
		d_elevatorposition = elevatorsetpoint;
		this.elevatorTriggerTolerance = elevatorTriggerTolerance;
		this.wristIndex = wristindex;
		intake = IntakeSubsystem.getInstance();
		requires(elevator = ElevatorSubsystem.getInstance());
	}
	
	@Override
	protected void initialize(){
		double currentPosition = elevator.getElevatorPosition();
		double position = elevator.getElevatorStages()[d_elevatorposition.ordinal()];
    	distance = position - currentPosition;
    	if(Math.abs(distance) > 1){
	    	double[][][] profiles = HBRSubsystem.generateSkidSteerPseudoProfile(distance, 0, Parameters.I_SAMPLE_LENGTH * 12, 120, 200, 9000.1, Parameters.W_WIDTH, RobotMap.DT, true, true);
	    	for(int i = 0; i < profiles[0].length; i++){
	    		profiles[0][i][0] += currentPosition;
	    	}
	    	
	    	//setup elevator PID
	    	elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.FOLLOWER);
	    	elevator.setPIDMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.PIDMode.POSITION);
	    	elevator.setILimit(ElevatorSubsystem.Follower.ELEVATOR, 0);
//	    	elevator.setFeedforward(ElevatorSubsystem.Follower.ELEVATOR, 0, GambeziDashboard.get_double("Elevator/kV"), GambeziDashboard.get_double("Elevator/kA"));
//	    	elevator.setFeedback(ElevatorSubsystem.Follower.ELEVATOR, GambeziDashboard.get_double("Elevator/kP"), GambeziDashboard.get_double("Elevator/kI"), GambeziDashboard.get_double("Elevator/kD"));
	    	elevator.setFeedforward(ElevatorSubsystem.Follower.ELEVATOR, 0, 0.0068, 0.0009);
	    	elevator.setFeedback(ElevatorSubsystem.Follower.ELEVATOR, 0.18, 0., 0.);
	    	elevator.resetIntegrator(ElevatorSubsystem.Follower.ELEVATOR);
	    	elevator.setProfile(ElevatorSubsystem.Follower.ELEVATOR, profiles[0]);

			elevator.resume(ElevatorSubsystem.Follower.ELEVATOR);

			elevator.setElevatorStage(d_elevatorposition.ordinal());

    	}
    	else {
        	elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.PID);
        	elevator.setPIDMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.PIDMode.POSITION);
        	elevator.setILimit(ElevatorSubsystem.Follower.ELEVATOR, 0);
        	elevator.resetIntegrator(ElevatorSubsystem.Follower.ELEVATOR);
        	
    		elevator.setSetpoint(ElevatorSubsystem.Follower.ELEVATOR, (elevator.getElevatorStages()[elevator.getElevatorStage()]));
    	}
    	//setup wrist PID
    	elevator.setMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.Mode.PID);
    	elevator.setPIDMode(ElevatorSubsystem.Follower.WRIST, HBRSubsystem.PIDMode.POSITION);
    	elevator.setILimit(ElevatorSubsystem.Follower.WRIST, 0);
//    	elevator.setFeedforward(ElevatorSubsystem.Follower.WRIST, 0, GambeziDashboard.get_double("Wrist/kV"), GambeziDashboard.get_double("Wrist/kA"));
//    	elevator.setFeedback(ElevatorSubsystem.Follower.WRIST, GambeziDashboard.get_double("Wrist/kP"), GambeziDashboard.get_double("Wrist/kI"), GambeziDashboard.get_double("Wrist/kD"));
    	elevator.setFeedforward(ElevatorSubsystem.Follower.WRIST, 0, 0, 0);
    	elevator.setFeedback(ElevatorSubsystem.Follower.WRIST, 0.55, 0, 0);
    	
    	elevator.resetIntegrator(ElevatorSubsystem.Follower.WRIST);
		
		if(!((elevator.getWristStage() == elevator.getWristStages().length - 1) && intake.ifCubeCompletelyHeld() && d_elevatorposition.ordinal() > 0)){
			elevator.setSetpoint(ElevatorSubsystem.Follower.WRIST, elevator.getWristStages()[elevator.getWristStage()]);
		}

		elevator.setEnabled(true);
		wristSet = false;
	}
	
	@Override
	protected void execute(){
		if(Math.abs(elevator.getElevatorPosition()  - elevator.getElevatorStages()[d_elevatorposition.ordinal()]) < elevatorTriggerTolerance && !wristSet){
			elevator.setWristStage(wristIndex);
			elevator.setSetpoint(ElevatorSubsystem.Follower.WRIST, elevator.getWristStages()[elevator.getWristStage()]);
			wristSet = true;
		}
	}
	
	@Override
	protected void end(){
		if(!(d_elevatorposition == ElevatorSubsystem.ElevatorPositions.BOTTOM)){
    		elevator.setMode(ElevatorSubsystem.Follower.ELEVATOR, HBRSubsystem.Mode.PID);
    		elevator.setSetpoint(ElevatorSubsystem.Follower.ELEVATOR, elevator.getElevatorStages()[d_elevatorposition.ordinal()]);
    	}else{
    		elevator.setFeedforward(ElevatorSubsystem.Follower.ELEVATOR, 0, 0, 0);
    		elevator.setFeedback(ElevatorSubsystem.Follower.ELEVATOR, 0, 0, 0);
    	}
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Math.abs(elevator.getWristPosition() - elevator.getWristStages()[elevator.getWristStage()]) < Math.PI/18 && !elevator.isRunning(ElevatorSubsystem.Follower.ELEVATOR);
	}

}
