package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WaitAndWristTop extends CommandGroup {
	
	//Does not go to all the way back, but rather to vertical
	public WaitAndWristTop(){
		addSequential(new Delay(1000));
		addSequential(new WristVertical());
	}
}
