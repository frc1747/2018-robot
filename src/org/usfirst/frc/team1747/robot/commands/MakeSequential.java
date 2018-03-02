package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MakeSequential extends CommandGroup {

    public MakeSequential(Command ... commands) {
    	for(int i = 0;i < commands.length;i++) {
    		addSequential(commands[i]);
    	}
    }
}
