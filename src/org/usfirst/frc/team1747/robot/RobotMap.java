/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1747.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	
	public static final int DRIVER = 0;
	public static final int OPERATOR = 1;

	public static final boolean[] LEFT_MOTOR_INVERSION = {false,false,false,false};
	public static final boolean[]	RIGHT_MOTOR_INVERSION = {true,true,true,true};
		
	public static final int[] LEFT_MOTOR_PORTS = {11,12,13,14};
	public static final int[] RIGHT_MOTOR_PORTS = {21,22,23,24};
	
	public static final int LEFT_SCALING = 7782;
	public static final int RIGHT_SCALING = 7671;
	
	public static final int FUSE_THERMISTOR = 3;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
