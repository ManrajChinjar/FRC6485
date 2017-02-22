package org.usfirst.frc.team6485.robot.commandgroups;

import org.usfirst.frc.team6485.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_PassBaseLine extends CommandGroup {

  public CG_PassBaseLine() {
    addSequential(new DriveDistance(1.65, 0.60));
  }

}
