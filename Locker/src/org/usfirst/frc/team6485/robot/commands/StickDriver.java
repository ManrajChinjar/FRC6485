package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

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
 * XBOX Controls: <i>(RIGHT BUMPER SAFETY)</i><br>
 * - Tank Drive<br>
 * - Left Bumper: Tank drive right stick overriding arcade drive left stick<br>
 * <br>
 * The Logitech joystick overrides the XBOX controller.
 * 
 * @author Kyle Saburao
 */
public class StickDriver extends Command {

  private double mLXAxisRequest, mLYAxisRequest, mXXAxisRequestL, mXYAxisRequestL, mXYAxisRequestR;
  private double mTargetAngle, mCurrentRelativeAngle;
  private final double kStraightDrivekP = RobotMap.AUTODRIVE_GYROKP;
  private boolean mGyroInitFlag;

  public StickDriver() {
    requires(Robot.DRIVETRAIN);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("I'll try spinning. That's a good trick.");
    mGyroInitFlag = false;
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
    mLXAxisRequest = -Robot.oi.getLJoyX() * Robot.oi.getLSliderScale();
    mLYAxisRequest = -Robot.oi.getLJoyY() * Robot.oi.getLSliderScale();

    mXXAxisRequestL = -Robot.oi.getXBOXLeftJoyX();
    mXYAxisRequestL = -Robot.oi.getXBOXLeftJoyY();
    mXYAxisRequestR = -Robot.oi.getXBOXRightJoyY();

    if (!Robot.oi.getLButtonPressed(2)) {
      mGyroInitFlag = false;
    }

    if (Robot.oi.getLMainTrigger()) {
      logitechControl();
    } else if (Robot.oi.getXBOXSafety()) {
      xboxControl();
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
