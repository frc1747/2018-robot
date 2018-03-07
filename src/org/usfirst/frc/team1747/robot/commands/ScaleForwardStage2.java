package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleForwardStage2 extends CommandGroup {
	
	public ScaleForwardStage2(){
		addSequential(new EjectCube());
		addSequential(new ElevatorReset());
	}
}
