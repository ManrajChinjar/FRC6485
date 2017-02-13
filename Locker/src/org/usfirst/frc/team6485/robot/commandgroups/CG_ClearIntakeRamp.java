package org.usfirst.frc.team6485.robot.commandgroups;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.commands.Delay;
import org.usfirst.frc.team6485.robot.commands.IntakePowerRamp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_ClearIntakeRamp extends CommandGroup {

  public CG_ClearIntakeRamp() {
    addSequential(new IntakePowerRamp(-RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new Delay(1.0));
    addSequential(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new Delay(1.0));
    addSequential(new IntakePowerRamp(-RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new Delay(1.5));
    addSequential(new IntakePowerRamp(0.0));
  }

}
