package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StickDriver extends Command {

    public StickDriver() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	System.out.println("I'll try spinning. That's a good trick.");
    }

    // Called repeatedly when this Command is scheduled to run
    // TODO: Pass joystick values to here, and decide if default is tank or arcade
    protected void execute() {
    	Robot.drivetrain.arcadeDrive(
    								Robot.oi.getLeftJoyY(), 
    								Robot.oi.getLeftJoyX()
    								);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
