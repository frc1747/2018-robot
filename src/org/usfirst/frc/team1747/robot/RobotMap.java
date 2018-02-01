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
	
	//IO
	public static final int DRIVER = 0;
	public static final int OPERATOR = 1;

	//DriveSubsystem
	public static final boolean[] LEFT_MOTOR_INVERSION = {false,false,false,false};
	public static final boolean[] RIGHT_MOTOR_INVERSION = {true,true,true,true};
		
	public static final int[] LEFT_MOTOR_PORTS = {01, 02, 03, 04};
	public static final int[] RIGHT_MOTOR_PORTS = {11, 12, 13, 14};
	
	public static final double LEFT_SCALING = 1266.5;
	public static final double RIGHT_SCALING = 1264.8;
	
	public static final int FUSE_THERMISTOR = 0;

	//Intake and Claw
	public static final int LEFT_INTAKE_PORT = 31;
	public static final int RIGHT_INTAKE_PORT = 32;
	
	//ElevatorSubsystem
	public static final int[] ELEVATOR_MOTOR_PORTS = {21,22};

	//ClimberSubsystem
	public static final int CLIMB_MOTOR_1 = 41;
	public static final int CLIMB_MOTOR_2 = 42;
	
	public static final boolean CLIMB_1_INVERT = false;
	public static final boolean CLIMB_2_INVERT = false;
	
	//Limit switches
	public static final int INTAKE_LIMIT_SWITCH = 7;
	public static final int ELEVATOR_LIMIT_SWITCH = 6;
	
	//Pneumatics
	public static final int GRIPPER_SOLENOIDS[] = {1, 2};
	public static final int WRIST_SOLENOIDS[] = {3, 4};
	
	//Encoders
		//Drive Subsystem
		public static final int LEFT_ENCODER_A = 0;
		public static final int LEFT_ENCODER_B = 1;
		public static final int RIGHT_ENCODER_A = 2;
		public static final int RIGHT_ENCODER_B = 3;
	
		public static final boolean LEFT_ENCODER_INVERSION = false;
		public static final boolean RIGHT_ENCODER_INVERSION = false;
	
		//Elevator subsystem
		public static final int ELEVATOR_ENCODER_A = 4;
		public static final int ELEVATOR_ENCODER_B = 5;
		
		public static final boolean ELEVATOR_A_INVERSION = false;
		public static final boolean ELEVATOR_B_INVERSION = false;
		
		//
	
		
	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
