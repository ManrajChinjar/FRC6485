package org.usfirst.frc.team6485.robot.commandgroups;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.commands.Delay;
import org.usfirst.frc.team6485.robot.commands.IntakePowerRamp;
import org.usfirst.frc.team6485.robot.commands.RaiseBridge;

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
