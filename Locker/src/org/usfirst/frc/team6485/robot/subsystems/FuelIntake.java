package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Fuel Intake subsystem.
 * <br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class FuelIntake extends Subsystem {
    
    private VictorSP roller = new VictorSP(RobotMap.FUEL_INTAKE_MOTOR);
    private double mReq;
    private double kSpeedNormal= 1.00;
    
    public enum State {
	IN, HALT, EXFIL
    }
    private State state;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    /**
     * Internal method to set the State enum of the motor controller.
     */
    private void setDirectionalState() {
	double speed = roller.getSpeed();
	if (speed == 0) {
	    state = State.HALT;
	}
	else {
	    if (speed > 0) {
		state = State.IN;
	    }
	    else if (speed < 0) {
		state = State.EXFIL;
	    }
	}
    }
    
    public FuelIntake() {
	// Run all the time
	roller.setSafetyEnabled(false);
	roller.setSpeed(0);
	setDirectionalState();
    }

    /**
     * Sets the PWM parameter of the intake motor according to the standard PWM range.
     * @param speed double [-1.00, 1.00]
     */
    public void setSpeed(double speed) {
	if (speed > 1) mReq = 1.00;
	else if (speed < -1) mReq = -1.00;
	roller.setSpeed(mReq);
	setDirectionalState();
    }
    
    /**
     * Starts the intake motor.
     */
    public void start() {
	roller.setSpeed(kSpeedNormal);
	setDirectionalState();
    }
    
    /**
     * Stops the intake motor.
     */
    public void stop() {
	roller.setSpeed(0);
	setDirectionalState();
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
    public State getDirectionalState() {
	return state;
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
}
