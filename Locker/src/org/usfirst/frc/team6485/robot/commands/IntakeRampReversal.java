package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake.IntakeState;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * @author Kyle Saburao
 */
public class IntakeRampReversal extends InstantCommand {

  public IntakeRampReversal() {
    super();
    requires(Robot.fuelintake);
  }

  @Override
  protected void initialize() {
    if (Robot.fuelintake.getDirectionalState() == IntakeState.HALT)
      Scheduler.getInstance().add(new IntakePowerRamp(-RobotMap.FUELINTAKE_NORMAL_PWM));
    else
      Scheduler.getInstance().add(new IntakePowerRamp(-Robot.fuelintake.getSpeed()));
  }

}
