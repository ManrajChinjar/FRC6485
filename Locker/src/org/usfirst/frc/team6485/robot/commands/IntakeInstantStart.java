package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * @author Kyle Saburao
 */
public class IntakeInstantStart extends InstantCommand {

  /**
   * Start the intake motor to intake mode.
   */
  public IntakeInstantStart() {
    super();
    requires(Robot.FUELINTAKE);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.FUELINTAKE.set(RobotMap.FUELINTAKE_NORMAL_PWM);
  }

}
