package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.subsystems.Bridge.BRIDGE_STATE;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Kyle Saburao
 */
public class BridgeMaintainer extends Command {

  private BRIDGE_STATE mRequiredState, mCurrentState;
  private final double kBridgeSpeed = RobotMap.BRIDGE_MAINTAIN_PWM; // Default to raise

  /**
   * Maintains the state of the bridge in case something moves it while idling.
   */
  public BridgeMaintainer() {
    requires(Robot.BRIDGE);
  }

  @Override
  protected void initialize() {
    Robot.BRIDGE.updateState();
  }

  @Override
  protected void execute() {
    mCurrentState = Robot.BRIDGE.getState();
    mRequiredState = Robot.BRIDGE.getRequiredState();

    if (mCurrentState != mRequiredState) {
      switch (mRequiredState) {
        case RAISED:
          Robot.BRIDGE.setMotor(kBridgeSpeed);
          break;
        case LOWERED:
          Robot.BRIDGE.setMotor(-kBridgeSpeed);
          break;
        default:
          break;
      }
    } else {
      Robot.BRIDGE.stop();
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.BRIDGE.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
