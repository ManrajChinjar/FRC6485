package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Stops the intake motor.
 */
public class IntakeStop extends InstantCommand {

    public IntakeStop() {
        super();
        requires(Robot.fuelintake);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.fuelintake.stop();
    }

}
