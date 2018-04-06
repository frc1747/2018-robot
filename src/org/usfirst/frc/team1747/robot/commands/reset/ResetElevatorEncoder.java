package org.usfirst.frc.team1747.robot.commands.reset;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ResetElevatorEncoder extends Command {
	private ElevatorSubsystem elevator;
	
	public ResetElevatorEncoder() {
		requires(elevator = ElevatorSubsystem.getInstance());
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		elevator.resetEncoder();
//		elevator.setSetpoint(ElevatorSubsy, setpoint);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
