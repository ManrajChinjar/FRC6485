package org.usfirst.frc.team6485.robot.utility;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrainBridgeReporter {

  public static void report() {
    SmartDashboard.putNumber("Gyroscope Value", Robot.DRIVETRAIN.getGyro().getAngle());
    SmartDashboard.putNumber("X-Axis Logitech Request (NEG)", -Robot.oi.getLJoyX());
    SmartDashboard.putNumber("Y-Axis Logitech Request (NEG)", -Robot.oi.getLJoyY());

    double[] work = Robot.DRIVETRAIN.getMotorPWMS();
    SmartDashboard.putNumber("Front Left PWM", work[0]);
    SmartDashboard.putNumber("Rear Left PWM", work[1]);
    SmartDashboard.putNumber("Front Right PWM", work[2]);
    SmartDashboard.putNumber("Rear Right PWM", work[3]);
    SmartDashboard.putNumber("Left PWM Group Difference", Math.abs(work[0] - work[1]));
    SmartDashboard.putNumber("Right PWM Group Difference", Math.abs(work[2] - work[3]));
    SmartDashboard.putBoolean("DriveTrain Encoder Moving", !Robot.DRIVETRAIN.getEncoder().getStopped());
    SmartDashboard.putNumber("DriveTrain Encoder Distance", Robot.DRIVETRAIN.getEncoder().getDistance());
    
    // Bridge
    SmartDashboard.putNumber("Bridge PWM", Robot.BRIDGE.getSpeed());
    SmartDashboard.putBoolean("Bridge Encoder Moving", !Robot.BRIDGE.getEncoder().getStopped());
  }

}
