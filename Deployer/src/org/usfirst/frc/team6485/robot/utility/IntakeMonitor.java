package org.usfirst.frc.team6485.robot.utility;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.commands.IntakeInstantStop;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeMonitor {

  private final double kStallCurrent = 8.0, kStallMaxTime = 2.0;
  
  private boolean mStallInit, mStallLock;
  private double mStallStartTime;
  private FuelIntake system;
  
  public IntakeMonitor() {
    system = Robot.FUELINTAKE;
    mStallInit = false;
    mStallLock = false;
    mStallStartTime = 0.0;
  }
  
  private boolean checkStall() {
    if (system.getCurrent() >= kStallCurrent) {
      return true;
    } else {
      return false;
    }
  }
  
  public void periodic() {
    if (checkStall() && !mStallInit) {
      mStallInit = true;
      mStallLock = false;
      mStallStartTime = Timer.getFPGATimestamp();
    }
    if (mStallInit && Timer.getFPGATimestamp() - mStallStartTime >= kStallMaxTime) {
      mStallLock = true;
    }
    if (mStallLock) {
      new IntakeInstantStop().start();
      mStallStartTime = 0.0;
      mStallInit = false;
      mStallLock = false;
    }
    SmartDashboard.putBoolean("IN-S-INIT", mStallInit);
    SmartDashboard.putBoolean("IN-S-LK", mStallLock);
  }
  
}
