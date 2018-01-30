package org.usfirst.frc.team1747.robot.commands;

import org.usfirst.frc.team1747.robot.OI;
import org.usfirst.frc.team1747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1747.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import lib.frc1747.controller.Logitech;

public class ElevatorDown extends Command {

	public ElevatorSubsystem elevator;
	
	public ElevatorDown() {
		// TODO Auto-generated constructor stub
		elevator = ElevatorSubsystem.getInstance();
		requires(elevator);
		setInterruptible(true);
	}

	protected void initialize() {
		elevator.setPower(-.35);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return elevator.getLowerSwitch();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		elevator.setPower(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}

}
