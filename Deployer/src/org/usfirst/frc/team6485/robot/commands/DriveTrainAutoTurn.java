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
public class DriveTrainAutoTurn extends Command {

  private boolean mShort, mAngularRateMode, mGoingLeft;
  private double mCurrentAngle, mAngleRequest, mTurnSpeed, mError;
  private final double kAngularTolerance = 0.40;

  private double mCurrentAngularRate;
  private final double kAngularNormalRate = 35.0, kAngularSlowRate = 25.0,
      kAngularSpeedIncrementor = 0.07;

  /**
   * 
   * @param angle double angle (Negative turns left, positive turns right)
   */
  public DriveTrainAutoTurn(double angle) {
    mAngleRequest = angle;
    requires(Robot.DRIVETRAIN);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.DRIVETRAIN.getGyro().reset();
    if (mAngularRateMode) {
      // Start with an initial turning speed
      mTurnSpeed = Math.sqrt(0.60);
    }
    setInterruptible(false);
    setTimeout(7.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mCurrentAngle = Robot.DRIVETRAIN.getGyro().getAngle();
    mCurrentAngularRate = Robot.DRIVETRAIN.getGyro().getRate();
    mError = mAngleRequest - mCurrentAngle;
    mTurnSpeed = Math.abs(mTurnSpeed);
    double mErrorAbs = Math.abs(mError);

    if (mAngularRateMode) {
      if (mErrorAbs > 20.0) {
        if (mGoingLeft) {
          if (mCurrentAngularRate < -kAngularNormalRate) {
            mTurnSpeed += kAngularSpeedIncrementor;
          } else if (mCurrentAngularRate > -kAngularNormalRate) {
            mTurnSpeed -= kAngularSpeedIncrementor;
          }
        } else {
          if (mCurrentAngularRate < kAngularNormalRate) {
            mTurnSpeed += kAngularSpeedIncrementor;
          } else if (mCurrentAngularRate > kAngularNormalRate) {
            mTurnSpeed -= kAngularSpeedIncrementor;
          }
        }
      } else {
        if (mGoingLeft) {
          if (mCurrentAngularRate < -kAngularSlowRate) {
            mTurnSpeed += kAngularSpeedIncrementor;
          } else if (mCurrentAngularRate > -kAngularSlowRate) {
            mTurnSpeed -= kAngularSpeedIncrementor;
          }
        } else {
          if (mCurrentAngularRate < kAngularSlowRate) {
            mTurnSpeed += kAngularSpeedIncrementor;
          } else if (mCurrentAngularRate > kAngularSlowRate) {
            mTurnSpeed -= kAngularSpeedIncrementor;
          }
        }
      }
    } else {
      // Preetesh's formula in second expression.
      if (mErrorAbs > 40.0) {
        mTurnSpeed = Math.sqrt(0.63);
      } else if (mErrorAbs >= 15.0 && mErrorAbs <= 40.0) {
        mTurnSpeed = Math.sqrt(0.59);
      } else if (mErrorAbs < 15.0) {
        mTurnSpeed = Math.sqrt(0.55);
      }
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
