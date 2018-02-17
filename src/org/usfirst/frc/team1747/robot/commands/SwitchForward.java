package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchForward extends CommandGroup {
	public SwitchForward(){
		addSequential(new ElevatorTop());
		addSequential(new WristBottom());
		addSequential(new EjectCube());
		addSequential(new WristVertical());
		addSequential(new ElevatorDown());
	}
}
