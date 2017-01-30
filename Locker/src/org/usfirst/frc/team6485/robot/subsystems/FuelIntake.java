package org.usfirst.frc.team6485.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FuelIntake extends Subsystem {
    
    private VictorSP Roller = new VictorSP(4);
    private double actual;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    
    public FuelIntake() {
	
	// Run all the time
	Roller.setSafetyEnabled(false);
	Roller.setSpeed(0.90);
	
    }

    
    public void setSpeed(double speed) {
	
	if (speed > 1) actual = 1;
	else if (speed < -1) actual = -1;
	
	Roller.setSpeed(actual);
	
    }
    
    
    public double getSpeed() {
	
	return Roller.getSpeed();
	
    }
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

