package org.usfirst.frc.team1747.robot.commands;
import org.usfirst.frc.team1747.robot.commands.wrist.WristTop;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ElevatorReset extends CommandGroup {
	
	public ElevatorReset() {
		addSequential(new WristTop());
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
	}
}
