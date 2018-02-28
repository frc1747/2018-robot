package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ResetIndex extends CommandGroup {

	public ResetIndex(Command command){
		addSequential(new ZeroIndex());
		addSequential(command);
	}
}
