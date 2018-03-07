package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class TeleopScaleForward extends Command {

	@Override
	protected void initialize(){
		
		switch (OI.getInstance().index){
			case 0: Scheduler.getInstance().add(new ScaleForwardStage1()); break;
			case 1: Scheduler.getInstance().add(new ScaleForwardStage2()); break;
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
