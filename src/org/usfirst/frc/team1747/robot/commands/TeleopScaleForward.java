package org.usfirst.frc.team1747.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class TeleopScaleForward extends Command {
	int index = 0;

	@Override
	protected void initialize(){
		
		switch (index){
			case 0: Scheduler.getInstance().add(new ScaleForwardStage1()); break;
			case 1: Scheduler.getInstance().add(new ScaleForwardStage2()); break;
			case 2: Scheduler.getInstance().add(new ScaleForwardStage3()); break;
		}
		index++;
		index %= 3;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
