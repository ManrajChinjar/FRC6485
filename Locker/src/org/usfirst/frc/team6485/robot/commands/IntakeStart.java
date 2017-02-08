package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake.IntakeState;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * @author Kyle Saburao
 */
public class IntakeStart extends InstantCommand {

  /**
   * Start the intake motor to intake mode.
   */
  public IntakeStart() {
    super();
    requires(Robot.fuelintake);
  }

  // Called once when the command executes
  protected void initialize() {
    Robot.fuelintake.set(IntakeState.IN);
  }

}
