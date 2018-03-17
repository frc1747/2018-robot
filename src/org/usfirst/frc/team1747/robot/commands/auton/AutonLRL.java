package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.AutonStopMotors;
import org.usfirst.frc.team1747.robot.commands.CloseClaw;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.DriveCurve;
import org.usfirst.frc.team1747.robot.commands.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.MakeParallel;
import org.usfirst.frc.team1747.robot.commands.MakeSequential;
import org.usfirst.frc.team1747.robot.commands.OpenClaw;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristBottom;
import org.usfirst.frc.team1747.robot.commands.WristOverTop;
import org.usfirst.frc.team1747.robot.commands.WristTop;
import org.usfirst.frc.team1747.robot.commands.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

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
//			new DriveProfile("/home/lvuser/left_to_left_scale.csv")
		));
		addSequential(new AutonStopMotors());
		addSequential(new WristOverTop());
		addSequential(new AutonOutake());
		//drive to right switch and get new cube
		addParallel(new MakeSequential(
			new WristBottom(),
			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
		));
//		addSequential(new WristBottom());
		addParallel(new Intake());
		addParallel(new OpenClaw());
		addSequential(new DriveProfile("/home/lvuser/LRL1.csv"));
//		addSequential(new DriveProfile("/home/lvuser/left_to_right_switch.csv"));
		//place cube in right switch
		addSequential(new CloseClaw());
		addSequential(new AutonStopMotors());
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
		addSequential(new WristBottom());
		addSequential(new DriveCurve(0.8, -15));
		addSequential(new AutonOutake());
		
		addParallel(new WristTop());
    	addSequential(new DriveCurve(-1,0));
    	addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));

    }
}
