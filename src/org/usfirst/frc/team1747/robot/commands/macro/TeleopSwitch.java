package org.usfirst.frc.team1747.robot.commands.macro;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TeleopSwitch extends CommandGroup {

	public TeleopSwitch(){
		addSequential(new SwitchStage1());
		addSequential(new SwitchStage2());
	}
}
