package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Executes a drive train point-turn to a specified amount of degrees relative
 * to the starting orientation.<br>
 * <br>
 * <b>Arguments:</b> double angle (Negative turns left, positive turns right)
 * <br>
 * <br>
 * <i>Kyle Saburao 2017</i>
 */
public class GyroscopeTurn extends Command {

    private double mCurrentAngle, mAngleRequest, mStartAngle, mTargetAngle, mTurnSpeed, mMultiplier, mError;
    private final double mBaseTurnSpeed = 0.55, mAngularTolerance = 0.75;

    public GyroscopeTurn(double angle) {
	mAngleRequest = angle;
	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	mStartAngle = Robot.drivetrain.getGyro().getAngle();
	mTargetAngle = mStartAngle + mAngleRequest;
	setTimeout(7);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	mCurrentAngle = Robot.drivetrain.getGyro().getAngle();
	mError = mTargetAngle - mCurrentAngle;
	mMultiplier = 1.00;

	mTurnSpeed = ((mAngleRequest < 0.00) ? mBaseTurnSpeed : -mBaseTurnSpeed) * mMultiplier;
	Robot.drivetrain.arcadeDrive(0, mTurnSpeed);

	SmartDashboard.putNumber("Gyro turn start angle", mStartAngle);
	SmartDashboard.putNumber("Gyro turn target angle", mTargetAngle);
	SmartDashboard.putNumber("Gyro turn error", mError);
	SmartDashboard.putNumber("Gyro turn power multiplier", mMultiplier);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return (Math.abs(mError) <= mAngularTolerance) || isTimedOut();
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
