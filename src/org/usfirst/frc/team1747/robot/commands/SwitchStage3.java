package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchStage3 extends CommandGroup {

	public SwitchStage3(){
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
		addSequential(new WristTop());
	}
}
