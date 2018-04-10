package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.DriverStation;

public class ReleaseBuddyClimb extends Command {

	private ClimberSubsystem climber;
	private DriverStation ds;
	
	public ReleaseBuddyClimb(){
		climber = ClimberSubsystem.getInstance();
		ds = DriverStation.getInstance();
		requires(climber);
	}
	
	@Override
	protected void initialize(){
		if(ds.isOperatorControl() && ds.getMatchTime() < 32){
			climber.releaseBuddyClimb();
			System.out.println("it is TIME TO CLIMB");
		}
	}
	
	@Override
	protected void execute(){}
	
	@Override
	protected void end(){
		climber.resetBuddyClimbLatch();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
