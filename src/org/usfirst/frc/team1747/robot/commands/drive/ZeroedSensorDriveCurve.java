package org.usfirst.frc.team1747.robot.commands.drive;

import org.usfirst.frc.team1747.robot.commands.reset.ZeroSensors;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ZeroedSensorDriveCurve extends CommandGroup {

    public ZeroedSensorDriveCurve(double distance, double angle) {
    	addSequential(new ZeroSensors());
    	addSequential(new DriveCurve(distance, angle));
//    	addSequential(new ZeroSensors());
    }
}
