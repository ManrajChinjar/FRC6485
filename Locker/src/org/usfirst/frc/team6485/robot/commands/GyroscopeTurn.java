package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Executes a drive train point-turn to a specified amount of degrees relative to the starting orientation.<br>
 * <br>
 * <b>Arguments:</b> double angle (Negative turns left, positive turns right)
 * <br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class GyroscopeTurn extends Command {

    private double currentAngle, 
            angleRequest, 
            startAngle, 
            targetAngle, 
            turnSpeed, 
            multiplier, 
            error,
            abserror;
    private final double baseTurnSpeed = 0.55,
	    angularTolerance = 0.75;


    public GyroscopeTurn(double angle) {

	angleRequest = angle;
	requires(Robot.drivetrain);

    }


    // Called just before this Command runs the first time
    protected void initialize() {

	startAngle = Robot.drivetrain.getGyroAngle();
	targetAngle = startAngle + angleRequest;
	setTimeout(7);

    }


    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

	currentAngle = Robot.drivetrain.getGyroAngle();
	error = targetAngle - currentAngle;
	abserror = Math.abs(error);

	// -(1/9)x^2+100, x = 30 - x

	if (abserror >= 30.00) multiplier = 1.00;
	else if (abserror < 30.00 && abserror >= 14.972) 
	    multiplier = (-(1.00/9.00) * Math.pow(30.00 - abserror, 2.00) + 100.00) / 100.00;
	else if (abserror < 14.972) multiplier = 0.75;

	turnSpeed = ((angleRequest < 0.00) ? baseTurnSpeed : -baseTurnSpeed) * multiplier;
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

	end();

    }
}
