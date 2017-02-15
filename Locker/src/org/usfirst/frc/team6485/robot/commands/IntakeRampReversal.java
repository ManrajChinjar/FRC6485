package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.RobotMap.IntakeState;

/**
 * Subclass the IntakePowerRamp command, call the superclass constructor with 0.0 PWM and then
 * immediately override the target speed.
 * 
 * @author Kyle Saburao
 */
public final class IntakeRampReversal extends IntakePowerRamp {

  public IntakeRampReversal() {
    super(0.0);
  }

  /**
   * Override this method so that the original 0.0 PWM rate can be replaced with the intended
   * request.
   */
  @Override
  protected void secondaryInitialize() {
    if (Robot.FUELINTAKE.getDirectionalState() == IntakeState.HALT)
      mTargetSpeed = -RobotMap.FUELINTAKE_NORMAL_PWM;
    else
      mTargetSpeed = -Robot.FUELINTAKE.getSpeed();
  }


}
