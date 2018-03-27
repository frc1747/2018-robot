package org.usfirst.frc.team1747.robot.commands.macro;

import org.usfirst.frc.team1747.robot.commands.EjectCube;
import org.usfirst.frc.team1747.robot.commands.ElevatorReset;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchStage2 extends CommandGroup {

	public SwitchStage2(){
		addSequential(new EjectCube(0.5));
		addSequential(new ElevatorReset());
	}
}
