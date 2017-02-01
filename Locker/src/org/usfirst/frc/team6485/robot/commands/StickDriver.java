package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * <i>Kyle Saburao 2017</i>
 */
public class StickDriver extends Command {


    private int mDutyCycleInitial = 0, 
	    mDutyCycle = 0, 
	    mDutyCycleDelta = 0;

    private boolean mThreadFlankSet = false;

    private double mLXAxisRequest, 
                mLYAxisRequest, 
                mXXAxisRequestL, 
                mXYAxisRequestL,
                mXYAxisRequestR;

    public StickDriver() {
	// Use requires() here to declare subsystem dependencies
	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	System.out.println("I'll try spinning. That's a good trick.");
    }
    
    private void logitechControl() {
	if (!Robot.oi.getLButtonPressed(7)) {
	    if (Robot.oi.getLButtonPressed(2)) {
		Robot.drivetrain.forwardBackDrive(mLYAxisRequest);	
	    }
	    else if (Robot.oi.getLButtonPressed(4)) {
		Robot.drivetrain.turnOnSpot(mLXAxisRequest);
	    }
	    else if (Robot.oi.getLButtonPressed(3)) {
		Robot.drivetrain.gyroStraightDrive(mLYAxisRequest);
	    }
	    else {
		Robot.drivetrain.arcadeDrive(mLYAxisRequest, mLXAxisRequest);
	    }
	}
	else {
	    if (!mThreadFlankSet) {
		mDutyCycleInitial = mDutyCycle;
		Robot.drivetrain.flankSpeed();
		mThreadFlankSet = true;
	    }
	    else {
		if (mDutyCycleDelta <= 100) {
		    Robot.drivetrain.flankSpeed();
		}
		else {

		    Robot.drivetrain.arcadeDrive(mLYAxisRequest, mLXAxisRequest);
		}
	    }
	}
    }
    
    private void xboxControl() {
	if (!Robot.oi.getXBOXButtonPressed(5)) {
	    Robot.drivetrain.tankDrive(mXYAxisRequestL, mXYAxisRequestR);
	}
	else {
	    if (Math.abs(mXYAxisRequestR) > 0.05) {
		Robot.drivetrain.tankDrive(mXYAxisRequestR, mXYAxisRequestR);
	    }
	    else if (Math.abs(mXYAxisRequestL) > 0.05 
		    || Math.abs(mXXAxisRequestL) > 0.05) {
		Robot.drivetrain.arcadeDrive(mXYAxisRequestL, mXXAxisRequestL);	
	    }
	    else {
		Robot.drivetrain.stop();
	    }
	}
    }

    // Called repeatedly when this Command is scheduled to run
    // TODO Run all motors at 50% (0.5) power to see which ones require inversion.
    // Argument inversion is temporary while I figure out the final motor inversions.
    // In addition, have a deadzone for the rotation axis (~5% either side) 
    // where effectively 0 rotational request forces the robot to drive straight using the gyroscope.
    protected void execute() {
	mLXAxisRequest = -Robot.oi.getLJoyX() * Robot.oi.getLSliderScale();
	mLYAxisRequest = -Robot.oi.getLJoyY() * Robot.oi.getLSliderScale();

	mXXAxisRequestL = -Robot.oi.getXBOXLeftJoyX();
	mXYAxisRequestL = -Robot.oi.getXBOXLeftJoyY();
	mXYAxisRequestR = -Robot.oi.getXBOXRightJoyY();

	mDutyCycleDelta = mDutyCycle - mDutyCycleInitial;

	if (Robot.oi.getLMainTrigger()) {
	    logitechControl();
	}
	else if (Robot.oi.getXBOXSafety()) {
	    xboxControl();
	}
	else {
	    Robot.drivetrain.stop();
	}

	if (mDutyCycleDelta > 200) {
	    mThreadFlankSet = false;
	}

	if (!Robot.oi.getLButtonPressed(3)) {
	    Robot.drivetrain.setGyroZSet(false);
	}

	mDutyCycle++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	Robot.drivetrain.stop();
	Robot.drivetrain.setGyroZSet(false);
	mThreadFlankSet = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }

}
