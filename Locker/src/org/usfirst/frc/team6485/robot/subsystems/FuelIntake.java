package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Fuel Intake subsystem. <br>
 * Intake is positive PWM, evacuation is negative PWM.
 * 
 * @author Kyle Saburao
 */
public class FuelIntake extends Subsystem {

  private VictorSP roller = new VictorSP(RobotMap.FUEL_INTAKE_MOTOR);
  private double mReq;
  private final double kSpeedNormal = RobotMap.INTAKENORMALPWM;
  private boolean mReverse = false;

  public enum IntakeState {
    IN, HALT, EVACUATE
  }

  private IntakeState intakeState;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  /**
   * Internal method to set the State enum of the motor controller.
   */
  private void setDirectionalState() {
    double speed = roller.getSpeed();
    if (speed == 0.0) {
      intakeState = IntakeState.HALT;
    } else {
      if (speed > 0.0) {
        intakeState = IntakeState.IN;
      } else if (speed < 0.0) {
        intakeState = IntakeState.EVACUATE;
      }
    }
  }

  public FuelIntake() {
    // Run all the time without watchdog concern.
    roller.setSafetyEnabled(false);
    roller.setSpeed(0.0);
    setDirectionalState();
    roller.setInverted(true);
  }

  /**
   * Sets the PWM parameter of the intake motor according to the standard PWM range.
   * 
   * @param speed double [-1.00, 1.00]
   */
  public void setSpeed(double speed) {
    if (speed > 1.0)
      mReq = 1.00;
    else if (speed < -1.0)
      mReq = -1.00;
    roller.set(mReq);
    setDirectionalState();
  }

  /**
   * Reverses the magnitude of the intake motor PWM.
   */
  public void reverseMagnitude() {
    mReverse = !mReverse;
    setSpeed(-roller.getSpeed());
  }

  /**
   * Starts the intake motor to default intake mode.
   */
  public void start() {
    setSpeed(kSpeedNormal);
  }

  /**
   * Starts the intake motor to evacuation mode.
   */
  public void evacuate() {
    setSpeed(-kSpeedNormal);
  }

  /**
   * Stops the intake motor.
   */
  public void stop() {
    setSpeed(0.0);
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
    return intakeState;
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
