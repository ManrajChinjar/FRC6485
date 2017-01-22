package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroscopeTurn extends Command {

    private double angularError;
    private double currentAngle;
    private double startAngle;
    private double targetAngle;
    private double turnSpeed;
    private double angularTolerance = 1.00;


    public GyroscopeTurn(double angle) {

	requires(Robot.drivetrain);
	startAngle = Robot.drivetrain.getGyroAngle();
	targetAngle = startAngle + angle;
	turnSpeed = (angle > 0) ? 0.55 : -0.55;

    }


    // Called just before this Command runs the first time
    protected void initialize() {

	Robot.drivetrain.arcadeDrive(0, turnSpeed);

    }


    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

	currentAngle = Robot.drivetrain.getGyro().getAngle();
	angularError = targetAngle - currentAngle;

	// Linear scaling once angular error is below 10 degrees.
	if (Math.abs(angularError) < 10) {
	    turnSpeed -= 0.07;
	    Robot.drivetrain.arcadeDrive(0, turnSpeed);
	}

    }


    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

	return Math.abs(angularError) < angularTolerance;

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
