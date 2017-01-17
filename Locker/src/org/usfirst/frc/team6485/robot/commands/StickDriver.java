package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StickDriver extends Command {


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
	double mXAxisRequest = -Robot.oi.getJoyX(); // Negative to swap direction
	double mYAxisRequest = Robot.oi.getInvertedY();

	mXAxisRequest *= Robot.oi.getSliderScale();
	mYAxisRequest *= Robot.oi.getSliderScale();
	
	if (Robot.oi.getMainTrigger()) {
	    if (!Robot.oi.getButtonPressed(7)) {
		if (!Robot.oi.getButtonPressed(4)) {
		    Robot.drivetrain.arcadeDrive(mYAxisRequest, mXAxisRequest);
		}
		else {
		    Robot.drivetrain.zAxisDrive(mYAxisRequest);
		}
	    }
	    else {
		Robot.drivetrain.flankSpeed();
	    }
	}
	else {
	    Robot.drivetrain.stop();
	}

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
    }
}
