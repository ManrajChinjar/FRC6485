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
  private final double kTurnP = RobotMap.AUTODRIVE_GYROKP, kDistanceMetresBeginSlow = 0.30;

  /**
   * Automatically drive forward a specific distance.
   * 
   * @param distance Metres (+ or -)
   * @param speed magnitude [0 to 0.90]
   */
  public DriveDistance(double distance, double speed) {
    requires(Robot.DRIVETRAIN);
    mDistanceTarget = distance;
    mTargetSpeed = Math.abs(speed);
    mTargetSpeed = Math.copySign(mTargetSpeed, mDistanceTarget);
    setTimeout(10.0); // 10 seconds max.
    setInterruptible(false);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.DRIVETRAIN.getEncoder().reset();
    Robot.DRIVETRAIN.getGyro().reset();
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
      mSpeed = 0.40;
    }

    if (mTargetSpeed < 0.40) {
      mSpeed = mTargetSpeed;
    }

    mPTurn = Robot.DRIVETRAIN.getGyro().getAngle() * kTurnP;
    Robot.DRIVETRAIN.arcadeDrive(mSpeed, mPTurn);
    SmartDashboard.putNumber("mDistanceErrorAbs", mDistanceErrorAbs);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return mDistanceErrorAbs <= 0.085 || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVETRAIN.stop();
  }

}
