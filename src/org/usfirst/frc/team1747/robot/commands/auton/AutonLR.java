package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.CloseClaw;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.OpenClaw;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.drive.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.reset.AutonStopMotors;
import org.usfirst.frc.team1747.robot.commands.wrist.WristBottom;
import org.usfirst.frc.team1747.robot.commands.wrist.WristOverTop;
import org.usfirst.frc.team1747.robot.commands.wrist.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import lib.frc1747.commands.MakeParallel;
import lib.frc1747.commands.MakeSequential;

public class AutonLR extends CommandGroup {
	public AutonLR(){
		
		//drive to right scale and place cube
		addSequential(new MakeParallel(
				new MakeSequential(
					new WristVertical(),
					new Delay(4500),
					new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
				),
				new DriveProfile("/home/lvuser/LR0.csv")
//				new DriveProfile("/home/lvuser/left_to_right_scale.csv")
			));
		addSequential(new WristOverTop());
		addSequential(new AutonOutake(-0.65));
		//bring elevator down and drive to get cube
		addSequential(new WristBottom());
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
		
//		addSequential(new MakeParallel(new Intake(), new DriveProfile("/home/lvuser/LR1.csv")));
		addParallel(new Intake());
////		addParallel(new OpenClaw());
		addSequential(new DriveProfile("/home/lvuser/LR1.csv"));
		
//		addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
//		addSequential(new CloseClaw());
		addSequential(new AutonStopMotors());
		//drive back to scale and place another cube
		addSequential(new MakeParallel(
				new MakeSequential(
						new AutonOutake(0.7),
						new Delay(250),
						new WristVertical(),
						new Delay(500),
						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
						new WristOverTop(),
						new AutonOutake(-0.6, 500),
						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
					),
					new DriveProfile("/home/lvuser/LR2.csv")
//					new DriveProfile("/home/lvuser/right_switch_to_right_scale_special.csv")
				));
	}
}
