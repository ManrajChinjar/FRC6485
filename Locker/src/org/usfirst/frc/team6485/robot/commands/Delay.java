package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Delays a command group for a specified amount of seconds.<br>
 * 60 seconds maximum.
 * 
 * @author Kyle Saburao
 */
public class Delay extends Command {

    private double mStartTime, mTimeLength;

    /**
     * Analogous to a wait function.
     * 
     * @param seconds
     *            The time to halt the entire system.
     */
    public Delay(double seconds) {
	mTimeLength = seconds;
	setTimeout(60);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	mStartTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.drivetrain.stop(); // To satisfy the watchdog.
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return (Timer.getFPGATimestamp() - mStartTime >= mTimeLength) || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
	String endtext = "Ending delay.";
	System.out.println(endtext);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}
