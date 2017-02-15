package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.RobotMap.BRIDGE_STATE;

/**
 * @author Kyle Saburao
 */
public final class RaiseBridge extends BridgeMover {

  /**
   * Raises the bridge to allow for fuel dumping.
   */
  public RaiseBridge() {
    super(BRIDGE_STATE.RAISED);
  }

}
