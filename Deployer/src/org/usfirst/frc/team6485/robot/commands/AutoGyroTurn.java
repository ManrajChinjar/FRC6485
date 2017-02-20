package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

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

  private double mCurrentAngle, mAngleRequest, mStartAngle, mTargetAngle, mTurnSpeed, mError,
      mAbsError, mAngularRateAccumulator, mGyroRate;
  private final double kAngularTolerance = 0.75, kTurnSpeedIncrementor = 0.001,
      kMaxAngularRateSeconds = RobotMap.AUTOGYROTURN_BASEDEGREESPERSECOND,
      kMinAngularRateSeconds = RobotMap.AUTOGYROTURN_SLOWDEGREESPERSECOND;

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
    mTurnSpeed = 0.50; // Slow initial speed to get going
    mAngularRateAccumulator = kMaxAngularRateSeconds;
    setInterruptible(false);
    setTimeout(7.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mCurrentAngle = Robot.DRIVETRAIN.getGyro().getAngle();
    mGyroRate = Robot.DRIVETRAIN.getGyro().getRate();
    mError = mTargetAngle - mCurrentAngle;
    mAbsError = Math.abs(mError);
    mTurnSpeed = Math.abs(mTurnSpeed);

    if (mAbsError <= kMaxAngularRateSeconds) {
      if ((kMaxAngularRateSeconds
          * (mAbsError / kMaxAngularRateSeconds)) > kMinAngularRateSeconds) {
        mAngularRateAccumulator = kMaxAngularRateSeconds * (mAbsError / kMaxAngularRateSeconds);
      } else {
        mAngularRateAccumulator = kMinAngularRateSeconds;
      }
    } else {
      mAngularRateAccumulator = kMaxAngularRateSeconds;
    }

    if (Math.abs(mGyroRate) < mAngularRateAccumulator) {
      mTurnSpeed += kTurnSpeedIncrementor;
    } else if (Math.abs(mGyroRate) > mAngularRateAccumulator) {
      mTurnSpeed -= kTurnSpeedIncrementor;
    }

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
    return (Math.abs(mError) <= kAngularTolerance) || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVETRAIN.stop();
  }

}
