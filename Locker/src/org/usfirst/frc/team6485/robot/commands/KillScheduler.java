package org.usfirst.frc.team6485.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class KillScheduler extends InstantCommand {

    public KillScheduler() {
        super();
    }

    protected void initialize() {
      Scheduler.getInstance().removeAll();
    }

}
