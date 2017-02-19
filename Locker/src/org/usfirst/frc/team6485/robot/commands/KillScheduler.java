package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * @author Kyle Saburao
 */
public class KillScheduler extends InstantCommand {

  public KillScheduler() {
    super();
    requires(Robot.DRIVETRAIN);
  }

  @Override
  protected void initialize() {
    Scheduler.getInstance().removeAll();
  }

}
