package org.usfirst.frc.team6485.robot.commandgroups;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.commands.IntakePowerRamp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class CG_PrepareFuelIntake extends CommandGroup {

  /**
   * Lower the bridge and start the intake motor.
   */
  public CG_PrepareFuelIntake() {
    // addParallel(new BridgeControl(BRIDGE_STATE.LOWERED));
    addParallel(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMALPWM));
  }

}
