package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.CloseClaw;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.OpenClaw;
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

public class AutonLR extends CommandGroup {
	public AutonLR(){
		
		//drive to right scale and place cube
		addSequential(new MakeParallel(
				new MakeSequential(
					new WristVertical(),
					new Delay(3500),
					new AutonOutake(0.7),
//					new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
//					new WristOverTop(),
					new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP, 24, 3),
					new AutonOutake(-1, 200)
				),
				new DriveProfile("/home/lvuser/LR0.csv")
//				new DriveProfile("/home/lvuser/left_to_right_scale.csv")
			));
		addSequential(new AutonStopMotors());

//		addSequential(new WristOverTop());
//		addSequential(new AutonOutake(-0.65));
		
		
		//bring elevator down and drive to get cube
//		addSequential(new WristBottom());
		addSequential(new MakeParallel(
				new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM, 9001, 0),
				new Intake(),
				new DriveProfile("/home/lvuser/LR1.csv")
			));
			addSequential(new AutonStopMotors());
//		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
		
//		addSequential(new MakeParallel(new Intake(), new DriveProfile("/home/lvuser/LR1.csv")));
		
		
//		addParallel(new Intake());
////		addParallel(new OpenClaw());
//		addSequential(new DriveProfile("/home/lvuser/LR1.csv"));
		
//		addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
//		addSequential(new CloseClaw());
//		addSequential(new AutonStopMotors());
		//drive back to scale and place another cube
			addSequential(new MakeParallel(
					new MakeSequential(
						new AutonOutake(0.7, 250),
						//new WristVertical(),
						new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH, 9001, 2),
						/*//new Delay(500),
						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
						new WristOverTop(),*/
						new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP, 24, 3),
						new AutonOutake(-1, 200)
					),
					new DriveProfile("/home/lvuser/LR2.csv")
//					new DriveProfile("/home/lvuser/right_switch_to_right_scale_special.csv")
				));
		addSequential(new AutonStopMotors());

		//grab third cube
		addSequential(new MakeParallel(
//				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
				new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM, 9001, 0),
				new Intake(),
				new DriveProfile("/home/lvuser/LR3.csv")
		));
		addSequential(new AutonStopMotors());
		
		// Go back and place a third cube in scale
		addSequential(new MakeParallel(
			new MakeSequential(
				new AutonOutake(0.7, 250),
				//new WristVertical(), 
				new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH, 9001, 2),
				new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP, 24, 3), 
				new AutonOutake(-1, 200)
				/*//new Delay(500),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
				new WristOverTop(),*/
				
			),
			new DriveProfile("/home/lvuser/LR4.csv")
		));
		addSequential(new AutonStopMotors());
	}
}
