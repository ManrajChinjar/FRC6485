package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.commands.LowerBridge;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Bridge extends Subsystem {

  private DigitalInput mLowerLimitSwitch;
  private DigitalInput mUpperLimitSwitch;
  private VictorSP mBridgeMotor;

  private final double kMotorSpeed = RobotMap.BRIDGE_NORMAL_PWM;
  private final double kMaxSpeedMagnitude = RobotMap.BRIDGE_MAX_SAFE_PWM;

  public enum BRIDGE_STATE {
    LOWERED, RAISED, UNKNOWN
  }

  private BRIDGE_STATE state;

  public Bridge() {
    mLowerLimitSwitch = new DigitalInput(RobotMap.BRIDGE_LOWER_LIMIT_SWITCH);
    mUpperLimitSwitch = new DigitalInput(RobotMap.BRIDGE_UPPER_LIMIT_SWITCH);
    mBridgeMotor = new VictorSP(RobotMap.BRIDGE_MOTOR);
    mBridgeMotor.setSafetyEnabled(false);

    LiveWindow.addActuator("BRIDGE", "MOTOR", mBridgeMotor);
    new LowerBridge().start();
  }

  private double mLimitSpeed(double speed) {
    if (speed > kMaxSpeedMagnitude)
      speed = kMaxSpeedMagnitude;
    else if (speed < -kMaxSpeedMagnitude)
      speed = -kMaxSpeedMagnitude;
    return speed;
  }

  /**
   * @return Returns true if the upper bridge limit switch is pressed.
   */
  public boolean getUpperSwitch() {
    return mUpperLimitSwitch.get();
  }

  /**
   * @return Returns true if the lower bridge limit switch is pressed.
   */
  public boolean getLowerSwitch() {
    return mLowerLimitSwitch.get();
  }

  /**
   * @param speed Set the PWM rate of the bridge motor.
   */
  private void setMotor(double speed) {
    mBridgeMotor.setSpeed(mLimitSpeed(speed));
  }

  /**
   * Stops the bridge motor.
   */
  public void stop() {
    setMotor(0.0);
  }

  /**
   * Set the bridge motor to raise the bridge.
   */
  public void setRaise() {
    mBridgeMotor.setSpeed(kMotorSpeed);
  }

  /**
   * Set the bridge motor to lower the bridge.
   */
  public void setLower() {
    mBridgeMotor.setSpeed(-kMotorSpeed);
  }

  /**
   * @return Returns true if the bridge motor is moving.
   */
  public boolean isMoving() {
    return mBridgeMotor.getSpeed() != 0.0;
  }

  /**
   * Update the bridge state.
   */
  public void updateState() {
    if (mLowerLimitSwitch.get() && !mUpperLimitSwitch.get())
      state = BRIDGE_STATE.LOWERED;
    else if (mUpperLimitSwitch.get() && !mLowerLimitSwitch.get())
      state = BRIDGE_STATE.RAISED;
    else
      state = BRIDGE_STATE.UNKNOWN;
  }

  /**
   * @return The state of the bridge.
   */
  public BRIDGE_STATE getState() {
    updateState();
    return state;
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {}
}

