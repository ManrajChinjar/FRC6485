package org.usfirst.frc.team6485.robot.autonomous;

import org.usfirst.frc.team6485.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class A_BlueAllianceLeft extends CommandGroup {

  public A_BlueAllianceLeft() {
    addSequential(new DriveDistance(1.65, 0.65));
  }

}
