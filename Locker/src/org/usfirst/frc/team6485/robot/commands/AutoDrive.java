package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Executes drive train movement either back or forth for a specified time period.<br>
 * <br>
 * <b>Arguments:</b> double speed, double time<br>
 * <br>
 * TODO Allow metre distance via encoder units.
 * 
 * @author Kyle Saburao
 */
public class AutoDrive extends Command {
  // TODO Also allow metre distance via future averaged encoder units

  private final double kP = RobotMap.AUTODRIVE_GYROKP;
  private double mCurrentAngle;
  private double mStartTime, mCurrentRunTime, mRampPeriod, mTimeWindow;
  private double mStartSpeed, mTargetSpeed, mSpeedSlope, mCPT;
  // private double mDistanceTarget, mDistanceError;
  private int mMotorIndex = 0;
  private boolean mAccelerated;

  private Gyro gyroscope = Robot.DRIVETRAIN.getGyro();
  // private Encoder encoder = Robot.DRIVETRAIN.getEncoder();

  /**
   * Drives forward and uses the gyroscope to maintain direction.
   * 
   * @param speed The speed to drive
   * @param time The time to drive
   */
  public AutoDrive(double speed, double time) {
    requires(Robot.DRIVETRAIN);
    if (time < 0) {
      time = 0.0;
    }
    setTimeout(time + 1.0); // Will kill the command one second after its
    // allotted window.

    mTargetSpeed = speed;
    mTimeWindow = time;
    setInterruptible(false);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out
        .println(String.format("Driving at %.2f for %.2f seconds.", mTargetSpeed, mTimeWindow));

    mRampPeriod = RobotMap.AUTODRIVE_RAMPPERIODSECONDS;
    mStartSpeed = Robot.DRIVETRAIN.getMotorPWM(mMotorIndex); // Change index if needed.
    mSpeedSlope = (mTargetSpeed - mStartSpeed) / mRampPeriod;
    mStartTime = Timer.getFPGATimestamp();
    mAccelerated = false;

    gyroscope.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mCurrentRunTime = Timer.getFPGATimestamp() - mStartTime;
    mCurrentAngle = gyroscope.getAngle();
    // Gyroscope measurement increases to the right, but the drive train
    // turn rate is negative the same direction.
    mCPT = mCurrentAngle * kP;
    if (mTargetSpeed < 0) {
      mCPT *= -1;
    }

    if (!mAccelerated) {
      double accelerationvelocitycompute = mSpeedSlope * mCurrentRunTime;
      Robot.DRIVETRAIN.arcadeDrive(
          (accelerationvelocitycompute > mTargetSpeed) ? mTargetSpeed : accelerationvelocitycompute,
          mCPT);
      if (Robot.DRIVETRAIN.getMotorPWM(mMotorIndex) == mTargetSpeed) {
        mAccelerated = true;
      }
    } else if (mTimeWindow - mCurrentRunTime <= mRampPeriod) {
      Robot.DRIVETRAIN.arcadeDrive(mSpeedSlope * (mTimeWindow - mCurrentRunTime), mCPT);
    } else
      Robot.DRIVETRAIN.arcadeDrive(mTargetSpeed, mCPT);

    SmartDashboard.putNumber("Gyroscope cPT", mCPT);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Timer.getFPGATimestamp() - mStartTime >= mTimeWindow) || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVETRAIN.stop();
    System.out.println(String.format("AutoDrive complete: %.2f PWM for %.3f seconds.", mTargetSpeed,
        mCurrentRunTime));
  }

}
