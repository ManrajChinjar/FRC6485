package org.usfirst.frc.team6485.robot.commands;

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
  }

  @Override
  protected void secondaryInitialize() {
    switch (mStartState) {
      case LOWERED:
        mReqState = BRIDGE_STATE.RAISED;
        break;
      case LOWERING:
        mReqState = BRIDGE_STATE.RAISED;
        break;
      case RAISED:
        mReqState = BRIDGE_STATE.LOWERED;
        break;
      case RAISING:
        mReqState = BRIDGE_STATE.LOWERED;
        break;
      case UNKNOWN:
        mReqState = BRIDGE_STATE.LOWERED;
        break;
      default:
        mReqState = BRIDGE_STATE.LOWERED;
        break;
    }
  }

}
