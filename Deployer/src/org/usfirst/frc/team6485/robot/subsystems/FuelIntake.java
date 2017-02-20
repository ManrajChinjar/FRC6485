package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.RobotMap.IntakeState;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Fuel Intake subsystem. <br>
 * Intake is -PWM, evacuation is +PWM.
 * 
 * @author Kyle Saburao
 */
public class FuelIntake extends Subsystem {

  private final VictorSP roller = new VictorSP(RobotMap.FUEL_INTAKE_MOTOR);

  private final double kSpeedNormal = RobotMap.FUELINTAKE_NORMALPWM;
  private double mReq;
  private boolean mReverse = false;

  private IntakeState mIntakeState;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public FuelIntake() {
    roller.setSafetyEnabled(false);
    roller.setSpeed(0.0);
    setDirectionalState();
  }

  /**
   * Internal method to set the State enum of the motor controller.
   */
  private void setDirectionalState() {
    double speed = roller.getSpeed();
    if (speed == 0.0) {
      mIntakeState = IntakeState.HALT;
    } else {
      if (speed < 0.0) {
        mIntakeState = IntakeState.IN;
      } else if (speed > 0.0) {
        mIntakeState = IntakeState.EVACUATE;
      }
    }
  }

  /**
   * Sets the PWM parameter of the intake motor according to the standard PWM range.
   * 
   * @param speed double [-1.00, 1.00]
   */
  public void set(double speed) {
    mReq = speed;
    if (speed > 1.0) {
      mReq = 1.00;
    } else if (speed < -1.0) {
      mReq = -1.00;
    }

    roller.setSpeed(mReq);
    setDirectionalState();
  }

  /**
   * Reverses the magnitude of the intake motor PWM.
   */
  public void reverseMagnitude() {
    mReverse = !mReverse;
    set(-roller.getSpeed());
  }

  /**
   * Starts the intake motor to default intake mode.
   */
  public void start() {
    set(kSpeedNormal);
  }

  /**
   * Starts the intake motor to evacuation mode.
   */
  public void evacuate() {
    set(-kSpeedNormal);
  }

  /**
   * Stops the intake motor.
   */
  public void stop() {
    set(0.0);
  }

  /**
   * @return The PWM rate of the intake motor.
   */
  public double getSpeed() {
    return roller.getSpeed();
  }

  /**
   * @return The directional state of the fuel intake motor controller as a State enum.
   */
  public IntakeState getDirectionalState() {
    setDirectionalState();
    return mIntakeState;
  }

  /**
   * Set the intake motor's state using an IntakeState enum.
   * 
   * @param state An IntakeState enum declaring the desired intake mode.
   */
  public void set(IntakeState state) {
    switch (state) {
      case HALT:
        stop();
        break;
      case IN:
        start();
        break;
      case EVACUATE:
        evacuate();
        break;
    }
  }

  public double getNormalIntakeSpeed() {
    return kSpeedNormal;
  }

  @Override
  public void initDefaultCommand() {}
}
