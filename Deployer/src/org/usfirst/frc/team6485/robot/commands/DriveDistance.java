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
   * @param speed magnitude [0 to 0.95]
   */
  public DriveDistance(double distance, double speed) {
    requires(Robot.DRIVETRAIN);
    mDistanceTarget = distance;
    // Square root the target speed because arcade drive squares the inputs by default.
    mSpeedTarget = Math.copySign(Math.sqrt(Math.abs(speed)), distance);
    if (Math.abs(mSpeedTarget) > Math.sqrt(Math.abs(RobotMap.DRIVETRAIN_PWMLIMIT))) {
      mSpeedTarget = Math.copySign(Math.sqrt(Math.abs(RobotMap.DRIVETRAIN_PWMLIMIT)), distance);
    }
    setTimeout(10.0); // 10 seconds max.
    setInterruptible(false);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mComplete = false;
    Robot.DRIVETRAIN.stop();

    mDistanceRampingSlope = (mSpeedTarget - Robot.DRIVETRAIN.getMotorPWM(RobotMap.FRONT_LEFT_MOTOR))
        / Math.abs(kDistanceMetresRamping);

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
    // This is a scalar amount
    mDistanceDriven = Math.abs(Robot.DRIVETRAIN.getEncoder().getDistance());

    if (Math.abs(mDistanceTarget) <= (2.0 * Math.abs(kDistanceMetresRamping)) + 0.25) {
      mSpeed = mSpeedTarget;
      if (mSpeed > Math.sqrt(0.75)) {
        mSpeed = Math.sqrt(0.75);
      }
    } else {
      if (mDistanceDriven <= Math.abs(kDistanceMetresRamping)) {
        mSpeed = mDistanceRampingSlope * mDistanceDriven;
      } else if (mDistanceDriven >= Math.abs(mDistanceTarget) - Math.abs(kDistanceMetresRamping)) {
        mSpeed = mDistanceRampingSlope * (Math.abs(mDistanceTarget) - mDistanceDriven);
      } else {
        mSpeed = mSpeedTarget;
      }
    }

    // Minimum speed limiter
    if (Math.abs(mSpeed) < Math.sqrt(RobotMap.DRIVEDISTANCE_MINIMUMALLOWABLEPWMMAGNITUDE)) {
      mSpeed = Math.copySign(Math.sqrt(RobotMap.DRIVEDISTANCE_MINIMUMALLOWABLEPWMMAGNITUDE),
          mSpeedTarget);
    }

    // Leave the turning to default to squared inputs.
    mPTurn = Robot.DRIVETRAIN.getGyro().getAngle() * kTurnP;

    // Direct override
    if (mDistanceTarget > 0.0) {
      mComplete = Robot.DRIVETRAIN.getEncoder().getDistance() >= mDistanceTarget;
    } else if (mDistanceTarget < 0.0) {
      mComplete = Robot.DRIVETRAIN.getEncoder().getDistance() <= mDistanceTarget;
    }
    if (mComplete) {
      mSpeed = 0.0;
    }

    Robot.DRIVETRAIN.arcadeDrive(mSpeed, mPTurn);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return mComplete || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVETRAIN.stop();
  }

}
