package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveCurve extends Command {
    
    private double mSpeed;
    private double mCurve;
    private double mTime;
    
    public AutoDriveCurve(double speed, double curve, double time) {
	requires(Robot.drivetrain);
	setTimeout(time);
	mSpeed = speed;
	mCurve = curve;
	mTime = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	System.out.println(String.format("Driving at %.2f with %.2f curve for %.2f seconds.", mSpeed, mCurve, mTime));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.drivetrain.drive(mSpeed, mCurve);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
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
