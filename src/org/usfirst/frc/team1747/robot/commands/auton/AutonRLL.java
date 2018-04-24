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
import org.usfirst.frc.team1747.robot.commands.wrist.WristTop;
import org.usfirst.frc.team1747.robot.commands.wrist.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import lib.frc1747.commands.MakeParallel;
import lib.frc1747.commands.MakeSequential;

public class AutonRLL extends CommandGroup {

    public AutonRLL() {
    	// Drive to right scale and place cube
    	addSequential(new MakeParallel(
			new MakeSequential(
				new WristVertical(),
				new Delay(4750),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
				new WristOverTop(),
				new AutonOutake()
			),
			new DriveProfile("/home/lvuser/RLL0.csv")
		));
		addSequential(new AutonStopMotors());
		
		// Bring elevator to bottom then grab another cube
		addSequential(new WristBottom());
		addSequential(new MakeParallel(
		   	new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
			new Intake(),
			new DriveProfile("/home/lvuser/RLL1.csv")
		));
		addSequential(new AutonStopMotors());
		
		// Place cube in switch
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
		addSequential(new WristBottom());
		addSequential(new DriveCurve(0.8, -15));
		addSequential(new AutonStopMotors());
		addSequential(new AutonOutake());
		
		// Back away from switch
		addSequential(new WristTop());
		addSequential(new MakeParallel(
			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
	    	new DriveCurve(-1,0)
		));
		addSequential(new AutonStopMotors());
    }
}
