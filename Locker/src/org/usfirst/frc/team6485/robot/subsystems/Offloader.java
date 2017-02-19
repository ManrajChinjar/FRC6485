package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.RobotMap.OFFLOADER_STATE;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The ball offloader subsystem.
 */
public class Offloader extends Subsystem {

  private final int kOffloaderPWMSlot = RobotMap.OFFLOADER_MOTOR;
  private final double kNormalPWMRate = RobotMap.OFFLOADER_MAXSAFEPWM;
  private Spark mMotor;

  private OFFLOADER_STATE mState;

  public Offloader() {
    mMotor = new Spark(kOffloaderPWMSlot);
    mState = OFFLOADER_STATE.UNKNOWN;
    mMotor.setSafetyEnabled(false);

    LiveWindow.addActuator("OFFLOADER", "MOTOR", mMotor);
  }

  private double limit(double speed) {
    if (speed > kNormalPWMRate) {
      speed = kNormalPWMRate;
    } else if (speed < -kNormalPWMRate) {
      speed = -kNormalPWMRate;
    }
    if (mState == OFFLOADER_STATE.ROLLED && speed > 0) {
      speed = 0; // Change equality sign when +PWM direction is determined.
    } else if (mState == OFFLOADER_STATE.UNROLLED && speed < 0) {
      speed = 0;
    }
    return speed;
  }

  /**
   * Sets the motor speed of the offloader.
   * 
   * @param speed The PWM Rate
   */
  public void set(double speed) {
    mMotor.setSpeed(limit(speed));
  }

  /**
   * Stops the offloader motor.
   */
  public void stop() {
    mMotor.setSpeed(0.0);
  }

  public OFFLOADER_STATE getState() {
    return mState;
  }

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new TEMPFabric());
  }

}

