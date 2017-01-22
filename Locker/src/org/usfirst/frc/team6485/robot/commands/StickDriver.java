package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StickDriver extends Command {


    private int loopCountInitial = 0;
    private int loopCountCurrent = 0;
    private int loopCountDelta;
    private boolean mThreadFlankSet = false;

    private double mLXAxisRequest;
    private double mLYAxisRequest;
    private double mXXAxisRequestL;
    private double mXYAxisRequestL;
    private double mXYAxisRequestR;


    public StickDriver() {

	// Use requires() here to declare subsystem dependencies
	requires(Robot.drivetrain);

    }


    // Called just before this Command runs the first time
    protected void initialize() {

	System.out.println("I'll try spinning. That's a good trick.");

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

	loopCountDelta = loopCountCurrent - loopCountInitial;

	if (Robot.oi.getLMainTrigger()) {
	    if (!Robot.oi.getLButtonPressed(7)) {
		if (Robot.oi.getLButtonPressed(2)) {
		    Robot.drivetrain.forwardBackDrive(mLYAxisRequest);	
		}
		else if (Robot.oi.getLButtonPressed(4)) {
		    Robot.drivetrain.turnOnSpot(mLXAxisRequest);
		}
		else if (Robot.oi.getLButtonPressed(3)) {
		    Robot.drivetrain.gyroTest(mLYAxisRequest);
		}
		else {
		    Robot.drivetrain.arcadeDrive(mLYAxisRequest, mLXAxisRequest);
		}
	    }
	    else {
		if (!mThreadFlankSet) {
		    loopCountInitial = loopCountCurrent;
		    Robot.drivetrain.flankSpeed();
		    mThreadFlankSet = true;
		}
		else {
		    if (loopCountDelta <= 100) {
			Robot.drivetrain.flankSpeed();
		    }
		    else {

			Robot.drivetrain.arcadeDrive(mLYAxisRequest, mLXAxisRequest);
		    }
		}

	    }
	}
	else if (Robot.oi.getXBOXSafety()) {
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
	else {
	    Robot.drivetrain.stop();
	}

	if (loopCountDelta > 200) {
	    mThreadFlankSet = false;
	}

	if (!Robot.oi.getLButtonPressed(3)) {
	    Robot.drivetrain.setgyroZSet(false);
	}

	loopCountCurrent++;

    }


    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

	return false;

    }


    // Called once after isFinished returns true
    protected void end() {

	Robot.drivetrain.stop();

    }


    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

	end();

    }

}
