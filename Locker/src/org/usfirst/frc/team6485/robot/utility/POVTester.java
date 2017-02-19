package org.usfirst.frc.team6485.robot.utility;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class POVTester {

  public static void report() {
    SmartDashboard.putNumber("LOGITECH POV BASE", Robot.oi.getLPOV());
    SmartDashboard.putNumber("Logitech POV 0", Robot.oi.getLPOV(0));
    SmartDashboard.putNumber("Logitech POV 1", Robot.oi.getLPOV(1));
    SmartDashboard.putNumber("Logitech POV 2", Robot.oi.getLPOV(2));
    SmartDashboard.putNumber("Logitech POV 3", Robot.oi.getLPOV(3));
    SmartDashboard.putNumber("Logitech POV 4", Robot.oi.getLPOV(4));
    SmartDashboard.putNumber("Logitech POV 5", Robot.oi.getLPOV(5));
    SmartDashboard.putNumber("Logitech POV 6", Robot.oi.getLPOV(6));
    SmartDashboard.putNumber("Logitech POV 7", Robot.oi.getLPOV(7));
    SmartDashboard.putNumber("Logitech POV 8", Robot.oi.getLPOV(8));
  }
}
