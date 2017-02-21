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
  private final double kAngularTolerance = 0.50;
  private boolean mShort;

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
    setInterruptible(false);
    setTimeout(7.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mCurrentAngle = Robot.DRIVETRAIN.getGyro().getAngle();
    mError = mTargetAngle - mCurrentAngle;
    mTurnSpeed = Math.abs(mTurnSpeed);

    // Preetesh's formula in second expression.
    if (Math.abs(mError) > 30.0) {
      mTurnSpeed = 0.60;
    } else if (Math.abs(mError) >= 10.0 && Math.abs(mError) <= 30.0) {
      mTurnSpeed = (0.1 / 20.0) * (Math.abs(mError) - 10.0) + 0.5;
    } else if (Math.abs(mError) < 10) {
      mTurnSpeed = 0.50;
    }

    mTurnSpeed = (mAngleRequest < 0.0) ? mTurnSpeed : -mTurnSpeed;

    Robot.DRIVETRAIN.turnOnSpot(mTurnSpeed);

    SmartDashboard.putNumber("Gyro turn start angle", mStartAngle);
    SmartDashboard.putNumber("Gyro turn target angle", mTargetAngle);
    SmartDashboard.putNumber("Gyro turn speed", mTurnSpeed);
    SmartDashboard.putNumber("Gyro turn error", mError);
    
    mShort = (Math.abs(mError) <= kAngularTolerance);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Math.abs(mError) <= kAngularTolerance) || mShort || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVETRAIN.stop();
  }

}
