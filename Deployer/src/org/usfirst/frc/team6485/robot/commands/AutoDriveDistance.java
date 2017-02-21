package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Kyle Saburao
 */
public class AutoDriveDistance extends Command {

  private double mDistanceTargetMetres, mDistanceCurrentMetres, mSpeed;
  private final double kP = RobotMap.AUTODRIVE_GYROKP;

  /**
   * Automatically drive forward a specific distance.
   * 
   * @param distance Metres
   * @param speed [0 to 0.90]
   */
  public AutoDriveDistance(double distance, double speed) {
    requires(Robot.DRIVETRAIN);
    mDistanceTargetMetres = distance;
    mSpeed = speed;
    setInterruptible(false);
    if (speed < 0.0) {
      mDistanceTargetMetres = -Math.abs(mDistanceTargetMetres);
    }
    if (distance < 0.0) {
      mSpeed = -Math.abs(mSpeed);
    }
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    Robot.DRIVETRAIN.getEncoder().reset();
    Robot.DRIVETRAIN.getGyro().reset();
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    mDistanceCurrentMetres = Robot.DRIVETRAIN.getEncoder().getDistance();
    Robot.DRIVETRAIN.arcadeDrive(mSpeed, Robot.DRIVETRAIN.getGyro().getAngle() * kP);
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return Robot.DRIVETRAIN.getEncoder().getDistance() >= mDistanceTargetMetres;
  }

  // Called once after isFinished returns true
  protected void end() {
    Robot.DRIVETRAIN.stop();
  }

}
