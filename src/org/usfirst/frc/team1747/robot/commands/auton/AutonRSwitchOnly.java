package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.AutonOpenClaw;
import org.usfirst.frc.team1747.robot.commands.AutonOutake;
import org.usfirst.frc.team1747.robot.commands.Delay;
import org.usfirst.frc.team1747.robot.commands.Intake;
import org.usfirst.frc.team1747.robot.commands.SetElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.WristAtElevatorPosition;
import org.usfirst.frc.team1747.robot.commands.drive.DriveCurve;
import org.usfirst.frc.team1747.robot.commands.drive.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.reset.AutonStopMotors;
import org.usfirst.frc.team1747.robot.commands.wrist.WristBottom;
import org.usfirst.frc.team1747.robot.commands.wrist.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import lib.frc1747.commands.MakeParallel;
import lib.frc1747.commands.MakeSequential;

/**
 *
 */
public class AutonRSwitchOnly extends CommandGroup {
	    
    public AutonRSwitchOnly(){
    	//path backwards to line up for first cube
    	addSequential(new MakeParallel(
    			new WristVertical(),
    			new DriveProfile("/home/lvuser/RRSW0.csv")
    		));
    	addSequential(new AutonStopMotors());
    	
    	addSequential(new MakeParallel(
    			new MakeSequential(
    					new WristAtElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH, 9001, 2),
    					new Delay(500),
    					new WristBottom(),
    					new AutonOutake(-0.4, 360)
    				),
    				new DriveCurve(3, 0)
    		));
    	addSequential(new AutonStopMotors());
    	
    	addSequential(new MakeParallel(
    			new MakeSequential(
    					new Delay(300),
    					new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
    			),
    			new DriveCurve(-2, 0)
    	));
    	addSequential(new AutonStopMotors());
    	
    	addSequential(new MakeParallel(
    			new AutonOpenClaw(),
    			new Intake(),
    			new DriveCurve(2, 0)
    			));
    	addSequential(new AutonStopMotors());
    	
    	addSequential(new MakeParallel(
    			new MakeSequential(
    					new Delay(200),
    					new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH)
    				),
    			new DriveCurve(-0.5, 0)
    		));
    	addSequential(new AutonStopMotors());
    	
    	addSequential(new MakeParallel(
    			new MakeSequential(
    					new Delay(500),
    					new AutonOutake(-0.5, 360)
    				),
    			new DriveCurve(1.5, 0)
    		));
    	addSequential(new AutonStopMotors());
    	
    	addSequential(new MakeParallel(
    			new MakeSequential(
    					new Delay(400),
    					new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)),
    			new DriveCurve(-3, 0)
    			));
    	addSequential(new AutonStopMotors());
    	
    	addSequential(new MakeParallel(
    			new Intake(),
    			new DriveCurve(3, -50)
    			));
    	addSequential(new AutonStopMotors());
    	
    	addSequential(new MakeParallel(
    			new MakeSequential(
    					new Delay(400),
    					new AutonOutake(-0.5, 360)
    				),
    			new DriveCurve(1, 50)
    		));
    	addSequential(new AutonStopMotors());
    }
}
