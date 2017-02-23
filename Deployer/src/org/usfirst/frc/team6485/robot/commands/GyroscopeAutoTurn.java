package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Executes a drive train point-turn to a specified amount of degrees relative to the starting
 * orientation.<br>
 * <br>
 * <b>Arguments:</b> double angle (Negative turns left, positive turns right)
 * 
 * @author Kyle Saburao
 */
public class GyroscopeAutoTurn extends Command {

  private boolean mShort;
  private double mCurrentAngle, mAngleRequest, mTargetAngle, mTurnSpeed, mError;
  private final double kAngularTolerance = 0.40;

  /**
   * 
   * @param angle double angle (Negative turns left, positive turns right)
   */
  public GyroscopeAutoTurn(double angle) {
    mAngleRequest = angle;
    requires(Robot.DRIVETRAIN);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.DRIVETRAIN.getGyro().reset();
    setInterruptible(false);
    setTimeout(7.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mCurrentAngle = Robot.DRIVETRAIN.getGyro().getAngle();
    mError = mAngleRequest - mCurrentAngle;
    mTurnSpeed = Math.abs(mTurnSpeed);
    double mErrorAbs = Math.abs(mError);

    // Preetesh's formula in second expression.
    if (mErrorAbs > 40.0) {
      mTurnSpeed = 0.55;
    } else if (mErrorAbs >= 15.0 && mErrorAbs <= 40.0) {
      mTurnSpeed = 0.50;
    } else if (mErrorAbs < 15.0) {
      mTurnSpeed = 0.43;
    }

    mTurnSpeed = (mAngleRequest < 0.0) ? mTurnSpeed : -mTurnSpeed;

    Robot.DRIVETRAIN.turnOnSpot(mTurnSpeed);

    if (mAngleRequest < 0.0) {
      mShort = mCurrentAngle <= mAngleRequest;
    } else if (mAngleRequest > 0.0) {
      mShort = mCurrentAngle >= mAngleRequest;
    }
    if (Math.abs(mAngleRequest) < 0.5) {
      mShort = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(mError) <= kAngularTolerance || mShort || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVETRAIN.stop();
  }

}
