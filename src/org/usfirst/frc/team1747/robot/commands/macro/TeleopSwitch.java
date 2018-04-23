package org.usfirst.frc.team1747.robot.commands.macro;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.ElevatorReset;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristAtElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.wrist.EjectCube;
import org.usfirst.frc.team1747.robot.commands.wrist.WristBottom;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TeleopSwitch extends CommandGroup {

	public TeleopSwitch(){
		/*addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
		addSequential(new WristBottom());
		addSequential(new EjectCube(0.5));
		addSequential(new ElevatorReset());*/
		addSequential(new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH,
													15, 0));
		addSequential(new AutonOutake(-0.5, 400));
		addSequential(new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM, 9001,3));
	}
}
