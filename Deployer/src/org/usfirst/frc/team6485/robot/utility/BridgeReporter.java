package org.usfirst.frc.team6485.robot.utility;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.Bridge;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BridgeReporter {

  private static Bridge system = Robot.BRIDGE;

  public static void report() {
    SmartDashboard.putNumber("Bridge PWM", system.getSpeed());
    // TODO Fix bridge encoder
    SmartDashboard.putBoolean("Bridge Encoder Moving", !system.getEncoder().getStopped());
    SmartDashboard.putNumber("BRIDGE ENCODER DISTANCE", system.getEncoder().getDistance());
  }

}
