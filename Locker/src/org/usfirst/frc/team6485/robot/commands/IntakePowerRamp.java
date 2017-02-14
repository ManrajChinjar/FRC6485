package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Computes the derivative of PWM speed per millisecond to ramp the speed of the intake motor to
 * smoothen current draw and to ease on the chain.
 * 
 * @author Kyle Saburao
 */
public class IntakePowerRamp extends Command {

  protected double mStartSpeed, mTargetSpeed, mSetSpeed, mSlopeMS;
  protected double mStartTime, mCurrentTime, mRunTimeMS, mAcceptableMarginMS;
  protected double kRampTimeSeconds = RobotMap.INTAKEPOWERRAMP_TIME_SECONDS;
  protected boolean mHalt = false;

  /**
   * Linearizes the power ramp of the intake motor to prevent voltage spikes or other problems.
   * 
   * @param speed -1.0 is full intake, 1.0 is full reverse, 0.0 is stop.
   * @author Kyle Saburao
   */
  public IntakePowerRamp(double speed) {
    requires(Robot.FUELINTAKE);
    mTargetSpeed = speed;
    this.setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mSetSpeed = 0.0;
    mRunTimeMS = 0.0;
    mStartTime = Timer.getFPGATimestamp();
    mStartSpeed = Robot.FUELINTAKE.getSpeed();
    mAcceptableMarginMS = 30.0;

    // PWM units per millisecond
    secondaryInitialize();
    mSlopeMS = (mTargetSpeed - mStartSpeed) / (kRampTimeSeconds * 1000.0);
    mHalt = false;
  }

  /**
   * Override if special conditions or arguments need to be created in subclasses.
   */
  protected void secondaryInitialize() {}

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!mHalt) {
      mCurrentTime = Timer.getFPGATimestamp();
      mRunTimeMS = (mCurrentTime * 1000.0) - (mStartTime * 1000.0);
      mSetSpeed = mSlopeMS * mRunTimeMS;

      // If 30 milliseconds is left, set the Target speed.
      if ((kRampTimeSeconds * 1000.0) - mRunTimeMS < mAcceptableMarginMS)
        Robot.FUELINTAKE.set(mTargetSpeed);
      else
        Robot.FUELINTAKE.set(mSetSpeed);

      SmartDashboard.putNumber("Intake Power Ramp Set Speed", mSetSpeed);
      SmartDashboard.putNumber("Intake Power Ramp Millisecond Runtime", mRunTimeMS);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.FUELINTAKE.getSpeed() == mTargetSpeed;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    mHalt = true;
  }

}
