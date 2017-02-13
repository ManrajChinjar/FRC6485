package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.subsystems.Bridge.BRIDGE_STATE;

/**
 * @author Kyle Saburao
 */
public final class LowerBridge extends BridgeMover {

  /**
   * Lowers the bridge to allow for fuel intake or gear loading.
   */
  public LowerBridge() {
    super(BRIDGE_STATE.LOWERED);
  }

}
