package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Kyle Saburao
 */
public class AutoDriveCurve extends Command {

  private double mSpeed, mCurve, mTimeRequest, mStartTime;

  /**
   * Drives the robot according to three parameters. This effectively mimics a periodic call of
   * .drive() from the DriveTrain for <i>t</i> amount of seconds.
   * 
   * @param speed The drive train speed [-1, 1]
   * @param curve The curve of the trajectory [-1, 1]
   * @param time The time of the drive (t|t > 0, t = R)
   */
  public AutoDriveCurve(double speed, double curve, double time) {
    requires(Robot.drivetrain);
    if (time < 0)
      time = 0;
    setTimeout(time + 1.0);
    mSpeed = speed;
    mCurve = curve;
    mTimeRequest = time;
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    mStartTime = Timer.getFPGATimestamp();
    System.out.println(String.format("Driving at %.2f with %.2f curve for %.2f seconds.", mSpeed,
        mCurve, mTimeRequest));
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    Robot.drivetrain.drive(mSpeed, mCurve);
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return (Timer.getFPGATimestamp() - mStartTime >= mTimeRequest) || isTimedOut();
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
