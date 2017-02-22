package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.RobotMap.RUNNING_MODE;

import edu.wpi.first.wpilibj.command.Command;

/**
 * <b>Standard Teleoperator controls</b><br>
 * <br>
 * Logitech Controls: <i>(MAIN TRIGGER SAFETY)</i><br>
 * - Arcade Drive<br>
 * - Button 2: Forwards and back only<br>
 * - Button 3: Rotate on current position<br>
 * - Thrust Lever: Power multiplier (Full at the top)<br>
 * <br>
 * <b>The offloader controls may be moved into their own default command if the watchdog isn't
 * affected by poor multithreading.</b>
 * 
 * @author Kyle Saburao
 */
public class DriveTrainDriver extends Command {

  private double mLXAxisRequest, mLYAxisRequest, mXXAxisRequestL, mXYAxisRequestL, mXYAxisRequestR;
  private double mTargetAngle, mCurrentRelativeAngle;
  private final double kStraightDrivekP = RobotMap.AUTODRIVE_GYROKP;
  private boolean mGyroInitFlag;

  public DriveTrainDriver() {
    requires(Robot.DRIVETRAIN);
    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("I'll try spinning. That's a good trick.");
    mGyroInitFlag = false;
    Robot.DRIVETRAIN.stop();
    Robot.OFFLOADER.stop();
  }

  private void logitechControl() {
    if (Robot.oi.getLButtonPressed(2)) {
      if (!mGyroInitFlag) {
        mGyroInitFlag = true;
        mTargetAngle = Robot.DRIVETRAIN.getGyro().getAngle();
      }
      mCurrentRelativeAngle = Robot.DRIVETRAIN.getGyro().getAngle() - mTargetAngle;
      Robot.DRIVETRAIN.arcadeDrive(mLYAxisRequest, mCurrentRelativeAngle * kStraightDrivekP);
    } else if (Robot.oi.getLButtonPressed(3)) {
      Robot.DRIVETRAIN.turnOnSpot(mLXAxisRequest);
    } else {
      Robot.DRIVETRAIN.arcadeDrive(mLYAxisRequest, mLXAxisRequest);
    }
  }

  /**
   * No longer used as the Xbox controller is instead used for commands in other subsystems.
   */
  @Deprecated
  private void xboxControl() {
    if (!Robot.oi.getXBOXButtonPressed(5)) {
      Robot.DRIVETRAIN.tankDrive(mXYAxisRequestL, mXYAxisRequestR);
    } else {
      if (Math.abs(mXYAxisRequestR) > 0.1) {
        Robot.DRIVETRAIN.tankDrive(mXYAxisRequestR, mXYAxisRequestR);
      } else if (Math.abs(mXYAxisRequestL) > 0.1 || Math.abs(mXXAxisRequestL) > 0.1) {
        Robot.DRIVETRAIN.arcadeDrive(mXYAxisRequestL, mXXAxisRequestL);
      } else {
        Robot.DRIVETRAIN.stop();
      }
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    /*
     * X-axis of the logitech is multiplied by 0.80 because the arcade drive algorithm is
     * exponential to the power of 2, so the increased slope at the end is truncated leading to
     * easier turning and handling.
     */
    mLXAxisRequest = -Robot.oi.getLJoyX() * Robot.oi.getLSliderScale() * 0.80;
    // The Y-axis is multiplied by -1.0 because the joystick follows standard flight conventions.
    mLYAxisRequest = -Robot.oi.getLJoyY() * Robot.oi.getLSliderScale();

    // If the thumb button on the logitech controller
    if (!Robot.oi.getLButtonPressed(2)) {
      mGyroInitFlag = false;
    }
    if (Robot.oi.getLogitechTrigger() && Robot.robotMode == RUNNING_MODE.TELEOP) {
      logitechControl();
    } else {
      Robot.DRIVETRAIN.stop();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVETRAIN.stop();
    mGyroInitFlag = false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

}
