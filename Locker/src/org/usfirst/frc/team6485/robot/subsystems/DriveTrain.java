package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
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

    private Spark frontLeft = new Spark(RobotMap.kFrontLeftMotor);
    private Spark backLeft = new Spark(RobotMap.kBackLeftMotor);
    private Spark frontRight = new Spark(RobotMap.kFrontRightMotor);
    private Spark backRight = new Spark(RobotMap.kBackRightMotor);
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
	
	// Default command is operator control over drive system
	setDefaultCommand(new StickDriver());
    }
    
    private static double mFixArgument(double num) {
	if (num > 1.00) {
	    num = 1;
	} else if (num < -1.00) {
	    num = -1;
	}
	return num;
    }
    
    public void tankDrive(double leftStick, double rightStick) {
	leftStick = mFixArgument(leftStick);
	rightStick = mFixArgument(rightStick);
    	driver.tankDrive(leftStick, rightStick);
    }
    

    public void arcadeDrive(double leftStick, double rightStick) {
	leftStick = mFixArgument(leftStick);
	rightStick = mFixArgument(rightStick);
    	driver.arcadeDrive(leftStick, rightStick);
    }
    
    
    public void zAxisDrive(double speed) {
	speed = mFixArgument(speed);;
	driver.tankDrive(speed, speed); // CHANGE THE SECOND ARGUMENT TO GYRO PID
    }
    
    
    // Stop the robot's drive motors
    public void stop() {
    	driver.tankDrive(0, 0);
    }
    
   
    public void flankSpeed() {
	driver.tankDrive(1, 1);
    }
    
}

