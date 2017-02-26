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
    SmartDashboard.putNumber("INTAKE PWM", Robot.FUELINTAKE.getSpeed());
    SmartDashboard.putString("INTAKE STATE", intakeenum);

    // Automatically stop the intake motor if it is stalled.
    // if (Robot.FUELINTAKE.getSpeed() != 0.0
    // && Math.abs(Robot.FUELINTAKE.getCurrent()) > Math.abs(RobotMap.FUELINTAKE_STALLCURRENT)) {
    // new IntakeInstantStop().start();
    // }
  }

}
