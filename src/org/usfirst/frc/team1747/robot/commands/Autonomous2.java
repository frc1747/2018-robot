package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.Robot.AutonChoice;
import org.usfirst.frc.team1747.robot.Robot.AutonRobotPosition;
import org.usfirst.frc.team1747.robot.commands.auton.AutonCL;
import org.usfirst.frc.team1747.robot.commands.auton.AutonCR;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLLC;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLL;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLL2;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLLL;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLLR;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLR;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLRC;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLRL;
import org.usfirst.frc.team1747.robot.commands.auton.AutonLRR;
import org.usfirst.frc.team1747.robot.commands.auton.AutonRL;
import org.usfirst.frc.team1747.robot.commands.auton.AutonRLC;
import org.usfirst.frc.team1747.robot.commands.auton.AutonRLL;
import org.usfirst.frc.team1747.robot.commands.auton.AutonRLR;
import org.usfirst.frc.team1747.robot.commands.auton.AutonRR;
import org.usfirst.frc.team1747.robot.commands.auton.AutonRR2;
import org.usfirst.frc.team1747.robot.commands.auton.AutonRRC;
import org.usfirst.frc.team1747.robot.commands.auton.AutonRRL;
import org.usfirst.frc.team1747.robot.commands.auton.AutonRRR;
import org.usfirst.frc.team1747.robot.commands.reset.ZeroElevatorEncoder;
import org.usfirst.frc.team1747.robot.commands.reset.ZeroSensors;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import com.tigerhuang.gambezi.dashboard.GambeziDashboard;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Autonomous2 extends CommandGroup {

    public Autonomous2(AutonRobotPosition robotPosition, AutonChoice choice) {
    	DriverStation ds = DriverStation.getInstance();
    	String gameMessage = ds.getGameSpecificMessage();
//    	String gameMessage = "LLL";
//    	String gameMessage = SmartDashboard.getString("AutonGameData", "LLL");
    	char[] scoringPositions = gameMessage.toCharArray();
    	
    	addSequential(new ZeroElevatorEncoder());
    	addSequential(new ZeroSensors());
    	
    	switch(robotPosition){
    		case LEFT:
    			if(choice == AutonChoice.SCALE_SCALE){
    				if(scoringPositions[1] == 'L'){
    					addSequential(new AutonLL2());
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
    			}else if(choice == AutonChoice.COMPLIANT_SCALE){
					if(scoringPositions[1] == 'L'){
    					addSequential(new AutonLLC());
    				}else if(scoringPositions[1] == 'R'){
    					addSequential(new AutonLRC());
    				}
    			}
    			break;
    		case CENTER:
    			if(scoringPositions[0] == 'L'){
    				addSequential(new AutonCL());
    			}else if(scoringPositions[0] == 'R'){
    				addSequential(new AutonCR());
    			}
    			break;
    		case RIGHT:
    			if(choice == AutonChoice.SCALE_SCALE){
    				if(scoringPositions[1] == 'L'){
    					addSequential(new AutonRL());
    				}else if(scoringPositions[1] == 'R'){
    					addSequential(new AutonRR2());
    				}
    			}else if(choice == AutonChoice.SCALE_SWITCH){
    				if(scoringPositions[1] == 'L'){
    					if(scoringPositions[0] == 'L'){
    						addSequential(new AutonRLL());
    					}
    					if(scoringPositions[0] == 'R'){
    						addSequential(new AutonRRL());
    					}
    				}
    				
    				if(scoringPositions[1] == 'R'){
    					if(scoringPositions[0] == 'L'){
    						addSequential(new AutonRLR());
    					}
    					if(scoringPositions[0] == 'R'){
    						addSequential(new AutonRRR());
    					}
    				}
    			}else if(choice == AutonChoice.COMPLIANT_SCALE){
					if(scoringPositions[1] == 'L'){
    					addSequential(new AutonRLC());
    				}else if(scoringPositions[1] == 'R'){
    					addSequential(new AutonRRC());
    				}
    			}
    			break;
    	}
    }
}
