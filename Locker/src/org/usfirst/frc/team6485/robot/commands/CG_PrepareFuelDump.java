package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class CG_PrepareFuelDump extends CommandGroup {

  /**
   * Stop the intake and raise the bridge. <br>
   * Will attempt to evacuate fuel stuck in the ramp.
   */
  public CG_PrepareFuelDump() {
    addSequential(new IntakePowerRamp(-RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new Delay(0.75));
    addSequential(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new Delay(0.75));
    addSequential(new IntakePowerRamp(-RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new Delay(1.5));
    addParallel(new IntakePowerRamp(0.0));
    addSequential(new RaiseBridge());
  }

}
