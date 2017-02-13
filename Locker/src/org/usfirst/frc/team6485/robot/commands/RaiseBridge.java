package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.Bridge.BRIDGE_STATE;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Kyle Saburao
 */
public class RaiseBridge extends Command {

  /**
   * Raises the bridge to allow for off-loading of fuel.
   */
  public RaiseBridge() {
    requires(Robot.BRIDGE);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.BRIDGE.setRaise();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.BRIDGE.updateState();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.BRIDGE.getState() == BRIDGE_STATE.RAISED;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.BRIDGE.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
