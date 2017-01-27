package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Executes a drive train point-turn to a specified amount of degrees relative to the starting orientation.<br>
 * <br>
 * <b>Arguments:</b> double angle (Negative turns left, positive turns right)
 */
public class GyroscopeTurn extends Command {

    private double currentAngle;
    private double angleRequest;
    private double startAngle;
    private double targetAngle;
    private double turnSpeed;
    private double baseTurnSpeed = 0.55;
    private double multiplier;

    private double angularTolerance = 0.50;

    private double kScale = 0.0333;


    public double error;


    public GyroscopeTurn(double angle) {

	angleRequest = angle;
	requires(Robot.drivetrain);

    }


    // Called just before this Command runs the first time
    protected void initialize() {

	startAngle = Robot.drivetrain.getGyroAngle();
	targetAngle = startAngle + angleRequest;
	turnSpeed = (angleRequest < 0) ? baseTurnSpeed : -baseTurnSpeed;

	setTimeout(5);

    }


    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	
	currentAngle = Robot.drivetrain.getGyroAngle();
	error = targetAngle - currentAngle;
	if (error * kScale > 1) {
	    multiplier = 1;
	}
	else if (error * kScale < .70) {
	    multiplier = 0.70;
	}
	else {
	    multiplier = error * kScale;
	}
	turnSpeed *= multiplier;
	Robot.drivetrain.arcadeDrive(0, turnSpeed);
	
	SmartDashboard.putNumber("Gyro turn start angle", startAngle);
	SmartDashboard.putNumber("Gyro turn target angle", targetAngle);
	SmartDashboard.putNumber("Gyro turn error", error);
	SmartDashboard.putNumber("Gyro turn power multiplier", multiplier);

    }


    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

	return (Math.abs(error) <= angularTolerance) 
		|| isTimedOut();

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
