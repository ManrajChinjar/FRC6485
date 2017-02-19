package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap.BRIDGE_STATE;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Kyle Saburao
 */
public class BridgeControl extends Command {

  private BRIDGE_STATE mReqState, mStartState;
  private boolean mShorted;

  /**
   * Moves the bridge into position.
   * 
   * @param state The requested bridge state.<br>
   *        Pass BRIDGE_STATE "UNKNOWN" to toggle, "RAISED" to raise and "LOWERED" to lower.
   */
  public BridgeControl(BRIDGE_STATE state) {
    requires(Robot.BRIDGE);
    mReqState = state;
    mShorted = false;
    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mStartState = Robot.BRIDGE.getState();

    if (mReqState == BRIDGE_STATE.LOWERED && mReqState == BRIDGE_STATE.RAISED
        && mReqState == mStartState) {
      mShorted = true;
    } else {

      switch (mReqState) {
        case RAISED:
          Robot.BRIDGE.setRaise();
          break;
        case LOWERED:
          Robot.BRIDGE.setLower();
          break;
        default:

          /**
           * Case fall-through to control toggle option.
           */
          switch (mStartState) {
            case UNKNOWN:
            case RAISED:
            case RAISING:
              Robot.BRIDGE.setLower();
              break;
            case LOWERED:
            case LOWERING:
              Robot.BRIDGE.setRaise();
              break;
          }
          break;
      }
    }
  }

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
    SmartDashboard.putNumber("BRIDGE STOP REQ TIMESTAMP", Timer.getFPGATimestamp());
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
