package org.usfirst.frc.team1747.robot.commands.macro;

import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.wrist.EjectCube;
import org.usfirst.frc.team1747.robot.commands.wrist.WristBottom;
import org.usfirst.frc.team1747.robot.commands.wrist.WristTop;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleForward extends CommandGroup {
	
	public ScaleForward(){
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP));
		addSequential(new WristBottom());
		addSequential(new EjectCube());
		//addSequential(new WristVertical());
		addSequential(new WristTop());
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
		addSequential(new WristTop());
	}
}
