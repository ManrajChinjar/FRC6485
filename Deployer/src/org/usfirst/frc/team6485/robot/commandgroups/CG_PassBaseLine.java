package org.usfirst.frc.team6485.robot.commandgroups;

import org.usfirst.frc.team6485.robot.commands.DriveDistance;
import org.usfirst.frc.team6485.robot.commands.IntakePowerRamp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class CG_PassBaseLine extends CommandGroup {

  public CG_PassBaseLine() {
    addParallel(new IntakePowerRamp(0.0));
    addSequential(new DriveDistance(1.65, 0.60));
  }

}
