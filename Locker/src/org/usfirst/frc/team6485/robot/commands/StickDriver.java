package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StickDriver extends Command {
	
	
	private int mThreadFlankRan = 0;
	private int mThreadExecutionRound = 0;
	private boolean mThreadFlankSet = false;


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
		double mLXAxisRequest = -Robot.oi.getLJoyX(); // Negative to swap direction
		double mLYAxisRequest = -Robot.oi.getLJoyY();
		
		double mXYAxisRequestL = -Robot.oi.getXBOXLeftJoyY();
		double mXYAxisRequestR = -Robot.oi.getXBOXRightJoyY();
		
		mLXAxisRequest *= Robot.oi.getLSliderScale();
		mLYAxisRequest *= Robot.oi.getLSliderScale();

		if (Robot.oi.getLMainTrigger()) {
			if (!Robot.oi.getLButtonPressed(7)) {
				if (!Robot.oi.getLButtonPressed(4)) {
//					Robot.drivetrain.mGyroFlag = false;
					Robot.drivetrain.arcadeDrive(mLYAxisRequest, mLXAxisRequest);
					
				}
				else {
					Robot.drivetrain.zAxisDrive(mLYAxisRequest);
				}
				if (mThreadExecutionRound - mThreadFlankRan > 200) {
					mThreadFlankSet = false;
				}
			}
			else {
				if (!mThreadFlankSet) {
					mThreadFlankRan = mThreadExecutionRound;
					Robot.drivetrain.flankSpeed();
					mThreadFlankSet = true;
				}
				else {
					if (mThreadExecutionRound - mThreadFlankRan <= 100) {
						Robot.drivetrain.flankSpeed();
					}
					else {
						if (mThreadExecutionRound - mThreadFlankRan > 200) {
							mThreadFlankSet = false;
						}
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
				else if (Math.abs(mXYAxisRequestL) > 0.05) {
					Robot.drivetrain.tankDrive(mXYAxisRequestL, mXYAxisRequestL);	
				}
				else {
					Robot.drivetrain.stop();
				}
			}
		}
		else {
			Robot.drivetrain.stop();
		}
		
		mThreadExecutionRound++;

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
