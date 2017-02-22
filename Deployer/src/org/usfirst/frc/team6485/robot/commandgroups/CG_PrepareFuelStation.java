package org.usfirst.frc.team6485.robot.commandgroups;

import org.usfirst.frc.team6485.robot.commands.BridgeAutoMove;
import org.usfirst.frc.team6485.robot.commands.IntakePowerRamp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_PrepareFuelStation extends CommandGroup {

  public CG_PrepareFuelStation() {
    addParallel(new IntakePowerRamp(0.0));
    addSequential(new BridgeAutoMove(false));
  }

}
