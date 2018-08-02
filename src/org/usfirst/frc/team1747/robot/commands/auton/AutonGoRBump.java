package org.usfirst.frc.team1747.robot.commands.auton;

import org.usfirst.frc.team1747.robot.commands.drive.DriveProfile;
import org.usfirst.frc.team1747.robot.commands.wrist.WristVertical;

import edu.wpi.first.wpilibj.command.CommandGroup;
import lib.frc1747.commands.MakeParallel;

public class AutonGoRBump extends CommandGroup {
	
	public AutonGoRBump(){
		addSequential(new MakeParallel(
				new WristVertical(),
				new DriveProfile("/home/lvuser/RB.csv")));
	}
}
