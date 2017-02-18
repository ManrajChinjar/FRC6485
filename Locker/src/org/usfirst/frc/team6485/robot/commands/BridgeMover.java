package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap.BRIDGE_STATE;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Kyle Saburao
 */
public abstract class BridgeMover extends Command {

  protected BRIDGE_STATE mReqState, mStartState;
  protected boolean mShorted;

  /**
   * Moves the bridge into position.<br>
   * <br>
   * <b>DO NOT DIRECTLY CREATE THIS COMMAND</b>
   * 
   * @param state The requested bridge state.<br>
   *        Pass BRIDGE _STATE "UNKNOWN" to toggle, "RAISED" to raise and "LOWERED" to lower.
   */
  public BridgeMover(BRIDGE_STATE state) {
    requires(Robot.BRIDGE);
    mReqState = state;
    this.setInterruptible(true);
    mShorted = false;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mStartState = Robot.BRIDGE.getState();

    secondaryInitialize();

    if (mStartState == mReqState) {
      mShorted = true;
    }

    switch (mReqState) {
      case RAISED:
        Robot.BRIDGE.setRaise();
        break;
      case LOWERED:
        Robot.BRIDGE.setLower();
        break;
      default:
        break;
    }
  }

  /**
   * Override if special conditions or arguments need to be created in subclasses.
   */
  protected void secondaryInitialize() {}

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.BRIDGE.updateState();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Robot.BRIDGE.getState() == Robot.BRIDGE.getRequiredState()) || mShorted;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.BRIDGE.stop();
    Robot.BRIDGE.updateState();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

}
