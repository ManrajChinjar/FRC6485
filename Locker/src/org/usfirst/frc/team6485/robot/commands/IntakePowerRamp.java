package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Performs integration to ramp the speed of the intake motor to smoothen current draw.
 * @author Kyle Saburao
 */
public class IntakePowerRamp extends Command {

    private double mStartSpeed, mTargetSpeed, mPowerPerCycle, mPowerAccumulator = 0;
    private double kRampTimeSeconds = RobotMap.INTAKEPOWERRAMP_TIME_SECONDS;
    private int mRampTargetCycles = (int) Math.ceil(kRampTimeSeconds / 0.02);
    private int mRampCycles;

    /**
     * Linearizes the power ramp of the intake motor to prevent voltage spikes or other problems.
     * @param speed -1 is full intake, 1 is full reverse, 0 is stop.
     */
    public IntakePowerRamp(double speed) {
	requires(Robot.fuelintake);
	mTargetSpeed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	mStartSpeed = Robot.fuelintake.getSpeed();
	mPowerPerCycle = (mTargetSpeed - mStartSpeed) / (double) mRampTargetCycles;
	mPowerAccumulator = mStartSpeed;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	mPowerAccumulator += mPowerPerCycle;
	if ((Math.abs(Robot.fuelintake.getSpeed() - mTargetSpeed) < 0.1) 
		|| mRampTargetCycles - mRampCycles <= 1) { // Ensure that the final speed is always set.
	    Robot.fuelintake.setSpeed(mTargetSpeed);
	}
	else {
	    Robot.fuelintake.setSpeed(mPowerAccumulator);
	}
	mRampCycles++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return (mRampCycles >= mRampTargetCycles);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
