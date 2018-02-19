package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleForward extends CommandGroup {
	
	public ScaleForward(){
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP));
		addSequential(new WristBottom());
		addSequential(new Delay(250));
		addSequential(new EjectCube());
		addSequential(new WristVertical());
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
		addSequential(new WristTop());
	}
}
