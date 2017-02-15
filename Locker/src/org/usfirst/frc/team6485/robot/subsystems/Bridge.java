package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.commands.BridgeMaintainer;
import org.usfirst.frc.team6485.robot.commands.LowerBridge;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * @author Kyle Saburao
 */
public class Bridge extends Subsystem {

  private DigitalInput mLowerLimitSwitch;
  private DigitalInput mUpperLimitSwitch;
  private VictorSP mBridgeMotor;

  private final double kMotorSpeed = RobotMap.BRIDGE_NORMAL_PWM;
  private final double kMaxSpeedMagnitude = RobotMap.BRIDGE_MAX_SAFE_PWM;

  // UNKNOWN state is overloaded as it toggles the bridge in ToggleBridge
  public enum BRIDGE_STATE {
    LOWERED, LOWERING, RAISED, RAISING, UNKNOWN
  }

  private BRIDGE_STATE mState, mRequiredState;

  public Bridge() {
    mLowerLimitSwitch = new DigitalInput(RobotMap.BRIDGE_LOWER_LIMIT_SWITCH);
    mUpperLimitSwitch = new DigitalInput(RobotMap.BRIDGE_UPPER_LIMIT_SWITCH);
    mBridgeMotor = new VictorSP(RobotMap.BRIDGE_MOTOR);
    mBridgeMotor.setSafetyEnabled(false);

    LiveWindow.addActuator("BRIDGE", "MOTOR", mBridgeMotor);
    new LowerBridge().start();
  }

  /**
   * Limits the bridge motor PWM magnitude to 14% as that is the fastest safe speed.
   * 
   * @param speed The requested bridge motor PWM rate.
   * @return The safe bridge motor PWM rate.
   */
  private double mLimitSpeed(double speed) {
    String warning = "";

    if (speed > kMaxSpeedMagnitude) {
      warning = String.format("WARNING: Bridge speed request is %.3f higher than safe maximum.",
          speed - kMaxSpeedMagnitude);
      speed = kMaxSpeedMagnitude;
    } else if (speed < -kMaxSpeedMagnitude) {
      warning = String.format("WARNING: Bridge speed request is %.3f lower than safe maximum.",
          speed - -kMaxSpeedMagnitude);
      speed = -kMaxSpeedMagnitude;
    }

    if (warning != "")
      System.out.println(warning);
    return speed;
  }

  /**
   * @param state The required state of the bridge.
   */
  private void setRequiredState(BRIDGE_STATE state) {
    mRequiredState = state;
  }

  /**
   * @return The required state of the bridge.
   */
  public BRIDGE_STATE getRequiredState() {
    if (mRequiredState != null)
      return mRequiredState;
    else
      return BRIDGE_STATE.UNKNOWN;
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
  public void setMotor(double speed) {
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
    setRequiredState(BRIDGE_STATE.RAISED);
  }

  /**
   * Set the bridge motor to lower the bridge.
   */
  public void setLower() {
    mBridgeMotor.setSpeed(-kMotorSpeed);
    setRequiredState(BRIDGE_STATE.LOWERED);
  }

  /**
   * @return Returns true if the bridge motor is moving.
   */
  public boolean isMoving() {
    return mBridgeMotor.getSpeed() != 0.0;
  }

  /**
   * @return The PWM rate of the bridge motor.
   */
  private double getSpeed() {
    return mBridgeMotor.getSpeed();
  }

  /**
   * Update the bridge state.
   */
  public void updateState() {
    if (mLowerLimitSwitch.get() && !mUpperLimitSwitch.get())
      mState = BRIDGE_STATE.LOWERED;
    else if (mUpperLimitSwitch.get() && !mLowerLimitSwitch.get())
      mState = BRIDGE_STATE.RAISED;
    else if (!mUpperLimitSwitch.get() && !mLowerLimitSwitch.get() && isMoving()) {
      if (getSpeed() > 0.0)
        mState = BRIDGE_STATE.RAISING;
      else if (getSpeed() < 0.0)
        mState = BRIDGE_STATE.LOWERING;
    } else
      mState = BRIDGE_STATE.UNKNOWN;

  }

  /**
   * @return The state of the bridge.
   */
  public BRIDGE_STATE getState() {
    updateState();
    return mState;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new BridgeMaintainer());
  }

}
