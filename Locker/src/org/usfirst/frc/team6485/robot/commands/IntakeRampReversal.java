package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake.IntakeState;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * @author Kyle Saburao
 */
public class IntakeRampReversal extends InstantCommand {

  private Command IRR;
  private Command IRI;

  public IntakeRampReversal() {
    super();
    requires(Robot.FUELINTAKE);
  }

  @Override
  protected void initialize() {
    IRR = new IntakePowerRamp(-RobotMap.FUELINTAKE_NORMAL_PWM);
    IRI = new IntakePowerRamp(-Robot.FUELINTAKE.getSpeed());

    if (Robot.FUELINTAKE.getDirectionalState() == IntakeState.HALT) {
      IRR.start();
      IRI.cancel();
    } else {
      IRI.start();
      IRR.cancel();
    }
  }

}
