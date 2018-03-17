package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.Robot.AutonChoice;
import org.usfirst.frc.team1747.robot.Robot.AutonRobotPosition;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLL;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLLL;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLLR;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLR;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLRL;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLRR;
import org.usfirst.frc.team1747.robot.commands.reset.ZeroElevatorEncoder;
import org.usfirst.frc.team1747.robot.commands.reset.ZeroSensors;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Autonomous2 extends CommandGroup {

    public Autonomous2(AutonRobotPosition robotPosition, AutonChoice choice) {
    	DriverStation ds = DriverStation.getInstance();
    	String gameMessage = ds.getGameSpecificMessage();
    	char[] scoringPositions = gameMessage.toCharArray();
    	
    	addSequential(new ZeroElevatorEncoder());
    	addSequential(new ZeroSensors());
    	
    	switch(robotPosition){
    		case LEFT:
    			if(choice == AutonChoice.SCALE_SCALE){
    				if(scoringPositions[1] == 'L'){
    					addSequential(new AutonLL());
    				}else if(scoringPositions[1] == 'R'){
    					addSequential(new AutonLR());
    				}
    			}else if(choice == AutonChoice.SCALE_SWITCH){
    				if(scoringPositions[1] == 'L'){
    					if(scoringPositions[0] == 'L'){
    						addSequential(new AutonLLL());
    					}
    					if(scoringPositions[0] == 'R'){
    						addSequential(new AutonLRL());
    					}
    				}
    				
    				if(scoringPositions[1] == 'R'){
    					if(scoringPositions[0] == 'L'){
    						addSequential(new AutonLLR());
    					}
    					if(scoringPositions[0] == 'R'){
    						addSequential(new AutonLRR());
    					}
    				}
    			}
    			break;
    		case CENTER:
    			break;
    		case RIGHT:
    			break;
    	}
    }
}
