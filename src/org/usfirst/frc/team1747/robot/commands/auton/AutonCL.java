package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOpenClaw;
import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.CloseClaw;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.OpenClaw;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristAtElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.drive.DriveCurve;
import org.usfirst.frc.team1747.robot.commands.drive.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.macro.TeleopSwitch;
import org.usfirst.frc.team1747.robot.commands.reset.AutonStopMotors;
import org.usfirst.frc.team1747.robot.commands.wrist.WristBottom;
import org.usfirst.frc.team1747.robot.commands.wrist.WristOverTop;
import org.usfirst.frc.team1747.robot.commands.wrist.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import lib.frc1747.commands.MakeParallel;
import lib.frc1747.commands.MakeSequential;

public class AutonCL extends CommandGroup {
	
	public AutonCL() {
		// Drive and put cube in scale
		addSequential(new MakeParallel(new MakeSequential(
				new WristBottom(),
				new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH, 9001, 0),
				new Delay(1000),
				new AutonOutake(-0.8, 750)),
			new DriveProfile("/home/lvuser/CL0.csv")
		));
		addSequential(new AutonStopMotors());
		
		// Bring elevator to bottom then grab another cube
		
		addSequential(new MakeParallel(
			new MakeSequential(
				new Delay(500),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
				),
			new DriveProfile("/home/lvuser/CL1.csv")
		));
		addSequential(new AutonStopMotors());
		
		addSequential(new MakeParallel(
				new AutonOpenClaw(),
				new Intake(),	
				new DriveCurve(2.5, 0)));
		addSequential(new AutonStopMotors());
		
		addSequential(new MakeParallel(
				new MakeSequential(
					new Delay(500),
					new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH)),
				new DriveCurve(-2.5, 0)));
		addSequential(new AutonStopMotors());
		
		addSequential(new MakeParallel(new MakeSequential(
				new Delay(1550),
				new AutonOutake(-0.8, 250)),
			new DriveProfile("/home/lvuser/CL2.csv")
		));
		addSequential(new AutonStopMotors());
		
		addSequential(new MakeParallel(
				new MakeSequential(
					new Delay(500),
					new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.CUBE_PILE)
					),
				new DriveProfile("/home/lvuser/CL1.csv")
			));
			addSequential(new AutonStopMotors());
			
			addSequential(new MakeParallel(
					new AutonOpenClaw(),
					new Intake(),	
					new DriveCurve(3, 0)));
			addSequential(new AutonStopMotors());
			
			addSequential(new MakeParallel(
					new MakeSequential(
						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH)),
					new DriveCurve(-3, 0)));
			addSequential(new AutonStopMotors());
			
			addSequential(new MakeParallel(new MakeSequential(
					new Delay(1550),
					new AutonOutake(-0.8, 250)),
				new DriveProfile("/home/lvuser/CL2.csv")
			));
			addSequential(new AutonStopMotors());
		
	}
}
