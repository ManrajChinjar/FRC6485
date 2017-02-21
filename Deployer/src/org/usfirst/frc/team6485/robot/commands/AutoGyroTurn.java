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
  private final double kAngularTolerance = 0.50, kTurnSpeedIncrementor = 0.001,
      kMaxAngularRateSeconds = RobotMap.AUTOGYROTURN_BASEDEGREESPERSECOND,
      kMinAngularRateSeconds = RobotMap.AUTOGYROTURN_SLOWDEGREESPERSECOND;

  private final double mkP = 1.0 / 30.0;

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
    mTurnSpeed = Math.abs(mTurnSpeed);

    // Preetesh's formula in second expression.
    if (Math.abs(mError) > 30.0) {
      mTurnSpeed = 0.60;
    } else if (Math.abs(mError) >= 10.0 && Math.abs(mError) <= 30.0) {
      mTurnSpeed = (0.1/20.0) * (Math.abs(mError) - 10.0) + 0.5;
    } else if (Math.abs(mError) < 10) {
      mTurnSpeed = 0.50;
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
