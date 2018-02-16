package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OpenAndEngageIntake extends CommandGroup {

	//Begins and ends with intake closed; First listed intake command on paper list along with WristBottom and WaitAndWristTop
	public OpenAndEngageIntake(){
		addParallel(new OpenClaw());
		addSequential(new Intake());
	}
}
