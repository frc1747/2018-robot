package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoIntake extends CommandGroup {

	public AutoIntake(){
		addSequential(new WristBottom());
		addParallel(new OpenClaw());
		addSequential(new Intake());
		addSequential(new Delay(500));
		addSequential(new WristVertical());
	}
}
