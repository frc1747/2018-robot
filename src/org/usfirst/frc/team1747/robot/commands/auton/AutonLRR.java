package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.CloseClaw;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.DriveCurve;
import org.usfirst.frc.team1747.robot.commands.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.MakeParallel;
import org.usfirst.frc.team1747.robot.commands.MakeSequential;
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
public class AutonLRR extends CommandGroup {

    public AutonLRR() {
    	//drive to right scale and place cube
    	addSequential(new MakeParallel(
				new MakeSequential(
					new WristVertical(),
					new Delay(4750),
					new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
				),
				new DriveProfile("/home/lvuser/LRR0.csv")
//				new DriveProfile("/home/lvuser/left_to_right_scale.csv")
			));
			
			addSequential(new WristOverTop());
			addSequential(new AutonOutake());
			//lower elevator and pick up cube at switch
			addParallel(new MakeSequential(
    			new WristBottom(),
    			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
			));
			
//		addSequential(new WristBottom());
		addParallel(new Intake());
		addSequential(new DriveProfile("/home/lvuser/LRR1.csv"));
//		addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
		//place cube in switch
		addSequential(new CloseClaw());
		addParallel(new Intake());
		addSequential(new Delay(400));
		addSequential(new WristBottom());
		addParallel(new Intake());  
		addSequential(new WristVertical());
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
		addSequential(new WristVertical());
		addSequential(new Delay(500));
		addSequential(new AutonOutake());
		
		addParallel(new WristTop());
    	addSequential(new DriveCurve(-1,0));
    	addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));

    }
}
