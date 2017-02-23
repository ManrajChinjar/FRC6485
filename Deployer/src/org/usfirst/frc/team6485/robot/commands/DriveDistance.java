package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Kyle Saburao
 */
public class DriveDistance extends Command {

  private double mDistanceTarget, mDistanceDriven, mSpeedTarget, mSpeed, mPTurn,
      mDistanceRampingSlope;
  private final double kTurnP = RobotMap.AUTODRIVE_GYROKP, kToleranceMetres = 0.085,
      kDistanceMetresRamping = RobotMap.DRIVEDISTANCE_RAMPINGDISTANCEMETRES;
  private boolean mComplete;

  // private double mDistanceCurrent, mDistanceError, mDistanceErrorAbs;
  // private final double kDistanceMetresBeginSlow = 0.35;

  /**
   * Automatically drive forward a specific distance.
   * 
   * @param distance Metres (+ or -)
   * @param speed magnitude [0 to 0.90]
   */
  public DriveDistance(double distance, double speed) {
    requires(Robot.DRIVETRAIN);
    mDistanceTarget = distance;
    mSpeedTarget = Math.copySign(speed, mDistanceTarget);
    if (mSpeedTarget > RobotMap.DRIVETRAIN_PWMLIMIT) {
      mSpeedTarget = Math.copySign(RobotMap.DRIVETRAIN_PWMLIMIT, speed);
    }
    setTimeout(10.0); // 10 seconds max.
    setInterruptible(false);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.DRIVETRAIN.stop();

    mDistanceRampingSlope = (mSpeedTarget - Robot.DRIVETRAIN.getMotorPWM(RobotMap.FRONT_LEFT_MOTOR))
        / kDistanceMetresRamping;

    mComplete = false;
    if (Math.abs(mDistanceTarget) <= kToleranceMetres) {
      mComplete = true;
    }

    Robot.DRIVETRAIN.getEncoder().reset();
    Robot.DRIVETRAIN.getGyro().reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mDistanceDriven = Math.abs(Robot.DRIVETRAIN.getEncoder().getDistance());

    if (Math.abs(mDistanceTarget) <= (2.0 * Math.abs(kDistanceMetresRamping)) + 0.25) {
      mSpeed = mSpeedTarget;
      if (mSpeed > 0.75) {
        mSpeed = 0.75;
      }
    } else {
      if (mDistanceDriven <= Math.abs(kDistanceMetresRamping)) {
        mSpeed = mDistanceRampingSlope * mDistanceDriven;
      } else if (mDistanceDriven >= Math.abs(mDistanceTarget) - Math.abs(kDistanceMetresRamping)) {
        mSpeed =
            mDistanceRampingSlope * (Math.abs(mDistanceTarget) - Math.abs(kDistanceMetresRamping));
      } else {
        mSpeed = mSpeedTarget;
      }
    }

    if (mSpeed < RobotMap.DRIVEDISTANCE_MINIMUMALLOWABLEPWMRATE) {
      mSpeed = RobotMap.DRIVEDISTANCE_MINIMUMALLOWABLEPWMRATE;
    }

    mPTurn = Robot.DRIVETRAIN.getGyro().getAngle() * kTurnP;
    Robot.DRIVETRAIN.arcadeDrive(mSpeed, mPTurn);

    if (mDistanceTarget > 0.0) {
      mComplete = Robot.DRIVETRAIN.getEncoder().getDistance() >= mDistanceTarget;
    } else if (mDistanceTarget < 0.0) {
      mComplete = Robot.DRIVETRAIN.getEncoder().getDistance() <= mDistanceTarget;
    }

    // mDistanceCurrent = Robot.DRIVETRAIN.getEncoder().getDistance();
    // mDistanceError = mDistanceTarget - mDistanceCurrent;
    // mDistanceErrorAbs = Math.abs(mDistanceError);
    //
    // if (mDistanceErrorAbs > kDistanceMetresBeginSlow) {
    // mSpeed = mSpeedTarget;
    // } else {
    // mSpeed = 0.43;
    // }
    //
    // if (mSpeedTarget < 0.43) {
    // mSpeed = mSpeedTarget;
    // }
    //
    // mPTurn = Robot.DRIVETRAIN.getGyro().getAngle() * kTurnP;
    // Robot.DRIVETRAIN.arcadeDrive(mSpeed, mPTurn);
    // mComplete = mDistanceErrorAbs <= kToleranceMetres;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math
        .abs(mDistanceTarget - Robot.DRIVETRAIN.getEncoder().getDistance()) <= kToleranceMetres
        || mComplete || isTimedOut();
    // return mDistanceErrorAbs <= kToleranceMetres || mComplete || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVETRAIN.stop();
  }

}
