package org.usfirst.frc.team6485.robot.utility;

import org.usfirst.frc.team6485.robot.Robot;
import org.usfirst.frc.team6485.robot.commands.IntakeInstantStop;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Kyle Saburao
 */
public class IntakeReporter {

  public static FuelIntake system = Robot.FUELINTAKE;

  public static void report() {
    String intakeenum = "";
    switch (system.getDirectionalState()) {
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
    SmartDashboard.putNumber("INTAKE PWM", system.getSpeed());
    SmartDashboard.putString("INTAKE STATE", intakeenum);

    // Shutdown the intake motor if the battery voltage dips below 7.5 volts.
    if (Robot.DRIVERSTATION.getBatteryVoltage() < 7.5 && system.getSpeed() != 0.0) {
      new IntakeInstantStop().start();
    }

    // Automatically stop the intake motor if it is stalled.
    // if (Robot.FUELINTAKE.getSpeed() != 0.0
    // && Math.abs(Robot.FUELINTAKE.getCurrent()) > Math.abs(RobotMap.FUELINTAKE_STALLCURRENT)) {
    // new IntakeInstantStop().start();
    // }
  }

}
