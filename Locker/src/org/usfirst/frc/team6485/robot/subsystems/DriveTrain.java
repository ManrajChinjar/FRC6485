package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.commands.StickDriver;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

    private Spark frontLeft = new Spark(9);
    private Spark backLeft = new Spark(8);
    private Spark frontRight = new Spark(1);
    private Spark backRight = new Spark(0);
    private RobotDrive driver;
    
    // Determine gyro port
    //private AnalogGyro gyroscope = new AnalogGyro(0);
    

    // Initialize drive train
    public DriveTrain() {
    	
	    driver = new RobotDrive(
			frontLeft,
			backLeft, 
			frontRight, 
			backRight
			);
	    
	    driver.setSafetyEnabled(true);
	    driver.setExpiration(0.15);
	    driver.setMaxOutput(1);
	    driver.setSensitivity(0.6);
	   
	     //* FIGURE OUT WHICH MOTORS NEED TO RUN IN REVERSE
//	    driver.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
//	    driver.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
//	    driver.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
//	    driver.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
	    
	    
    }
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
	// COMMAND WILL BE COMMAND DRIVEWITHARCADE
	setDefaultCommand(new StickDriver());
    }
    
    
    public void tankDrive(double leftStick, double rightStick) {
    	driver.tankDrive(leftStick, rightStick);
    }
    

    public void arcadeDrive(double leftStick, double rightStick) {
    	driver.arcadeDrive(leftStick, rightStick);
    }
    
    
    // Stop the robot's drive motors
    public void stop() {
    	driver.tankDrive(0, 0);
    }
    
   
    public void flankSpeed() {
	driver.tankDrive(1, 1);
    }
    
}

