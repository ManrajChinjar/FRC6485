package org.usfirst.frc.team6485.robot.autonomous;

import org.usfirst.frc.team6485.robot.commandgroups.CG_PassBaseLine;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class A_RedAllianceCentre extends CommandGroup {

  public A_RedAllianceCentre() {
    addSequential(new CG_PassBaseLine());
  }

}
