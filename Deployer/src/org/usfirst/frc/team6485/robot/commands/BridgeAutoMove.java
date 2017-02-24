package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Kyle Saburao
 */
public class BridgeAutoMove extends Command {

  private boolean mState;
  private double mStartTime, kWaitTimeSeconds = 3.0;

  /**
   * Automatically move the bridge to the right state via a timed command. <br>
   * This works because the Spark Motor Controller automatically halts the bridge motor when the
   * limit switches are pressed.
   * 
   * @param state True for up, false for down
   */
  public BridgeAutoMove(boolean state) {
    requires(Robot.BRIDGE);
    setInterruptible(false);
    mState = state;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (mState) {
      Robot.BRIDGE.setRaise();
    } else {
      Robot.BRIDGE.setLower();
    }
    mStartTime = Timer.getFPGATimestamp();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // If the Spark controller is overriding the PWM signal, the current flow should drop to an idle
    // level.
    // If the controller is idling but the PWM rate is not 0.0, then the bridge has hit a switch.
    // return (Math.abs(Robot.BRIDGE.getCurrent()) <= RobotMap.BRIDGE_IDLECURRENTMAGNITUDE
    // && Robot.BRIDGE.getSpeed() != 0.0)
    return Timer.getFPGATimestamp() - mStartTime >= kWaitTimeSeconds;
    // || Robot.BRIDGE.getEncoder().getStopped();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.BRIDGE.stop();
  }

}
