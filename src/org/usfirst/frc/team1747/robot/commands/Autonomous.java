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
    	
    	switch (robotPosition) {
    		case LEFT:
    			if(scoringPositions[1] == 'L'){
    				addSequential(new DriveProfile(""));
    				
        			if(scoringPositions[0] == 'L'){
        				
        			}
        			else if(scoringPositions[0] == 'R'){
        				
        			}
    			}
    			else if(scoringPositions[1] == 'R'){
        			if(scoringPositions[0] == 'L'){
        				
        			}
        			else if(scoringPositions[0] == 'R'){
        				
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
        			if(scoringPositions[0] == 'L'){
        				
        			}
        			else if(scoringPositions[0] == 'R'){
        				
        			}
    			}
    			else if(scoringPositions[1] == 'R'){
        			if(scoringPositions[0] == 'L'){
        				
        			}
        			else if(scoringPositions[0] == 'R'){
        				
        			}
    			}
    	}
    	
    	// Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
