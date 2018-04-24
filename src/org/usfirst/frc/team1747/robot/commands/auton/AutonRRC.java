package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristAtElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.drive.DriveCurve;
import org.usfirst.frc.team1747.robot.commands.drive.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.reset.AutonStopMotors;
import org.usfirst.frc.team1747.robot.commands.wrist.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import lib.frc1747.commands.MakeParallel;

public class AutonRRC extends CommandGroup {
	
	public AutonRRC(){
		//TODO change to profile
		addSequential(new MakeParallel(new WristVertical(),
						new DriveProfile("/home/lvuser/RRC.csv")));
		addSequential(new AutonStopMotors());
		addSequential(new DriveCurve(0, 90));
		addSequential(new AutonStopMotors());
		addSequential(new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP, 24, 3));
		addSequential(new AutonOutake());
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
		addSequential(new DriveCurve(0, 90));
		addSequential(new AutonStopMotors());
	}
}