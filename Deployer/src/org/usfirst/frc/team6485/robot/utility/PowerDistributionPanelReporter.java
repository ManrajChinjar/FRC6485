package org.usfirst.frc.team6485.robot.utility;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PowerDistributionPanelReporter {
  
  private static PowerDistributionPanel PDP = new PowerDistributionPanel();
  
  public static double getChannelCurrent(int index) {
    return PDP.getCurrent(index);
  }
  
  private static void reportChannels() {
    SmartDashboard.putNumber("FL Current", getChannelCurrent(0));
    SmartDashboard.putNumber("FR Current", getChannelCurrent(15));
    SmartDashboard.putNumber("RL Current", getChannelCurrent(1));
    SmartDashboard.putNumber("RR Current", getChannelCurrent(14));
    // Fix the following PDP indexes.
//    SmartDashboard.putNumber("Bridge Current", getChannelCurrent(7));
//    SmartDashboard.putNumber("Intake Current", getChannelCurrent(7));
//    SmartDashboard.putNumber("Offloader Current", getChannelCurrent(7));
  }
  
  public static void report() {
    reportChannels();
  }

}
