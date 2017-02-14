package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.Bridge.BRIDGE_STATE;

/**
 * @author Kyle Saburao
 */
public class ToggleBridge extends BridgeMover {

  /**
   * Toggles the bridge state. Will default to lowering it no limit switches are pressed.
   */
  public ToggleBridge() {
    super(BRIDGE_STATE.UNKNOWN);
    requires(Robot.BRIDGE);
  }

}
