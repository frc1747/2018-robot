package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchForward extends CommandGroup {
	public SwitchForward(){
		addSequential(new ElevatorUp());
		addSequential(new WristBottom());
		addSequential(new EjectCube());
	}
}
