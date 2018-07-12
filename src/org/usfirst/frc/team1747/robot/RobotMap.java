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
	public static final double DRIVE_LINEAR_MAX_ERROR_POWER = 0.7;
	public static final double DRIVE_ANGULAR_MAX_ERROR_POWER = 0.7;
	public static final double ELEVATOR_MAX_ERROR_POWER = 0.5;
	
	public static final boolean ENABLE_AUTON_ERROR_DETECTION = false;
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	
	public static final double DT = 0.02;
	
	//IO
	public static final int DRIVER = 0;
	public static final int OPERATOR = 1;

	//DriveSubsystem
	public static final boolean[] LEFT_MOTOR_INVERSION = {false,false,false,false};
	public static final boolean[] RIGHT_MOTOR_INVERSION = {true,true,true,true};
		
	public static final int[] LEFT_MOTOR_PORTS = {01, 02, 03, 04};
	public static final int[] RIGHT_MOTOR_PORTS = {11, 12, 13, 14};
	
	public static final double LEFT_PRACTICE_SCALING = 1266.5 * 10.38/10.25 * 9.87/10.25;
	public static final double RIGHT_PRACTICE_SCALING = 1264.8 * 10.38/10.25  * 9.87/10.25;
	public static final double LEFT_SCALING = 1266.5 * 10.1/10.27 * 10.4/10.25 * 10.1/10.25 * 10.33/10.25 * 10.4/10.25 * 10.11/10.25 * 10.35/10.25 * 10.37/10.25 * 10.34/10.25 * 9.88/10.25;
	public static final double RIGHT_SCALING = 1264.8 * 10.12/10.27 * 10.4/10.25 * 10.1/10.25 * 10.4/10.25 * 10.4/10.25 * 10.05/10.25 * 10.33/10.25 * 10.33/10.25 * 10.31/10.25 * 10.29/10.25 * 9.90/10.25;
	
	public static final int FUSE_THERMISTOR = 0;
	
	//ElevatorSubsystem
	public static final int[] ELEVATOR_MOTOR_PORTS = {21,22};
	public static final double ELEVATOR_SCALING = 188.85140562248995983935742971888; //(2210+1418)/(25+5.0/8.0-6-9.0/16);			
	public static final boolean[] ELEVATOR_MOTOR_INVERSIONS = {true, false};
	public static final int WRIST_MOTOR_PORT = 51;
	public static final int WRIST_ENCODER = 1;
	public static final double ELEVATOR_SPEED_LIMIT_POSITION = 24;
	
	//wrist offset should be between 0 and 5
	public static final double WRIST_OFFSET_PRACTICE  = 0.546 - 0.62 - 0.54-2.76;					//used to set wrist to "0"
	public static final double WRIST_ENCODER_GEAR = 60. / 84;
	public static final double WRIST_OFFSET_COMP  = -3.7 - 0.12 + 0.04 - 0.672;					//used to set wrist to "0"
	
	
	//Intake
	public static final int LEFT_INTAKE_PORT = 31;
	public static final int RIGHT_INTAKE_PORT = 32;
	public static final boolean[] INTAKE_MOTOR_INVERSIONS = {true, false};
	
	//ClimberSubsystem
	public static final int CLIMB_MOTOR_1 = 41;
	public static final int CLIMB_MOTOR_2 = 42;
	
	public static final boolean CLIMB_1_INVERT = false;
	public static final boolean CLIMB_2_INVERT = true;
	
	//Pneumatics
	public static final int CUBE_LED_SOLENOID = 0;
	public static final int CLAW_SOLENOID = 1;
	public static final int CLIMBER_SOLENOID = 2;
	
	
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
	
	public static final boolean ELEVATOR_ENCODER_INVERSION = false;
	
	
	//Limit Switches
	public static final int ELEVATOR_LIMIT_SWITCH = 6;
	public static final int INTAKE_CUBE_SENSOR = 2;
	public static final int JUMPER_DIGITAL_INPUT = 9;
	
	//Camera
	//ToDo: Find constants
	public static final double CAM_THETA = 10 * (Math.PI / 180);	//rad
	public static final double CAM_MOUNT_HEIGHT = 4;	//ft
	public static final double CAM_VD = 550;			//px
	public static final double CAM_WIDTH = 480;			//px
	public static final double CAM_HEIGHT = 640;		//px
}
