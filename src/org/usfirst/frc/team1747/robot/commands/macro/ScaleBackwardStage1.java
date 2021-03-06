package org.usfirst.frc.team1747.robot.commands.macro;

import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.wrist.WristOverTop;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleBackwardStage1 extends CommandGroup {

	ScaleBackwardStage1(){
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP));
		addSequential(new WristOverTop());
	}
}
