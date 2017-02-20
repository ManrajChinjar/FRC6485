package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.RobotMap.OFFLOADER_STATE;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The ball offloader subsystem.
 * 
 * Using Talon SR for now, will change to spark. <br>
 * Be sure to correct wire colours for motor wires.
 * 
 * @author Kyle Saburao
 */
@SuppressWarnings("unused")
public class Offloader extends Subsystem {

  private final int kOffloaderPWMSlot = RobotMap.OFFLOADER_MOTOR;
  private final double kNormalPWMRate = RobotMap.OFFLOADER_MAXSAFEPWM;
  private Talon mMotor;
  // private Spark mMotor;

  private OFFLOADER_STATE mState;

  public Offloader() {
    mMotor = new Talon(kOffloaderPWMSlot);
    // mMotor = new Spark(kOffloaderPWMSlot);
    mState = OFFLOADER_STATE.UNKNOWN;
    mMotor.setSafetyEnabled(false);

    LiveWindow.addActuator("OFFLOADER", "MOTOR", mMotor);
    mMotor.stopMotor();
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

  private void updateState() {
    if (mMotor.getSpeed() > 0.0) {
      mState = OFFLOADER_STATE.ROLLING;
    } else if (mMotor.getSpeed() < 0.0) {
      mState = OFFLOADER_STATE.UNROLLING;
    } else {
      mState = OFFLOADER_STATE.UNKNOWN;
    }
  }

  public double getSpeed() {
    return mMotor.getSpeed();
  }

  /**
   * Sets the motor speed of the offloader.
   * 
   * @param speed The PWM Rate
   */
  public void set(double speed) {
    mMotor.setSpeed(limit(speed));
    updateState();
  }

  /**
   * Stops the offloader motor.
   */
  public void stop() {
    mMotor.setSpeed(0.0);
    updateState();
  }

  public OFFLOADER_STATE getState() {
    updateState();
    return mState;
  }

  @Override
  public void initDefaultCommand() {}

}

