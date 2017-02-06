package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Uses integration to ramp the speed of the intake motor to smoothen current draw.
 * <br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class IntakePowerRamp extends Command {
    
    private double mStartSpeed, mTargetSpeed;
    private double mPowerPerCycle, mPowerAccumulator = 0;
    private final double mRampTimeSeconds = 0.25;
    private int mRampTargetCycles = (int) Math.ceil(mRampTimeSeconds / 0.02);
    private int mRampCycles;

    public IntakePowerRamp(double speed) {
	requires(Robot.fuelintake);
	mTargetSpeed = speed;
	mStartSpeed = Robot.fuelintake.getSpeed();
	mPowerPerCycle = (mTargetSpeed - mStartSpeed) / (double) mRampTargetCycles;
	mPowerAccumulator = mStartSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	mPowerAccumulator += mPowerPerCycle;
	if (Math.abs(Robot.fuelintake.getSpeed() - mTargetSpeed) < 0.1) {
	    Robot.fuelintake.setSpeed(mTargetSpeed);
	}
	else {
	    Robot.fuelintake.setSpeed(mPowerAccumulator);
	}
	mRampCycles++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (mRampCycles == mRampTargetCycles);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
