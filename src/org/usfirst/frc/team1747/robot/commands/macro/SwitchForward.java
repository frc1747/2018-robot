package org.usfirst.frc.team1747.robot.commands.macro;

import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.wrist.EjectCube;
import org.usfirst.frc.team1747.robot.commands.wrist.WristBottom;
import org.usfirst.frc.team1747.robot.commands.wrist.WristTop;
import org.usfirst.frc.team1747.robot.commands.wrist.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchForward extends CommandGroup {
	public SwitchForward(){
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
		addSequential(new WristBottom());
		addSequential(new EjectCube());
		addSequential(new WristVertical());
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
		addSequential(new WristTop());
	}
}
