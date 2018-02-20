package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleForwardStage2 extends CommandGroup {
	
	public ScaleForwardStage2(){
		addSequential(new EjectCube());
		//addSequential(new WristVertical());
		addSequential(new WristTop());
	}
}
