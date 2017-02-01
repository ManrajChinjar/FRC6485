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

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    
    public FuelIntake() {
	
	// Run all the time
	roller.setSafetyEnabled(false);
	roller.setSpeed(0.90);
	
    }

    
    public void setSpeed(double speed) {
	
	if (speed > 1) mActual = 1;
	else if (speed < -1) mActual = -1;
	
	roller.setSpeed(mActual);
	
    }
    
    
    public double getSpeed() {
	
	return roller.getSpeed();
	
    }
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

