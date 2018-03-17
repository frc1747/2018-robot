package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.AutonStopMotors;
import org.usfirst.frc.team1747.robot.commands.CloseClaw;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.MakeParallel;
import org.usfirst.frc.team1747.robot.commands.MakeSequential;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristBottom;
import org.usfirst.frc.team1747.robot.commands.WristOverTop;
import org.usfirst.frc.team1747.robot.commands.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonLL extends CommandGroup {
	public AutonLL(){
		// Drive and put cube in scale
		addSequential(new MakeParallel(
			new MakeSequential(
				new WristVertical(),
				new Delay(2500),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
			),
			new DriveProfile("/home/lvuser/LL0.csv")
		));
		addSequential(new AutonStopMotors());
		addSequential(new WristOverTop());
		addSequential(new AutonOutake());
		
		// Bring elevator to bottom then grab another cube
		addSequential(new WristBottom());
		addSequential(new MakeParallel(
			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
			new Intake(),
			new DriveProfile("/home/lvuser/LL1.csv")
		));
		addSequential(new AutonStopMotors());
		
		// Go back and place another cube in scale
		addSequential(new MakeParallel(
			new MakeSequential(
				new WristVertical(),
				new Delay(1000),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
				new WristOverTop(),
				new AutonOutake(),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
			),
			new DriveProfile("/home/lvuser/LL2.csv")
		));
		addSequential(new AutonStopMotors());
	}
}
