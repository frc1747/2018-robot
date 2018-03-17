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
public class AutonLLL extends CommandGroup {

    public AutonLLL() {
    	//drive to scale and place cube
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
		//bring elvator down and grab new cube
		addSequential(new MakeParallel(
				new MakeSequential(
				new WristBottom(),
				new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
			)
				));
		//addParallel(new WristBottom());
		addParallel(new Intake());
		
		addSequential(new DriveProfile("/home/lvuser/LLL1.csv"));
		addSequential(new CloseClaw());
		
		addSequential(new AutonStopMotors());
		
		//place cube in switch
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
		addSequential(new WristBottom());
		addSequential(new DriveCurve(0.8, -15));
		addSequential(new AutonOutake());
		
		addParallel(new WristTop());
    	addSequential(new DriveCurve(-1,0));
		addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));

    }
}
