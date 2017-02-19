package org.usfirst.frc.team6485.robot.autonomous;

import org.usfirst.frc.team6485.robot.commands.AutoDrive;
import org.usfirst.frc.team6485.robot.commands.AutoGyroTurn;
import org.usfirst.frc.team6485.robot.commands.Delay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class A_CentrePositionStart extends CommandGroup {

  public A_CentrePositionStart() {
    addSequential(new AutoDrive(.80, 2.0));
    addSequential(new Delay(3.0));
    addSequential(new AutoDrive(-.60, 2.0));
    addSequential(new AutoGyroTurn(90.0));
  }

}
