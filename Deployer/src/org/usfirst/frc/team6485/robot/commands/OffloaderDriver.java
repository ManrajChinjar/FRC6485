package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Kyle Saburao
 */
public class OffloaderDriver extends Command {

  private double mXYAxisRequestL;

  public OffloaderDriver() {
    requires(Robot.OFFLOADER);
    setInterruptible(true);
  }

  /**
   * Control the offloader motor using the left stick of the Xbox controller. <br>
   * Up rolls (tightens) the fabric to release fuel, while down unrolls the fabric to hold them.
   * <br>
   * <b>BE SURE TO MAKE SURE THAT THE MOTOR DOESN'T OVER GO THE LIMITS. DO NOT OVERTIGHTEN OR
   * OVERLOOSEN.</b>
   */
  private void offloaderControl() {
    if (Math.abs(mXYAxisRequestL) > 0.1) {
      Robot.OFFLOADER.set(mXYAxisRequestL);
    } else {
      Robot.OFFLOADER.set(0);
    }

  }

  // Called just before this Command runs the first time
  protected void initialize() {
    Robot.OFFLOADER.stop();
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    mXYAxisRequestL = Robot.oi.getXBOXLeftJoyY();
    if (Math.abs(mXYAxisRequestL) > 0.075) {
      offloaderControl();
    } else {
      Robot.OFFLOADER.stop();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  protected void end() {
    Robot.OFFLOADER.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
    end();
  }
}
