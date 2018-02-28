package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

public class ZeroIndex extends Command {

	@Override
	protected void initialize(){
		OI.getInstance().index = 0;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
