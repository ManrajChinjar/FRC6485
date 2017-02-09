package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;
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
  @Override
  protected void initialize() {
    Robot.fuelintake.setSpeed(RobotMap.FUELINTAKE_NORMAL_PWM);
    
  }

}
