package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake.IntakeState;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Stops the intake motor.
 * <br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class IntakeStop extends InstantCommand {

    public IntakeStop() {
        super();
        requires(Robot.fuelintake);
    }

    // Called once when the command executes
    protected void initialize() {
	Robot.fuelintake.set(IntakeState.HALT);
    }

}
