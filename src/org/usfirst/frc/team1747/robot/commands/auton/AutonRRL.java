package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.drive.DriveCurve;
import org.usfirst.frc.team1747.robot.commands.drive.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.reset.AutonStopMotors;
import org.usfirst.frc.team1747.robot.commands.wrist.WristBottom;
import org.usfirst.frc.team1747.robot.commands.wrist.WristOverTop;
import org.usfirst.frc.team1747.robot.commands.wrist.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import lib.frc1747.commands.MakeParallel;
import lib.frc1747.commands.MakeSequential;

public class AutonRRL extends CommandGroup {

    public AutonRRL() {
    	// Drive to switch side position
    	addSequential(new MakeParallel(
			new MakeSequential(
				new WristVertical(),
				new Delay(750),
				new WristBottom(),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH)
			),
			new DriveProfile("/home/lvuser/RRL0.csv")
		));
		addSequential(new AutonStopMotors());

		// Do a 180 and drop the cube
		addSequential(new MakeParallel(
			new MakeSequential(
				new Delay(800),
				new AutonOutake()
			),
			new DriveCurve(0, 180)
		));
		addSequential(new AutonStopMotors());

		// Drive to other side of the switch and pickup a cube
		addSequential(new MakeParallel(
			new MakeSequential(
				new WristVertical(),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
				new Delay(1000),
				new WristBottom(),
				new Intake()
			),            	    					
			new DriveProfile("/home/lvuser/RRL1.csv")
		));
		addSequential(new AutonStopMotors());

		// Drive back to scale and place cube
		addSequential(new MakeParallel(
			new MakeSequential(
				new AutonOutake(0.7),
				new WristVertical(),
				new Delay(750),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
				new WristOverTop(),
				new AutonOutake(-0.65)
			),
			new DriveProfile("/home/lvuser/RRL2.csv")
		));
		addSequential(new AutonStopMotors());
		
		// Put elevator back down
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
    }
}
