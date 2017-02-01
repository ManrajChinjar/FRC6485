package org.usfirst.frc.team6485.robot.commands;

// Credits to Team 2175 for implementation idea.

import edu.wpi.first.wpilibj.command.Command;

/**
 * Delays a command group for a specified amount of seconds.<br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class Delay extends Command {
    
    public Delay(double seconds) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);\

	setTimeout(seconds);

    }

    // Called just before this Command runs the first time
    protected void initialize() {


    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

	return isTimedOut();

    }

    // Called once after isFinished returns true
    protected void end() {

	String endtext = "Ending delay.";
	System.out.println(endtext);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

	end();

    }

}
