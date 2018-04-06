package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.Robot.AutonChoice;
import org.usfirst.frc.team1747.robot.Robot.AutonRobotPosition;
import org.usfirst.frc.team1747.robot.commands.drive.DriveCurve;
import org.usfirst.frc.team1747.robot.commands.drive.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.reset.AutonStopMotors;
import org.usfirst.frc.team1747.robot.commands.reset.ZeroElevatorEncoder;
import org.usfirst.frc.team1747.robot.commands.reset.ZeroSensors;
import org.usfirst.frc.team1747.robot.commands.wrist.WristBottom;
import org.usfirst.frc.team1747.robot.commands.wrist.WristOverTop;
import org.usfirst.frc.team1747.robot.commands.wrist.WristTop;
import org.usfirst.frc.team1747.robot.commands.wrist.WristVertical;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem.ElevatorPositions;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import lib.frc1747.commands.MakeParallel;
import lib.frc1747.commands.MakeSequential;

/**
 *
 */
public class Autonomous extends CommandGroup {

    public Autonomous(AutonRobotPosition robotPosition, AutonChoice choice) {
    	DriverStation ds = DriverStation.getInstance();
    	String gameMessage = ds.getGameSpecificMessage();
    	char[] scoringPositions = gameMessage.toCharArray();
    	/*scoringPositions[1] = 'L';
    	scoringPositions[0] = 'L';*/

    	
    	addSequential(new ZeroElevatorEncoder());
    	addSequential(new ZeroSensors());
    	
    	// New Stuff
    	
    	
    	
    	switch (robotPosition) {
    		case LEFT:
    			if(scoringPositions[1] == 'L'){
    				addSequential(new MakeParallel(
    					new MakeSequential(
    						new WristVertical(),
    						new Delay(2500),
    						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
    					),
    					new DriveProfile("/home/lvuser/LLL0.csv")
//    					new DriveProfile("/home/lvuser/left_to_left_scale.csv")
    				));
    				addSequential(new AutonStopMotors());
    				addSequential(new WristOverTop());
    				addSequential(new AutonOutake());
    				
    				/*addParallel(new MakeSequential(
    	    			new WristBottom(),
    	    			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
    				));*/
    				
    				addSequential(new WristBottom());
    				addParallel(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
        			if(scoringPositions[0] == 'L') {
        				if(choice == AutonChoice.SCALE_SWITCH){
//	        				addSequential(new WristBottom());
        					addParallel(new Intake());
        					addSequential(new DriveProfile("/home/lvuser/LLL1.csv"));
//	        				addSequential(new DriveProfile("/home/lvuser/left_to_left_switch.csv"));
	        				addSequential(new CloseClaw());
	        				addSequential(new AutonStopMotors());
	        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
	        				addSequential(new WristBottom());
	        				addSequential(new DriveCurve(0.8, -15));
	        				addSequential(new AutonOutake());
	        				
	        				addParallel(new WristTop());
	        		    	addSequential(new DriveCurve(-1,0));
	        				
        				}else if(choice == AutonChoice.SCALE_SCALE){
        					addParallel(new Intake());
        					addSequential(new DriveProfile("/home/lvuser/LL1.csv"));

//	        				addSequential(new DriveProfile("/home/lvuser/left_to_left_switch.csv"));
	        				addSequential(new CloseClaw());
	        				addSequential(new AutonStopMotors());
	        				
	        				addSequential(new MakeParallel(
        	    					new MakeSequential(
        	    							new WristVertical(),
        	    							new Delay(1000),
        	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
            	    						new WristOverTop(),
            	    						new AutonOutake(),
        	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
            	    					),
        	    						new DriveProfile("/home/lvuser/LL2.csv")

//            	    					new DriveProfile("/home/lvuser/left_switch_to_left_scale.csv")
            	    				));
	        				
        				}
        			}
        			else if(scoringPositions[0] == 'R'){
        				if(choice == AutonChoice.SCALE_SWITCH){
//	        				addSequential(new WristBottom());
        					addParallel(new Intake());
        					addParallel(new OpenClaw());
        					addSequential(new DriveProfile("/home/lvuser/LRL1.csv"));
//	        				addSequential(new DriveProfile("/home/lvuser/left_to_right_switch.csv"));
	        				addSequential(new CloseClaw());
	        				addSequential(new AutonStopMotors());
	        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
	        				addSequential(new WristBottom());
	        				addSequential(new DriveCurve(0.8, -15));
	        				addSequential(new AutonOutake());
	        				
	        				addParallel(new WristTop());
	        		    	addSequential(new DriveCurve(-1,0));
	        					

        				}else if(choice == AutonChoice.SCALE_SCALE){
        					addParallel(new Intake());
//        					addParallel(new OpenClaw());
        					addSequential(new DriveProfile("/home/lvuser/LL1.csv"));
//	        				addSequential(new DriveProfile("/home/lvuser/left_to_left_switch.csv"));
	        				addSequential(new CloseClaw());
	        				addSequential(new AutonStopMotors());
	        				
	        				addSequential(new MakeParallel(
        	    					new MakeSequential(
        	    							new WristVertical(),
        	    							new Delay(1000),
        	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
            	    						new WristOverTop(),
            	    						new AutonOutake(),
        	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
            	    					),
        	    						new DriveProfile("/home/lvuser/LL2.csv")
//            	    					new DriveProfile("/home/lvuser/left_switch_to_left_scale.csv")
            	    				));
        				}
        			}
    			}
    			else if(scoringPositions[1] == 'R'){
    				
        			if(scoringPositions[0] == 'L'){
        				if(choice == AutonChoice.SCALE_SWITCH){
        					addSequential(new MakeParallel(
        	    					new MakeSequential(
        	    						new WristVertical(),
        	    						new Delay(750),
        	    						new WristBottom(),
        	    						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH)
        	    					),
        	    					new DriveProfile("/home/lvuser/LLR0.csv")
//        	    					new DriveProfile("/home/lvuser/left_to_left_switch_special.csv")
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
        					
        					
        					addSequential(new MakeParallel(
            	    					new MakeSequential(
            	    							new WristVertical(),
            	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
                	    						new Delay(1000),
                	    						new WristBottom(),
                	    						new Intake()
                	    					),            	    					
            	    					new DriveProfile("/home/lvuser/LLR1.csv")
//                	    					new DriveProfile("/home/lvuser/left_to_right_switch_special.csv")
                	    				));
        					addSequential(new AutonStopMotors());
        					
        					addSequential(new MakeParallel(
        	    					new MakeSequential(
        	    							new WristVertical(),
        	    							new Delay(1000),
        	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
            	    						new WristOverTop(),
            	    						new AutonOutake()
            	    					),
        	    						new DriveProfile("/home/lvuser/LLR2.csv")
//            	    					new DriveProfile("/home/lvuser/right_switch_to_right_scale_special.csv")
            	    				));
    					addSequential(new AutonStopMotors());
    					addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
        					
        					/*
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
	        				*/
        					}else if(choice == AutonChoice.SCALE_SCALE){
        						addSequential(new MakeParallel(
                    					new MakeSequential(
                    						new WristVertical(),
                    						new Delay(4500),
                    						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
                    					),
                    					new DriveProfile("/home/lvuser/LR0.csv")
//                    					new DriveProfile("/home/lvuser/left_to_right_scale.csv")
                    				));
        						addSequential(new WristOverTop());
                				addSequential(new AutonOutake());
                				addSequential(new WristBottom());
    							addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
        						addParallel(new Intake());
//            					addParallel(new OpenClaw());
            					addSequential(new DriveProfile("/home/lvuser/LR1.csv"));
//    	        				addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
//    	        				addSequential(new CloseClaw());
    	        				addSequential(new AutonStopMotors());
    	        				
    	        				addSequential(new MakeParallel(
            	    					new MakeSequential(
            	    							new WristVertical(),
            	    							new Delay(1000),
            	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
                	    						new WristOverTop(),
                	    						new AutonOutake(),
            	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
                	    					),
            	    						new DriveProfile("/home/lvuser/LR2.csv")
//                	    					new DriveProfile("/home/lvuser/right_switch_to_right_scale.csv")
                	    				));
        					}
        				}
        			else if(scoringPositions[0] == 'R'){
        				if(choice == AutonChoice.SCALE_SWITCH){
        				addSequential(new MakeParallel(
            					new MakeSequential(
            						new WristVertical(),
            						new Delay(4750),
            						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
            					),
            					new DriveProfile("/home/lvuser/LRR0.csv")
//            					new DriveProfile("/home/lvuser/left_to_right_scale.csv")
            				));
            				
        				addSequential(new WristOverTop());
        				addSequential(new AutonOutake());
        				
        				/*addParallel(new MakeSequential(
        	    			new WristBottom(),
        	    			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
        				));*/
        				
        				addSequential(new WristBottom());
        				addParallel(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
        				
//        				addSequential(new WristBottom());
        				addParallel(new Intake());
    					addSequential(new DriveProfile("/home/lvuser/LRR1.csv"));
//        				addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
        				
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
        			}else if(choice == AutonChoice.SCALE_SCALE){
        				addSequential(new MakeParallel(
            					new MakeSequential(
            						new WristVertical(),
            						new Delay(4500),
            						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
            					),
            					new DriveProfile("/home/lvuser/LR0.csv")
//            					new DriveProfile("/home/lvuser/left_to_right_scale.csv")
            				));
						addSequential(new WristOverTop());
        				addSequential(new AutonOutake());
        				addSequential(new WristBottom());
						addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));

						addParallel(new Intake());
//    					addParallel(new OpenClaw());
    					addSequential(new DriveProfile("/home/lvuser/LR1.csv"));
//        				addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
        				addSequential(new CloseClaw());
        				addSequential(new AutonStopMotors());
        				
        				addSequential(new MakeParallel(
    	    					new MakeSequential(
    	    							new WristVertical(),
    	    							new Delay(1000),
    	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
        	    						new WristOverTop(),
        	    						new AutonOutake(),
    	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
        	    					),
    	    						new DriveProfile("/home/lvuser/LR2.csv")
//        	    					new DriveProfile("/home/lvuser/right_switch_to_right_scale_special.csv")
        	    				));
        				}
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
    			if(scoringPositions[1] == 'R'){
    				addSequential(new MakeParallel(
    					new MakeSequential(
    						new WristVertical(),
    						new Delay(2500),
    						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
    					),
    					new DriveProfile("/home/lvuser/RRR0.csv")
//    					new DriveProfile("/home/lvuser/right_to_right_scale.csv")
    				));
    				addSequential(new AutonStopMotors());
    				addSequential(new WristOverTop());
    				addSequential(new AutonOutake());
    				
    				addParallel(new MakeSequential(
    	    			new WristBottom(),
    	    			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
    				));
        			if(scoringPositions[0] == 'R') {
        				if(choice == AutonChoice.SCALE_SWITCH){
//	        				addSequential(new WristBottom());
        					addParallel(new Intake());
        					addSequential(new DriveProfile("/home/lvuser/RRR1.csv"));
//	        				addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
	        				addSequential(new CloseClaw());
	        				addSequential(new AutonStopMotors());
	        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
	        				addSequential(new WristBottom());
	        				addSequential(new DriveCurve(0.8, -15));
	        				addSequential(new AutonOutake());
	        				
	        				addParallel(new WristTop());
	        		    	addSequential(new DriveCurve(-1,0));
	        				
        				}else if(choice == AutonChoice.SCALE_SCALE){
        					addParallel(new Intake());
        					addParallel(new CloseClaw());
        					addSequential(new DriveProfile("/home/lvuser/RR1.csv"));
//	        				addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
	        				addSequential(new CloseClaw());
	        				addSequential(new AutonStopMotors());
	        				
	        				addSequential(new MakeParallel(
        	    					new MakeSequential(
        	    							new WristVertical(),
        	    							new Delay(1000),
        	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
            	    						new WristOverTop(),
            	    						new AutonOutake(),
        	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
            	    					),
        	    						new DriveProfile("/home/lvuser/RR2.csv")
//            	    					new DriveProfile("/home/lvuser/right_switch_to_right_scale.csv")
            	    				));
	        				
        				}
        			}
        			else if(scoringPositions[0] == 'L'){
        				if(choice == AutonChoice.SCALE_SWITCH){
//	        				addSequential(new WristBottom());
        					addParallel(new Intake());
        					addParallel(new OpenClaw());
        					addSequential(new DriveProfile("/home/lvuser/RLR1.csv"));
//	        				addSequential(new DriveProfile("/home/lvuser/right_to_left_switch.csv"));
	        				addSequential(new CloseClaw());
	        				addSequential(new AutonStopMotors());
	        				addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH));
	        				addSequential(new WristBottom());
	        				addSequential(new DriveCurve(0.8, -15));
	        				addSequential(new AutonOutake());
	        				
	        				addParallel(new WristTop());
	        		    	addSequential(new DriveCurve(-1,0));
	        					

        				}else if(choice == AutonChoice.SCALE_SCALE){
        					addParallel(new Intake());
        					addParallel(new OpenClaw());
        					addSequential(new DriveProfile("/home/lvuser/RR1.csv"));
//	        				addSequential(new DriveProfile("/home/lvuser/right_to_right_switch.csv"));
	        				addSequential(new CloseClaw());
	        				addSequential(new AutonStopMotors());
	        				
	        				addSequential(new MakeParallel(
        	    					new MakeSequential(
        	    							new WristVertical(),
        	    							new Delay(1000),
        	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
            	    						new WristOverTop(),
            	    						new AutonOutake(),
        	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
            	    					),
        	    						new DriveProfile("/home/lvuser/RR2.csv")

//            	    					new DriveProfile("/home/lvuser/right_switch_to_right_scale.csv")
            	    				));
        				}
        			}
    			}
    			else if(scoringPositions[1] == 'L'){
    				
        			if(scoringPositions[0] == 'R'){
        				if(choice == AutonChoice.SCALE_SWITCH){
        					addSequential(new MakeParallel(
        	    					new MakeSequential(
        	    						new WristVertical(),
        	    						new Delay(750),
        	    						new WristBottom(),
        	    						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.SWITCH)
        	    					),
        	    					new DriveProfile("/home/lvuser/RRL0.csv")
//        	    					new DriveProfile("/home/lvuser/right_to_right_switch_special.csv")
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
        					
        					
        					addSequential(new MakeParallel(
            	    					new MakeSequential(
            	    							new WristVertical(),
            	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM),
                	    						new Delay(1000),
                	    						new WristBottom(),
                	    						new Intake()
                	    					),
            	    						new DriveProfile("/home/lvuser/RRL1.csv")
//                	    					new DriveProfile("/home/lvuser/right_to_left_switch_special.csv")
                	    				));
        					addSequential(new AutonStopMotors());
        					
        					addSequential(new MakeParallel(
        	    					new MakeSequential(
        	    							new WristVertical(),
        	    							new Delay(1000),
        	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
            	    						new WristOverTop(),
            	    						new AutonOutake()
            	    					),
        	    						new DriveProfile("/home/lvuser/RRL2.csv")
//            	    					new DriveProfile("/home/lvuser/left_switch_to_left_scale_special.csv")
            	    				));
    					addSequential(new AutonStopMotors());
    					addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
        					
        					/*
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
	        				*/
        					}else if(choice == AutonChoice.SCALE_SCALE){
        						addSequential(new MakeParallel(
                    					new MakeSequential(
                    						new WristVertical(),
                    						new Delay(2500),
                    						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
                    					),
                    					new DriveProfile("/home/lvuser/RL0.csv")
//                    					new DriveProfile("/home/lvuser/right_to_left_scale.csv")
                    				));
        						addSequential(new WristOverTop());
                				addSequential(new AutonOutake());
                				addSequential(new WristBottom());
                				
        						addParallel(new Intake());
            					addParallel(new OpenClaw());
            					addSequential(new DriveProfile("/home/lvuser/RL1.csv"));
//    	        				addSequential(new DriveProfile("/home/lvuser/left_to_left_switch.csv"));
    	        				addSequential(new CloseClaw());
    	        				addSequential(new AutonStopMotors());
    	        				
    	        				addSequential(new MakeParallel(
            	    					new MakeSequential(
            	    							new WristVertical(),
            	    							new Delay(1000),
            	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
                	    						new WristOverTop(),
                	    						new AutonOutake(),
            	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
                	    					),
            	    						new DriveProfile("/home/lvuser/RL2.csv")
//                	    					new DriveProfile("/home/lvuser/left_switch_to_left_scale.csv")
                	    				));
        					}
        				}
        			else if(scoringPositions[0] == 'L'){
        				if(choice == AutonChoice.SCALE_SWITCH){
        				addSequential(new MakeParallel(
            					new MakeSequential(
            						new WristVertical(),
            						new Delay(2500),
            						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
            					),
            					new DriveProfile("/home/lvuser/RLL0.csv")
//            					new DriveProfile("/home/lvuser/right_to_left_scale.csv")
            				));
            				
            				addSequential(new WristOverTop());
            				addSequential(new AutonOutake());
            				
            				addParallel(new MakeSequential(
            	    			new WristBottom(),
            	    			new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
            				));
            				
        				addSequential(new WristBottom());
        				addParallel(new Intake());
    					addSequential(new DriveProfile("/home/lvuser/RLL1.csv"));
//        				addSequential(new DriveProfile("/home/lvuser/left_to_left_switch.csv"));
        				
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
        			}else if(choice == AutonChoice.SCALE_SCALE){
        				addSequential(new MakeParallel(
            					new MakeSequential(
            						new WristVertical(),
            						new Delay(2500),
            						new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP)
            					),
            					new DriveProfile("/home/lvuser/RL0.csv")
//            					new DriveProfile("/home/lvuser/right_to_left_scale.csv")
            				));
						addSequential(new WristOverTop());
        				addSequential(new AutonOutake());
        				addSequential(new WristBottom());
        				
						addParallel(new Intake());
    					addParallel(new OpenClaw());
    					addSequential(new DriveProfile("/home/lvuser/RL1.csv"));
//        				addSequential(new DriveProfile("/home/lvuser/left_to_left_switch.csv"));
        				addSequential(new CloseClaw());
        				addSequential(new AutonStopMotors());
        				
        				addSequential(new MakeParallel(
    	    					new MakeSequential(
    	    							new WristVertical(),
    	    							new Delay(1000),
    	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.TOP),
        	    						new WristOverTop(),
        	    						new AutonOutake(),
    	    							new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM)
        	    					),
    	    						new DriveProfile("/home/lvuser/RL2.csv")
//        	    					new DriveProfile("/home/lvuser/left_switch_to_left_scale_special.csv")
        	    				));
        				}
					}
    			}
    	}
    	
    	
    	
    	addSequential(new SetElevatorPosition(ElevatorSubsystem.ElevatorPositions.BOTTOM));
    }
}
