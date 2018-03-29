package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.CloseClaw;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.OpenClaw;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristBottom;
import org.usfirst.frc.team1747.robot.commands.WristOverTop;
import org.usfirst.frc.team1747.robot.commands.WristTop;
import org.usfirst.frc.team1747.robot.commands.WristVertical;
import org.usfirst.frc.team1747.robot.commands.drive.DriveCurve;
import org.usfirst.frc.team1747.robot.commands.drive.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.reset.AutonStopMotors;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import lib.frc1747.commands.MakeParallel;
import lib.frc1747.commands.MakeSequential;

/**
 *
 */
public class AutonLRL extends CommandGroup {

    public AutonLRL() {
    	//go to left scale and place cube
    	addSequential(new MakeParallel(
			new MakeSequential(
				new WristVertical(),
				new Delay(2500),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
			),
			new DriveProfile("/home/lvuser/LLL0.csv")
		));
		addSequential(new AutonStopMotors());
		addSequential(new WristOverTop());
		addSequential(new AutonOutake());
		
		//drive to right switch and get new cube
		addSequential(new MakeParallel(new MakeSequential(new WristBottom(), new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)), new Intake(), new OpenClaw(), new DriveProfile("/home/lvuser/LRL1.csv")));
		addSequential(new AutonStopMotors());
		addSequential(new CloseClaw());
		addSequential(new AutonStopMotors());
		
		//raise elevator and eject cube
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
		addSequential(new WristBottom());
		addSequential(new DriveCurve(0.8, -15));
		addSequential(new AutonOutake());
		
		//back away from scwitch
    	addSequential(new MakeParallel(new WristTop(), new DriveCurve(-1, 0)));
    	addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));

    }
}
