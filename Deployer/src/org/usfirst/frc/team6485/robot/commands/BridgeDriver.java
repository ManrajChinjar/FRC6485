package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BridgeDriver extends Command {

  public static double kBridgeNormalPWM = RobotMap.BRIDGE_NORMALPWM;

  public BridgeDriver() {
    requires(Robot.BRIDGE);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.BRIDGE.stop();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Math.abs(Robot.oi.getXBOXRightJoyY()) > 0.1) {
      if (-Robot.oi.getXBOXRightJoyY() > 0.0) {
        Robot.BRIDGE.setMotor(-Robot.oi.getXBOXRightJoyY() * RobotMap.BRIDGE_RAISEPWM);
      } else if (-Robot.oi.getXBOXRightJoyY() < 0.0) {
        Robot.BRIDGE.setMotor(-Robot.oi.getXBOXRightJoyY() * RobotMap.BRIDGE_LOWERPWM);
      } else {
        Robot.BRIDGE.stop();
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.BRIDGE.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
