package org.usfirst.frc.team6485.robot.utility;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.subsystems.Offloader;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OffloaderReporter {

  private static Offloader system = Robot.OFFLOADER;

  public static void report() {
    SmartDashboard.putNumber("Offloader PWM", system.getSpeed());
  }

}
