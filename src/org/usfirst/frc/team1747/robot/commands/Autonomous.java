package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.Robot.AutonChoice;
import org.usfirst.frc.team1747.robot.Robot.AutonRobotPosition;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Autonomous extends CommandGroup {

    public Autonomous(AutonRobotPosition robotPosition, AutonChoice choice) {
    	DriverStation ds = DriverStation.getInstance();
    	String gameMessage = ds.getGameSpecificMessage();
    	char[] scoringPositions = gameMessage.toCharArray();
    	
    	addSequential(new ZeroElevatorEncoder());
    	addSequential(new ZeroSensors());
    	switch (robotPosition) {
    		case LEFT:
    			if(scoringPositions[1] == 'L'){
    				addSequential(new MakeParallel(
    					new MakeSequential(
    						new WristVertical(),
    						new Delay(2500)
//    						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
    					),
    					new DriveProfile("/home/lvuser/left_to_left_scale.csv")
    				));
    				
    				addSequential(new WristOverTop());
    				addSequential(new AutonOutake());
    				
    				addParallel(new MakeSequential(
    	    			new WristBottom()
//    	    			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
    				));
        			if(scoringPositions[0] == 'L') {
        				if(choice == AutonChoice.SCALE_SWITCH){
//	        				addSequential(new WristBottom());
        					addParallel(new Intake());
	        				addSequential(new DriveProfile("/home/lvuser/left_to_left_switch.csv"));
	        				addSequential(new CloseClaw());
	        				addSequential(new AutonStopMotors());
//	        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
	        				addSequential(new WristBottom());
	        				addSequential(new DriveCurve(0.8, -15));
	        				addSequential(new AutonOutake());
	        				
        				}else if(choice == AutonChoice.SCALE_SCALE){
        					
        				}
        			}
        			else if(scoringPositions[0] == 'R'){
        				if(choice == AutonChoice.SCALE_SWITCH){
//	        				addSequential(new WristBottom());
        					addParallel(new Intake());
        					addParallel(new OpenClaw());
	        				addSequential(new DriveProfile("/home/lvuser/left_to_right_switch.csv"));
	        				addSequential(new CloseClaw());
	        				addSequential(new AutonStopMotors());
//	        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
	        				addSequential(new WristBottom());
	        				addSequential(new DriveCurve(0.8, -15));
	        				addSequential(new AutonOutake());
	        					

        				}else if(choice == AutonChoice.SCALE_SCALE){
        					
        				}
        			}
    			}
    			else if(scoringPositions[1] == 'R'){
    				addSequential(new MakeParallel(
        					new MakeSequential(
        						new WristVertical(),
        						new Delay(2500)
//        						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
        					),
        					new DriveProfile("/home/lvuser/left_to_right_scale.csv")
        				));
        				
        				addSequential(new WristOverTop());
        				addSequential(new AutonOutake());
        				
        				addParallel(new MakeSequential(
        	    			new WristBottom()
//        	    			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
        				));
    				
        			if(scoringPositions[0] == 'L'){
        				if(choice == AutonChoice.SCALE_SWITCH){
	        				addSequential(new WristBottom());
	        				addParallel(new Intake());
	        				addSequential(new DriveProfile("/home/lvuser/right_to_left_switch.csv"));
	        				addSequential(new WristBottom());
	        				addParallel(new Intake());  
	        				addSequential(new WristVertical());
//	        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
	        				addSequential(new WristVertical());
	        				addSequential(new Delay(500));
	        				addSequential(new AutonOutake());
        					}else if(choice == AutonChoice.SCALE_SCALE){
        						
        					}
        				}
        			else if(scoringPositions[0] == 'R'){
        				addSequential(new WristBottom());
        				addParallel(new Intake());
        				addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
        				
        				addSequential(new CloseClaw());
        				addParallel(new Intake());
        				addSequential(new Delay(400));
        				addSequential(new WristBottom());
        				addParallel(new Intake());  
        				addSequential(new WristVertical());
//        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
        				addSequential(new WristVertical());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
    			}
    			break;
    		case CENTER:
    			if(scoringPositions[0] == 'L'){
    				addSequential(new DriveProfile("/home/lvuser/center_to_left_switch.csv"));
    			}
    			else if(scoringPositions[0] == 'R'){
    				addSequential(new DriveProfile("/home/lvuser/center_to_right_switch.csv"));
    			}
    			break;
    		case RIGHT:
    			if(scoringPositions[1] == 'L'){
    				
    				addSequential(new DriveProfile("/home/lvuser/right_to_left_scale.csv"));
    				addSequential(new WristVertical());
    				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP));
    				addSequential(new Delay(500));
    				addSequential(new WristTop());
    				addSequential(new Delay(500));
    				addSequential(new AutonOutake());
    				addSequential(new WristVertical());
    				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
    				addSequential(new ZeroSensors());
    				
        			if(scoringPositions[0] == 'L'){
        				addSequential(new WristBottom());
        				addParallel(new Intake());
        				addParallel(new OpenClaw());
        				addSequential(new DriveProfile("/home/lvuser/left_to_left_switch.csv"));
        				addSequential(new WristBottom());
        				addParallel(new Intake());  
        				addSequential(new WristVertical());
        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
        				addSequential(new WristVertical());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
        			else if(scoringPositions[0] == 'R'){
        				addSequential(new WristBottom());
        				addParallel(new Intake());
        				addParallel(new OpenClaw());
        				addSequential(new DriveProfile("/home/lvuser/left_to_right_switch.csv"));
        				addSequential(new WristBottom());
        				addParallel(new Intake());  
        				addSequential(new WristVertical());
        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
        				addSequential(new WristVertical());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
    			}
    			else if(scoringPositions[1] == 'R'){
    				
    				addSequential(new DriveProfile("/home/lvuser/right_to_right_scale.csv"));
    				addSequential(new WristVertical());
    				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP));
    				addSequential(new Delay(500));
    				addSequential(new WristTop());

    				addSequential(new Delay(500));
    				addSequential(new AutonOutake());
    				addSequential(new WristVertical());
    				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
    				addSequential(new ZeroSensors());
    				
        			if(scoringPositions[0] == 'L'){
        				addSequential(new WristBottom());
        				addParallel(new Intake());
        				addParallel(new OpenClaw());
        				addSequential(new DriveProfile("/home/lvuser/right_to_left_switch.csv"));
        				addSequential(new WristBottom());
        				addParallel(new Intake());  
        				addSequential(new WristVertical());
        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
        				addSequential(new WristVertical());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
        			else if(scoringPositions[0] == 'R'){
        				addSequential(new WristBottom());
        				addParallel(new Intake());
        				addParallel(new OpenClaw());
        				addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
        				addSequential(new Delay(500));
        				addSequential(new WristVertical());
        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
        				addSequential(new WristBottom());
        				addSequential(new Delay(500));
        				addSequential(new AutonOutake());
        			}
    			}
    	}
    	
    	addParallel(new WristTop());
    	addSequential(new DriveCurve(-1,0));
//    	addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
    }
}
