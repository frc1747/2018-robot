package org.usfirst.frc.team1747.robot.commands.macro;

import java.awt.Robot;

import org.usfirst.frc.team1747.robot.OI;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class TeleopSwitch extends Command {

	@Override
	protected void initialize(){
		
		switch (OI.getInstance().index){
			case 0: Scheduler.getInstance().add(new SwitchStage1()); break;
			case 1: Scheduler.getInstance().add(new SwitchStage2()); break;
		}
		
		OI.getInstance().index++;
		OI.getInstance().index %= 2;
		
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
