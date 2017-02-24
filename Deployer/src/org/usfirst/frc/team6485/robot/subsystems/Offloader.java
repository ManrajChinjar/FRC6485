package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.RobotMap.OFFLOADER_STATE;
import org.usfirst.frc.team6485.robot.commands.OffloaderDriver;
import org.usfirst.frc.team6485.robot.utility.PowerDistributionPanelReporter;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The ball offloader subsystem.<br>
 * Negative PWM rolls the vinyl.
 * 
 * @author Kyle Saburao
 */
public class Offloader extends Subsystem {

  private final int kOffloaderPWMSlot = RobotMap.OFFLOADER_MOTOR;
  private final double kNormalPWMRate = RobotMap.OFFLOADER_MAXSAFEPWM;
  private Spark mMotor;

  private OFFLOADER_STATE mState;

  public Offloader() {
    mMotor = new Spark(kOffloaderPWMSlot);
    mState = OFFLOADER_STATE.FREE;
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
    if (mState == OFFLOADER_STATE.TAUT && speed > 0.0) {
      speed = 0.0;
    }
    return speed;
  }

  private void updateState() {
    if (Math.abs(RobotMap.OFFLOADER_MAXWORKINGCURRENT - getCurrent()) > 0.75) {
      mState = OFFLOADER_STATE.TAUT;
    } else {
      mState = OFFLOADER_STATE.FREE;
    }
  }

  public double getSpeed() {
    return mMotor.getSpeed();
  }
  
  /**
   * @return The current of the offloader motor in amperes.
   */
  public double getCurrent() {
    return PowerDistributionPanelReporter.getChannelCurrent(RobotMap.PDP_OFFLOADER);
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
  public void initDefaultCommand() {
    setDefaultCommand(new OffloaderDriver());
  }

}

