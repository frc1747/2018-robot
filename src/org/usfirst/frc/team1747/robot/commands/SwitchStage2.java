package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchStage2 extends CommandGroup {

	public SwitchStage2(){
		addSequential(new EjectCube());
		addSequential(new WristTop());
	}
}
