package org.usfirst.frc.team1747.robot.commands.macro;

import org.usfirst.frc.team1747.robot.commands.EjectCube;
import org.usfirst.frc.team1747.robot.commands.ElevatorReset;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristBottom;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TeleopSwitchHigh extends CommandGroup {

	public TeleopSwitchHigh(){
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.HIGH_SWITCH));
		addSequential(new WristBottom());
		addSequential(new EjectCube(0.5));
		addSequential(new ElevatorReset());
	}
}
