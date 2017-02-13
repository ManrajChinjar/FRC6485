package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.Bridge.BRIDGE_STATE;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ToggleBridge extends InstantCommand {

  private Command mBridgeCommand;
  private BRIDGE_STATE mStartState;

  /**
   * Toggles the bridge state. Will default to lowering it no limit switches are pressed.
   */
  public ToggleBridge() {
    super();
    requires(Robot.BRIDGE);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    mStartState = Robot.BRIDGE.getState();

    if (mStartState == BRIDGE_STATE.UNKNOWN)
      mBridgeCommand = new LowerBridge();
    else if (mStartState == BRIDGE_STATE.LOWERED)
      mBridgeCommand = new RaiseBridge();
    else if (mStartState == BRIDGE_STATE.RAISED)
      mBridgeCommand = new LowerBridge();
    else
      return;

    mBridgeCommand.start();
  }

}
