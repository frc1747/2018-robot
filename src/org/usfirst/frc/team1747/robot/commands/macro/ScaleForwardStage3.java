package org.usfirst.frc.team1747.robot.commands.macro;

import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.wrist.WristTop;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleForwardStage3 extends CommandGroup {

	public ScaleForwardStage3(){
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
		addSequential(new WristTop());
	}
}
