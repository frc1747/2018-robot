package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.Robot.AutonRobotPosition;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Autonomous extends CommandGroup {

    public Autonomous(AutonRobotPosition robotPosition) {
    	DriverStation ds = DriverStation.getInstance();
    	String gameMessage = ds.getGameSpecificMessage();
    	char[] scoringPositions = gameMessage.toCharArray();
    	
    	addSequential(new ZeroSensors());
    	switch (robotPosition) {
    		case LEFT:
    			if(scoringPositions[1] == 'L'){
    				
    				addSequential(new DriveProfile(""));
    				addSequential(new ElevatorTop());
    				addSequential(new ElevatorTop());
    				addSequential(new Delay(500));
    				addSequential(new WristUp());
    				addSequential(new WristUp());
    				addSequential(new Delay(500));
    				addSequential(new AutonOutake());
    				addSequential(new WristDown());
    				addSequential(new WristDown());
    				addSequential(new ElevatorDown());
    				addSequential(new ElevatorDown());
    				addSequential(new ZeroSensors());
    				
        			if(scoringPositions[0] == 'L'){
        				addSequential(new DriveProfile(""));
        				addParallel(new Intake());  				
        				addSequential(new ElevatorTop());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
        			else if(scoringPositions[0] == 'R'){
        				addSequential(new DriveProfile(""));
        				addParallel(new Intake());
        				addSequential(new ElevatorTop());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
    			}
    			else if(scoringPositions[1] == 'R'){
    				
    				addSequential(new DriveProfile(""));
    				addSequential(new ElevatorTop());
    				addSequential(new ElevatorTop());
    				addSequential(new Delay(500));
    				addSequential(new WristUp());
    				addSequential(new WristUp());
    				addSequential(new Delay(500));
    				addSequential(new AutonOutake());
    				addSequential(new WristDown());
    				addSequential(new WristDown());
    				addSequential(new ElevatorDown());
    				addSequential(new ElevatorDown());
    				addSequential(new ZeroSensors());
    				
        			if(scoringPositions[0] == 'L'){
        				addSequential(new DriveProfile(""));
        				addParallel(new Intake());
        				addSequential(new ElevatorTop());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
        			else if(scoringPositions[0] == 'R'){
        				addSequential(new DriveProfile(""));
        				addParallel(new Intake());
        				addSequential(new ElevatorTop());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
    			}
    			
    		case CENTER:
    			if(scoringPositions[0] == 'L'){
    				addSequential(new DriveProfile(""));
    			}
    			else if(scoringPositions[0] == 'R'){
    				addSequential(new DriveProfile(""));
    			}
    		case RIGHT:
    			if(scoringPositions[1] == 'L'){
    				
    				addSequential(new DriveProfile(""));
    				addSequential(new ElevatorTop());
    				addSequential(new ElevatorTop());
    				addSequential(new Delay(500));
    				addSequential(new WristUp());
    				addSequential(new WristUp());
    				addSequential(new Delay(500));
    				addSequential(new AutonOutake());
    				addSequential(new WristDown());
    				addSequential(new WristDown());
    				addSequential(new ElevatorDown());
    				addSequential(new ElevatorDown());
    				addSequential(new ZeroSensors());
    				
        			if(scoringPositions[0] == 'L'){
        				addSequential(new DriveProfile(""));
        				addParallel(new Intake());
        				addSequential(new ElevatorTop());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
        			else if(scoringPositions[0] == 'R'){
        				addSequential(new DriveProfile(""));
        				addParallel(new Intake());
        				addSequential(new ElevatorTop());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
    			}
    			else if(scoringPositions[1] == 'R'){
    				
    				addSequential(new DriveProfile(""));
    				addSequential(new ElevatorTop());
    				addSequential(new ElevatorTop());
    				addSequential(new Delay(500));
    				addSequential(new WristUp());
    				addSequential(new WristUp());
    				addSequential(new Delay(500));
    				addSequential(new AutonOutake());
    				addSequential(new WristDown());
    				addSequential(new WristDown());
    				addSequential(new ElevatorDown());
    				addSequential(new ElevatorDown());
    				addSequential(new ZeroSensors());
    				
        			if(scoringPositions[0] == 'L'){
        				addSequential(new DriveProfile(""));
        				addParallel(new Intake());
        				addSequential(new ElevatorTop());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
        			else if(scoringPositions[0] == 'R'){
        				addSequential(new DriveProfile(""));
        				addParallel(new Intake());
        				addSequential(new ElevatorTop());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
    			}
    	}
    }
}
