package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchStage1 extends CommandGroup {

	public SwitchStage1(){
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
		addSequential(new WristBottom());
	}
}
