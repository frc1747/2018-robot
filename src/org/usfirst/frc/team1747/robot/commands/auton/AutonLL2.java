package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristAtElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.drive.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.reset.AutonStopMotors;
import org.usfirst.frc.team1747.robot.commands.wrist.WristBottom;
import org.usfirst.frc.team1747.robot.commands.wrist.WristOverTop;
import org.usfirst.frc.team1747.robot.commands.wrist.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import lib.frc1747.commands.MakeParallel;
import lib.frc1747.commands.MakeSequential;

public class AutonLL2 extends CommandGroup {
	
	public AutonLL2() {
		// Drive and put cube in scale
		addSequential(new MakeParallel(
			new MakeSequential(
				new WristVertical(),
				new Delay(1650),
				new AutonOutake(0.7),
//				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
//				new WristOverTop(),
				new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP, 24, 3),
				new AutonOutake(-0.9, 200)
			),
			new DriveProfile("/home/lvuser/LL0.csv")
		));
		addSequential(new AutonStopMotors());
		
		// Bring elevator to bottom then grab another cube
//		addSequential(new WristBottom());
		addSequential(new MakeParallel(
//			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
			new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM, 9001, 0),
			new Intake(),
			new DriveProfile("/home/lvuser/LL1.csv")
		));
		addSequential(new AutonStopMotors());
		
		// Go back and place another cube in scale
		addSequential(new MakeParallel(
			new MakeSequential(
				new AutonOutake(0.7, 250),
				new WristVertical(),
				/*//new Delay(500),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
				new WristOverTop(),*/
				new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP, 24, 3),
				new AutonOutake(-0.9, 200)
			),
			new DriveProfile("/home/lvuser/LL2.csv")
		));
		addSequential(new AutonStopMotors());

		// Bring elevator to bottom then grab another cube
//		addSequential(new WristBottom());
		addSequential(new MakeParallel(
//				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
				new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM, 9001, 0),
				new Intake(),
				new DriveProfile("/home/lvuser/LL3.csv")
		));
		addSequential(new AutonStopMotors());
		
		// Go back and place a third cube in scale
				addSequential(new MakeParallel(
					new MakeSequential(
						new AutonOutake(0.7, 250),
						new WristVertical(), 
						new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP, 24, 3), 
						new AutonOutake(-0.9, 200)
						/*//new Delay(500),
						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
						new WristOverTop(),*/
						
					),
					new DriveProfile("/home/lvuser/LL4.csv")
				));
				addSequential(new AutonStopMotors());
	}
}
