package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.Bridge.BRIDGE_STATE;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowerBridge extends Command {

  /**
   * Lowers the bridge to allow for fuel intake or gear loading.
   */
  public LowerBridge() {
    requires(Robot.BRIDGE);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.BRIDGE.setLower();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.BRIDGE.updateState();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.BRIDGE.getState() == BRIDGE_STATE.LOWERED;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.BRIDGE.stop();
    Robot.BRIDGE.updateState();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
