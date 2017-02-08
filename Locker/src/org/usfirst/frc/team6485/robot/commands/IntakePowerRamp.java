package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Performs integration to ramp the speed of the intake motor to smoothen current draw.
 * 
 * @author Kyle Saburao
 */
public class IntakePowerRamp extends Command {

  private double mStartSpeed, mTargetSpeed, mPowerPerCycle, mPowerAccumulator = 0;
  private double kRampTimeSeconds = RobotMap.INTAKEPOWERRAMP_TIME_SECONDS;
  private int mRampTargetCycles = (int) Math.ceil(kRampTimeSeconds / 0.02);
  private int mRampCycles;

  /**
   * Linearizes the power ramp of the intake motor to prevent voltage spikes or other problems.
   * 
   * @param speed 1.0 is full intake, -1.0 is full reverse, 0.0 is stop.
   */
  public IntakePowerRamp(double speed) {
    requires(Robot.fuelintake);
    mTargetSpeed = speed;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mStartSpeed = Robot.fuelintake.getSpeed();
    mPowerPerCycle = (mTargetSpeed - mStartSpeed) / mRampTargetCycles;
    mPowerAccumulator = mStartSpeed;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mPowerAccumulator += mPowerPerCycle;
    if ((Math.abs(Robot.fuelintake.getSpeed() - mTargetSpeed) < 0.1)
        || mRampTargetCycles - mRampCycles <= 1) {
      Robot.fuelintake.setSpeed(mTargetSpeed);
    } else {
      Robot.fuelintake.setSpeed(mPowerAccumulator);
    }
    mRampCycles++;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return mRampCycles >= mRampTargetCycles;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    SmartDashboard.putNumber("Final mRampCycles", mRampCycles);
    SmartDashboard.putNumber("Final mRampTargetCycles", mRampTargetCycles);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {}
}
