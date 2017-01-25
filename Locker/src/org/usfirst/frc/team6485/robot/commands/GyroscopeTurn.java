package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Executes a drive train point-turn to a specified amount of degrees relative to the starting orientation.<br>
 * <br>
 * <b>Arguments:</b> double angle (Negative turns left, positive turns right)
 */
public class GyroscopeTurn extends Command {

	private double currentAngle;
    private double startAngle;
    private double targetAngle;
    private double turnSpeed;
    private double angularTolerance = 1.00;
    
    private double angleRequest;
    
    public double error;


    public GyroscopeTurn(double angle) {

	    angleRequest = angle;
		requires(Robot.drivetrain);

    }


    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	startAngle = Robot.drivetrain.getGyroAngle();
    	targetAngle = startAngle + angleRequest;
    	turnSpeed = (angleRequest < 0) ? 0.55 : -0.55;
   
    	setTimeout(5);

    }


    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    Robot.drivetrain.arcadeDrive(0, turnSpeed);
    currentAngle = Robot.drivetrain.getGyroAngle();
    error = targetAngle - currentAngle;
    SmartDashboard.putNumber("Gyro turn start angle", startAngle);
    SmartDashboard.putNumber("Gyro turn target angle", targetAngle);
    SmartDashboard.putNumber("Gyro turn error", error);

    }


    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

	return (Math.abs(error) <= angularTolerance) 
			|| isTimedOut();

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
