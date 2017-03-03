package org.usfirst.frc.team6485.robot.utility;

import edu.wpi.first.wpilibj.Joystick;

public class XBOX extends Joystick {

  public XBOX(int port) {
    super(port);
  }

  public double getLeftJoyX() {
    return super.getRawAxis(0);
  }

  public double getLeftJoyY() {
    return super.getRawAxis(1);
  }

  public double getRightJoyX() {
    return super.getRawAxis(4);
  }

  public double getRightJoyY() {
    return super.getRawAxis(5);
  }

  /**
   * 
   * @param index Button index from [0, 12]
   * @return State of button or false if out of bounds.
   */
  public boolean getButton(int index) {
    return super.getRawButton(index);
  }

}
