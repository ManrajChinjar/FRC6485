package org.usfirst.frc.team6485.robot.autonomous;

import org.usfirst.frc.team6485.robot.commandgroups.CG_PassBaseLine;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class A_RedAllianceLeft extends CommandGroup {

  public A_RedAllianceLeft() {
    addSequential(new CG_PassBaseLine());
  }

}
