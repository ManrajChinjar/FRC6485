package org.usfirst.frc.team6485.robot.autonomous;

import org.usfirst.frc.team6485.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class A_RedAllianceRight extends CommandGroup {

  public A_RedAllianceRight() {
    addSequential(new DriveDistance(1.65, 0.65));
  }

}
