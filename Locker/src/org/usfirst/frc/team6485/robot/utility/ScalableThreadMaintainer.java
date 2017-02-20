package org.usfirst.frc.team6485.robot.utility;

import edu.wpi.first.wpilibj.Timer;

public interface ScalableThreadMaintainer {

  public static void maintain(boolean state) {
    if (state) {
      Timer.delay(.25);
    }
  }

}
