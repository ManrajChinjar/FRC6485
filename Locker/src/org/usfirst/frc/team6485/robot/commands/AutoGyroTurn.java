package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Executes a drive train point-turn to a specified amount of degrees relative to the starting
 * orientation.<br>
 * <br>
 * <b>Arguments:</b> double angle (Negative turns left, positive turns right)
 * 
 * @author Kyle Saburao
 */
public class AutoGyroTurn extends Command {

  private double mCurrentAngle, mAngleRequest, mStartAngle, mTargetAngle, mTurnSpeed, mError;
  private final double mBaseTurnSpeed = 0.60, mSlowTurnSpeed = 0.50, mAngularTolerance = 0.75;

  /**
   * 
   * @param angle double angle (Negative turns left, positive turns right)
   */
  public AutoGyroTurn(double angle) {
    mAngleRequest = angle;
    requires(Robot.DRIVETRAIN);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mStartAngle = Robot.DRIVETRAIN.getGyro().getAngle();
    mTargetAngle = mStartAngle + mAngleRequest;
    setTimeout(7);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mCurrentAngle = Robot.DRIVETRAIN.getGyro().getAngle();
    mError = mTargetAngle - mCurrentAngle;

    mTurnSpeed = 0.04 * Math.abs(mError);
    if (mTurnSpeed > mBaseTurnSpeed)
      mTurnSpeed = mBaseTurnSpeed;
    else if (mTurnSpeed < mSlowTurnSpeed)
      mTurnSpeed = mSlowTurnSpeed;

    mTurnSpeed = (mAngleRequest < 0.0) ? mTurnSpeed : -mTurnSpeed;

    Robot.DRIVETRAIN.turnOnSpot(mTurnSpeed);

    SmartDashboard.putNumber("Gyro turn start angle", mStartAngle);
    SmartDashboard.putNumber("Gyro turn target angle", mTargetAngle);
    SmartDashboard.putNumber("Gyro turn speed", mTurnSpeed);
    SmartDashboard.putNumber("Gyro turn error", mError);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Math.abs(mError) <= mAngularTolerance) || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVETRAIN.stop();
  }

}
