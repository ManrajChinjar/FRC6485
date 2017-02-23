package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Kyle Saburao
 */
public class DriveDistance extends Command {

  private double mDistanceTarget, mDistanceCurrent, mDistanceError, mTargetSpeed, mSpeed, mPTurn,
      mDistanceErrorAbs;
  private final double kTurnP = RobotMap.AUTODRIVE_GYROKP, kDistanceMetresBeginSlow = 0.35,
      kToleranceMetres = 0.085;
  private boolean mShort;

  /**
   * Automatically drive forward a specific distance.
   * 
   * @param distance Metres (+ or -)
   * @param speed magnitude [0 to 0.90]
   */
  public DriveDistance(double distance, double speed) {
    requires(Robot.DRIVETRAIN);
    mDistanceTarget = distance;
    mTargetSpeed = Math.copySign(speed, mDistanceTarget);
    setTimeout(10.0); // 10 seconds max.
    setInterruptible(false);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.DRIVETRAIN.getEncoder().reset();
    Robot.DRIVETRAIN.getGyro().reset();
    mShort = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mDistanceCurrent = Robot.DRIVETRAIN.getEncoder().getDistance();
    mDistanceError = mDistanceTarget - mDistanceCurrent;
    mDistanceErrorAbs = Math.abs(mDistanceError);

    if (mDistanceErrorAbs > kDistanceMetresBeginSlow) {
      mSpeed = mTargetSpeed;
    } else {
      mSpeed = 0.43;
    }

    if (mTargetSpeed < 0.43) {
      mSpeed = mTargetSpeed;
    }

    mPTurn = Robot.DRIVETRAIN.getGyro().getAngle() * kTurnP;
    Robot.DRIVETRAIN.arcadeDrive(mSpeed, mPTurn);
    SmartDashboard.putNumber("mDistanceErrorAbs", mDistanceErrorAbs);
    mShort = mDistanceErrorAbs <= kToleranceMetres;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return mDistanceErrorAbs <= kToleranceMetres || mShort || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVETRAIN.stop();
  }

}
