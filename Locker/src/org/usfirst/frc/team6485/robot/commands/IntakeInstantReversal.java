package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.RobotMap.IntakeState;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * @author Kyle Saburao
 */
public class IntakeInstantReversal extends InstantCommand {

  /**
   * Sets the fuel intake to standard reverse if it's currently off and will reverse the current
   * speed if it's already on.
   */
  public IntakeInstantReversal() {
    super();
    requires(Robot.FUELINTAKE);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    if (Robot.FUELINTAKE.getDirectionalState() == IntakeState.HALT) {
      Robot.FUELINTAKE.set(-RobotMap.FUELINTAKE_NORMALPWM);
    } else
      Robot.FUELINTAKE.reverseMagnitude();
  }

}
