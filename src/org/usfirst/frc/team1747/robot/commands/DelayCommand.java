package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DelayCommand extends CommandGroup {

	public DelayCommand(int delay, Command command){
		addSequential(new Delay(delay));
		addSequential(command);
	}
}
