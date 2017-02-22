package org.usfirst.frc.team6485.robot.utility;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Kyle Saburao
 */
public class DriveTrainReporter {

  private static DriveTrain system = Robot.DRIVETRAIN;

  public static void report() {
    SmartDashboard.putNumber("Gyroscope Value", system.getGyro().getAngle());
    SmartDashboard.putNumber("X-Axis Logitech Request (NEG)", -Robot.oi.getLJoyX());
    SmartDashboard.putNumber("Y-Axis Logitech Request (NEG)", -Robot.oi.getLJoyY());

    double[] work = system.getMotorPWMS();
    SmartDashboard.putNumber("Front Left PWM", work[0]);
    SmartDashboard.putNumber("Rear Left PWM", work[1]);
    SmartDashboard.putNumber("Front Right PWM", work[2]);
    SmartDashboard.putNumber("Rear Right PWM", work[3]);
    SmartDashboard.putBoolean("DriveTrain Encoder Moving", !system.getEncoder().getStopped());
    SmartDashboard.putNumber("DriveTrain Encoder Distance", system.getEncoder().getDistance());
    SmartDashboard.putNumber("DriveTrain Encoder m/s", system.getEncoder().getRate());
  }

}
