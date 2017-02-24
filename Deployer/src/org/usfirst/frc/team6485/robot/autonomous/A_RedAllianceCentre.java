package org.usfirst.frc.team6485.robot.autonomous;

import org.usfirst.frc.team6485.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class A_RedAllianceCentre extends CommandGroup {

  public A_RedAllianceCentre() {
    addSequential(new DriveDistance(1.65, 0.65));
  }

}
