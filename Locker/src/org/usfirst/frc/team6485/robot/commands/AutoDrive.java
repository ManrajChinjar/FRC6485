package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
public class AutoDrive extends Command {

    // ASSUMES THAT THE ROBORIO WILL OPERATE AT 50 HERTZ
    // TODO Also allow metre distance via future averaged encoder units

    private final double kP = 1.0 / 50.0;
    private double mCurrentAngle, mSpeed, cPT, mTime;
    private Gyro gyroscope = Robot.drivetrain.getGyro();

    /**
     * <b>WORK IN PROGRESS</b><br>
     * <br>
     * The ADXRS450 gyroscope is used to determine any rotational drift and if so,
     * correct the error. Rotational oscillation means that kP is too high, 
     * while not working at all means that it's too low.
     * @param speed A double value within the range [-1, 1] back or forth
     */
    public AutoDrive(double speed, double time) {
	requires(Robot.drivetrain);
	if (time < 0) time = 0;
	setTimeout(time);
	mSpeed = speed;
	mTime = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	gyroscope.reset();
	System.out.println(String.format("Driving at %.2f for %.2f seconds.", mSpeed, mTime));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	mCurrentAngle = gyroscope.getAngle();
	cPT = -mCurrentAngle * kP;
	if (mSpeed < 0) cPT *= -1;
	SmartDashboard.putNumber("Gyroscope cPT", cPT);
	Robot.drivetrain.drive(mSpeed, cPT);
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
