package org.usfirst.frc.team1747.robot.commands.macro;

import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristDown;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleForwardStage1 extends CommandGroup {
	
	ScaleForwardStage1(){
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.LOW_SCALE));
		addSequential(new WristDown());
	}
}
