package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class TeleopScaleBackward extends Command {
	int index = 0;

	@Override
	protected void initialize(){
		
		switch (index){
			case 0: Scheduler.getInstance().add(new ScaleBackwardStage1()); break;
			case 1: Scheduler.getInstance().add(new ScaleBackwardStage2()); break;
		}
		index++;
		index %= 2;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
