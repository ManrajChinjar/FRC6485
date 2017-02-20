package org.usfirst.frc.team6485.robot.utility;

import org.usfirst.frc.team6485.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BridgeReporter {

  public static void report() {
    String bridgecurrentstring = "";
    switch (Robot.BRIDGE.getState()) {
      case UNKNOWN:
        bridgecurrentstring = "UNKNOWN";
        break;
      case RAISED:
        bridgecurrentstring = "RAISED";
        break;
      case LOWERED:
        bridgecurrentstring = "LOWERED";
        break;
      case LOWERING:
        bridgecurrentstring = "LOWERING";
        break;
      case RAISING:
        bridgecurrentstring = "RAISING";
        break;
      default:
        break;
    }
    SmartDashboard.putString("Bridge State", bridgecurrentstring);

    String bridgerequiredstring = "";
    switch (Robot.BRIDGE.getRequiredState()) {
      case RAISED:
        bridgecurrentstring = "RAISED";
        break;
      case LOWERED:
        bridgecurrentstring = "LOWERED";
        break;
      default:
        bridgecurrentstring = "UNKNOWN";
        break;
    }
    SmartDashboard.putString("Required Bridge State", bridgerequiredstring);
    SmartDashboard.putNumber("Bridge PWM", Robot.BRIDGE.getSpeed());
  }

}
