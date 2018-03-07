package org.usfirst.frc.team1747.robot.commands;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ElevatorReset extends CommandGroup {
	
	@Override
	protected void initialize() {
		addSequential(new WristTop());
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
	}
}
