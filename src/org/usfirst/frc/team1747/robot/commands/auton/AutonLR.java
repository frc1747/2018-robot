package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.AutonStopMotors;
import org.usfirst.frc.team1747.robot.commands.CloseClaw;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.MakeParallel;
import org.usfirst.frc.team1747.robot.commands.MakeSequential;
import org.usfirst.frc.team1747.robot.commands.OpenClaw;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristBottom;
import org.usfirst.frc.team1747.robot.commands.WristOverTop;
import org.usfirst.frc.team1747.robot.commands.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

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
		addSequential(new AutonOutake());
		//bring elevator down and drive to get cube
		addSequential(new WristBottom());
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));

		addParallel(new Intake());
//		addParallel(new OpenClaw());
		addSequential(new DriveProfile("/home/lvuser/LR1.csv"));
//		addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
//		addSequential(new CloseClaw());
		addSequential(new AutonStopMotors());
		//drive back to scale and place another cube
		addSequential(new MakeParallel(
				new MakeSequential(
						new WristVertical(),
						new Delay(1000),
						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
						new WristOverTop(),
						new AutonOutake(),
						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
					),
					new DriveProfile("/home/lvuser/LR2.csv")
//					new DriveProfile("/home/lvuser/right_switch_to_right_scale_special.csv")
				));
	}
}
