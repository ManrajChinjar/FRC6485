package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake.IntakeState;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Sets the fuel intake to standard reverse if it's currently off
 * and will reverse the current speed if it's already on.
 * @author Kyle Saburao
 */
public class IntakeReversal extends InstantCommand {

    public IntakeReversal() {
	super();
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot.fuelintake);
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.fuelintake.getDirectionalState() == IntakeState.HALT) {
	    Robot.fuelintake.set(IntakeState.EXFIL);
	}
	else Robot.fuelintake.reverseSpeed();
    }

}
