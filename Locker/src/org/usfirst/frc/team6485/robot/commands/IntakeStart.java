package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Starts the intake motor to its standard running PWM setting.
 * <br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class IntakeStart extends InstantCommand {

    public IntakeStart() {
        super();
        requires(Robot.fuelintake);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.fuelintake.start();
    }

}
