package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Computes the derivative of PWM speed per millisecond to ramp the speed of the intake motor to
 * smoothen current draw and to ease on the chain.
 * 
 * @author Kyle Saburao
 */
public class IntakePowerRamp extends Command {

  protected double mStartSpeed, mTargetSpeed, mSetSpeed, mSlopeMS, mStartTime, mCurrentTime,
      mRunTimeMS, mAcceptableMarginMS;
  protected final double kRampTimeSeconds = RobotMap.INTAKEPOWERRAMP_TIMESECONDS;
  protected FuelIntake mFuelIntake;

  /**
   * Linearizes the power ramp of the intake motor to prevent voltage spikes or other problems.
   * 
   * @param speed -1.0 is full intake, 1.0 is full reverse, 0.0 is stop.
   * @author Kyle Saburao
   */
  public IntakePowerRamp(double speed) {
    requires(Robot.FUELINTAKE);
    mFuelIntake = Robot.FUELINTAKE;
    mTargetSpeed = speed;
    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mSetSpeed = 0.0;
    mRunTimeMS = 0.0;
    mStartTime = Timer.getFPGATimestamp();
    mStartSpeed = mFuelIntake.getSpeed();
    mAcceptableMarginMS = 30.0;

    // PWM units per millisecond
    secondaryInitialize();
    mSlopeMS = (mTargetSpeed - mStartSpeed) / (kRampTimeSeconds * 1000.0);
  }

  /**
   * Override if special conditions or arguments need to be created in subclasses.
   */
  protected void secondaryInitialize() {}

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mCurrentTime = Timer.getFPGATimestamp();
    mRunTimeMS = (mCurrentTime * 1000.0) - (mStartTime * 1000.0);
    mSetSpeed = mSlopeMS * mRunTimeMS;
    if (mTargetSpeed > mStartSpeed) {
      mSetSpeed = (mSetSpeed > mTargetSpeed) ? mTargetSpeed : mSetSpeed;
    } else if (mTargetSpeed < mStartSpeed) {
      mSetSpeed = (mSetSpeed < mTargetSpeed) ? mTargetSpeed : mSetSpeed;
    }

    mFuelIntake.set(mSetSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return mFuelIntake.getSpeed() == mTargetSpeed;
  }

}
