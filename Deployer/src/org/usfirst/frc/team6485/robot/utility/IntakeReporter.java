package org.usfirst.frc.team6485.robot.utility;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Kyle Saburao
 */
public class IntakeReporter {

  public static void report() {
    String intakeenum = "";
    switch (Robot.FUELINTAKE.getDirectionalState()) {
      case HALT:
        intakeenum = "HALT";
        break;
      case IN:
        intakeenum = "IN";
        break;
      case EVACUATE:
        intakeenum = "EVACUATE";
        break;
    }
    SmartDashboard.putNumber("Intake PWM", Robot.FUELINTAKE.getSpeed());
    SmartDashboard.putString("Intake ENUM", intakeenum);
  }

}
