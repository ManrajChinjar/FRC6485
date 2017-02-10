package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * @author Kyle Saburao
 */
public class IntakeInstantStop extends InstantCommand {

  /**
   * Immediately halts the intake motor.
   */
  public IntakeInstantStop() {
    super();
    requires(Robot.fuelintake);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.fuelintake.stop();
  }

}
