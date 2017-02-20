package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * @author Kyle Sburao
 */
public class StopBridgeManually extends InstantCommand {

  public StopBridgeManually() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.BRIDGE);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.BRIDGE.stop();
  }

}
