package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake.IntakeState;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * @author Kyle Saburao
 */
public final class IntakeRampReversal extends InstantCommand {

  private Command mIntakeCommand;

  public IntakeRampReversal() {
    super();
    requires(Robot.FUELINTAKE);
  }

  @Override
  protected void initialize() {
    if (Robot.FUELINTAKE.getDirectionalState() == IntakeState.HALT) {
      mIntakeCommand = new IntakePowerRamp(-RobotMap.FUELINTAKE_NORMAL_PWM);
    } else {
      mIntakeCommand = new IntakePowerRamp(-Robot.FUELINTAKE.getSpeed());
    }
    mIntakeCommand.start();
  }

}
