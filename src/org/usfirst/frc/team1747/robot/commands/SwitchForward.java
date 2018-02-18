package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchForward extends CommandGroup {
	public SwitchForward(){
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP));
		addSequential(new WristBottom());
		addSequential(new EjectCube());
		addSequential(new WristVertical());
		addSequential(new ElevatorDown());
	}
}
