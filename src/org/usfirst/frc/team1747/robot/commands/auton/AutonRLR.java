package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.CloseClaw;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.OpenClaw;
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

public class AutonRLR extends CommandGroup {

    public AutonRLR() {
    	// Go to left scale and place cube
    	addSequential(new MakeParallel(
			new MakeSequential(
				new WristVertical(),
				new Delay(2250),
				new AutonOutake(0.7),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
			),
			new DriveProfile("/home/lvuser/RLR0.csv")
		));
		addSequential(new AutonStopMotors());
		addSequential(new WristOverTop());
		addSequential(new AutonOutake());
		
		// Drive to right switch and get new cube
		addSequential(new MakeParallel(
			new MakeSequential(
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
				new WristBottom()),
			new Intake(),
			new DriveProfile("/home/lvuser/RLR1.csv")
		));
		addSequential(new AutonStopMotors());
		//backup to get cube
		addSequential(new MakeParallel(new Intake(), new DriveCurve(-0.5, 0.0)));
		// Raise elevator and eject cube
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
		addSequential(new WristBottom());
		addSequential(new DriveCurve(0.8, -50));
		addSequential(new AutonOutake(-0.5));
		
		// Back away from switch
    	addSequential(new MakeParallel(
    		new WristTop(),
    		new DriveCurve(-1, 0)
    	));
    	addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
    }
}
