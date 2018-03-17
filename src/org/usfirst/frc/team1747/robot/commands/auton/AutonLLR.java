package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristBottom;
import org.usfirst.frc.team1747.robot.commands.WristOverTop;
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
public class AutonLLR extends CommandGroup {

    public AutonLLR() {
    	//place cube in close switch by doing 180
    	addSequential(new MakeParallel(
				new MakeSequential(
					new WristVertical(),
					new Delay(750),
					new WristBottom(),
					new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH)
				),
				new DriveProfile("/home/lvuser/LLR0.csv")
//				new DriveProfile("/home/lvuser/left_to_left_switch_special.csv")
			));
		addSequential(new AutonStopMotors());
		
		addSequential(new MakeParallel(
				new MakeSequential(
					new Delay(800),
					new AutonOutake()
				),
						new DriveCurve(0, 180)
			));
		addSequential(new AutonStopMotors());
		//drive to other side of the switch and pickup cube
		
		addSequential(new MakeParallel(
					new MakeSequential(
							new WristVertical(),
							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
    						new Delay(1000),
    						new WristBottom(),
    						new Intake()
    					),            	    					
					new DriveProfile("/home/lvuser/LLR1.csv")
//    					new DriveProfile("/home/lvuser/left_to_right_switch_special.csv")
    				));
		addSequential(new AutonStopMotors());
		//drive back to scale and place cube
		addSequential(new MakeParallel(
				new MakeSequential(
						new WristVertical(),
						new Delay(1000),
						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
						new WristOverTop(),
						new AutonOutake()
					),
					new DriveProfile("/home/lvuser/LLR2.csv")
//					new DriveProfile("/home/lvuser/right_switch_to_right_scale_special.csv")
				));
	addSequential(new AutonStopMotors());
	addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
    }
}
