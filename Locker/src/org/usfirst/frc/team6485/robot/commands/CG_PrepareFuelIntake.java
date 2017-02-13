package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class CG_PrepareFuelIntake extends CommandGroup {

  /**
   * Lower the bridge and start the intake motor.
   */
  public CG_PrepareFuelIntake() {
    addParallel(new LowerBridge());
    addParallel(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMAL_PWM));
  }

}
