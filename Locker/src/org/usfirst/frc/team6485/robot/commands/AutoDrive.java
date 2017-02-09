package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Executes drive train movement either back or forth for a specified time period.<br>
 * <br>
 * <b>Arguments:</b> double speed, double time<br>
 * <br>
 * TODO Allow metre distance via encoder units.
 * 
 * @author Kyle Saburao
 */
public class AutoDrive extends Command {

  // ASSUMES THAT THE ROBORIO WILL OPERATE AT 50 HERTZ
  // TODO Also allow metre distance via future averaged encoder units

  private final double kP = RobotMap.AUTODRIVE_GYRO_KP;
  private double mCurrentAngle, mSpeed, cPT, mTimeWindow, mStartTime;
  private Gyro gyroscope = Robot.drivetrain.getGyro();

  /**
   * Drives forward and uses the gyroscope to maintain direction.
   * 
   * @param speed The speed to drive
   * @param time The time to drive
   */
  public AutoDrive(double speed, double time) {
    requires(Robot.drivetrain);
    if (time < 0)
      time = 0;
    setTimeout(time + 1.0); // Kills the command one second after its
    // allotted window.
    mSpeed = speed;
    mTimeWindow = time;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    gyroscope.reset();
    mStartTime = Timer.getFPGATimestamp();
    System.out.println(String.format("Driving at %.2f for %.2f seconds.", mSpeed, mTimeWindow));
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    mCurrentAngle = gyroscope.getAngle();
    // Gyroscope measurement increases to the right, but the drive train
    // turn rate is negative the same direction.
    // TODO Check if this is still the case.
    cPT = mCurrentAngle * kP;
    if (mSpeed < 0)
      cPT *= -1;
    SmartDashboard.putNumber("Gyroscope cPT", cPT);
    Robot.drivetrain.drive(mSpeed, cPT);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Timer.getFPGATimestamp() - mStartTime >= mTimeWindow) || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drivetrain.stop();
  }


}
