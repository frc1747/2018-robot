package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleBackward extends CommandGroup {

	public ScaleBackward(){
		addSequential(new ElevatorTop());
		addSequential(new WristTop());
		addSequential(new EjectCube());
		addSequential(new WristVertical());
		addSequential(new ElevatorBottom());
	}
}
