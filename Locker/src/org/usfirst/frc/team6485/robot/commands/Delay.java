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
   * @param seconds The time to halt the entire system.
   */
  public Delay(double seconds) {
    mTimeLength = seconds;
    setTimeout(seconds + .125);
    this.setInterruptible(false);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mStartTime = Timer.getFPGATimestamp();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.DRIVETRAIN.stop(); // To satisfy the watchdog.
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Timer.getFPGATimestamp() - mStartTime >= mTimeLength) || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println(String.format("Ending delay of %.3f seconds after %.3f actual seconds.",
        mTimeLength, (Timer.getFPGATimestamp() - mStartTime)));
  }

}
