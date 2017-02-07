package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake.IntakeState;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * @author Kyle Saburao
 */
public class IntakeRampReversal extends InstantCommand {

    public IntakeRampReversal() {
        super();
        requires(Robot.fuelintake);
    }

    // Called once when the command executes
    protected void initialize() {
	if (Robot.fuelintake.getDirectionalState() == IntakeState.HALT) {
	    new IntakePowerRamp(-Robot.fuelintake.getNormalIntakeSpeed());
	}
	else new IntakePowerRamp(-Robot.fuelintake.getSpeed());
    }

}
