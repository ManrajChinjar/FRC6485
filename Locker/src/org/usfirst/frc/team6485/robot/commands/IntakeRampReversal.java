package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake.IntakeState;

/**
 * Subclass the IntakePowerRamp command, call the superclass constructor with 0.0 PWM then
 * immediately override the target speed.
 * 
 * @author Kyle Saburao
 */
public final class IntakeRampReversal extends IntakePowerRamp {

  public IntakeRampReversal() {
    super(0.0);
    if (Robot.FUELINTAKE.getDirectionalState() == IntakeState.HALT)
      mTargetSpeed = -RobotMap.FUELINTAKE_NORMAL_PWM;
    else
      mTargetSpeed = -Robot.FUELINTAKE.getSpeed();
  }

}
