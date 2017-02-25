package org.usfirst.frc.team6485.robot.autonomous;

import org.usfirst.frc.team6485.robot.commandgroups.CG_PassBaseLine;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class A_BlueAllianceLeft extends CommandGroup {

  public A_BlueAllianceLeft() {
    addSequential(new CG_PassBaseLine());
  }

}
