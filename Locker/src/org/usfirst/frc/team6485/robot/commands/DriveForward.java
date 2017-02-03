package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Executes drive train movement either back or forth for a specified time period.<br>
 * Only use seconds accurate to one decimal place.<br>
 * <br>
 * <b>Arguments:</b> double speed, double time<br>
 * <br>
 * TODO Allow metre distance via encoder units
 * <br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class DriveForward extends Command {

    // ASSUMES THAT THE ROBORIO WILL OPERATE AT 50 HERTZ
    // TODO Also allow metre distance via future averaged encoder units

    private double mDutyCycle, 
    mDutyCycleTarget, 
    mSpeed;

    /**
     * Tells the drive train to go forwards for a specific amount of time
     * @param time ONLY PASS ACCURACY UP TO A TENTH OF A SECOND (t.t)
     */
    public DriveForward(double speed, double time) {
	requires(Robot.drivetrain);
	mDutyCycle = 0;
	mDutyCycleTarget = (time * 1000) / 20;
	this.mSpeed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	setTimeout(15);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	if (mDutyCycleTarget - mDutyCycle < 5) mSpeed *= 0.90;
	Robot.drivetrain.gyroStraightDrive(mSpeed);
	mDutyCycle++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return (mDutyCycle == mDutyCycleTarget) 
		|| isTimedOut();
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
