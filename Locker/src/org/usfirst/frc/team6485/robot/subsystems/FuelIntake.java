package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * <br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class FuelIntake extends Subsystem {
    
    private VictorSP roller = new VictorSP(RobotMap.FuelIntakeMotor);
    private double mActual;
    private double kSpeedNormal= 1.00;
    
    public boolean IntakeRunning;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public FuelIntake() {
	// Run all the time
	roller.setSafetyEnabled(false);
	roller.setSpeed(0);
	IntakeRunning = false;
    }
    
    public void switchDirection() {
	kSpeedNormal *= -1.00;
    }

    public void setSpeed(double speed) {
	if (speed > 1) mActual = 1;
	else if (speed < -1) mActual = -1;
	if (mActual != 0) IntakeRunning = true;
	else IntakeRunning = false;
	roller.setSpeed(mActual);
    }
    
    public void start() {
	IntakeRunning = true;
	roller.setSpeed(kSpeedNormal);
    }
    
    public void stop() {
	IntakeRunning = false;
	roller.setSpeed(0);
    }
    
    public double getSpeed() {
	return roller.getSpeed();
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
}

