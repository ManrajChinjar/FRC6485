package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.commands.StickDriver;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveTrain extends Subsystem {

    private Spark mFrontLeftMotor = new Spark(RobotMap.kFrontLeftMotor);
    private Spark mRearLeftMotor = new Spark(RobotMap.kRearLeftMotor);
    private Spark mFrontRightMotor = new Spark(RobotMap.kFrontRightMotor);
    private Spark mRearRightMotor = new Spark(RobotMap.kRearRightMotor);
    private RobotDrive engine;

    private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    private double mKpGyro = 0.025;


    // Initialize drive train
    public DriveTrain() {

	engine = new RobotDrive(
        		mFrontLeftMotor,
        		mRearLeftMotor, 
        		mFrontRightMotor, 
        		mRearRightMotor
		);

	engine.setSafetyEnabled(true);
	engine.setExpiration(0.15);
	engine.setMaxOutput(1);
	engine.setSensitivity(0.6);

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
	engine.tankDrive(leftStick, rightStick);
    }


    public void arcadeDrive(double leftStick, double rightStick) {
	leftStick = mFixArgument(leftStick);
	rightStick = mFixArgument(rightStick);
	engine.arcadeDrive(leftStick, rightStick);
    }


    public void zAxisDrive(double speed) {
	gyro.reset();
	engine.drive(speed, -gyro.getAngle()*mKpGyro); // CHANGE THE SECOND ARGUMENT TO GYRO PID
    }


    // Stop the robot's drive motors
    public void stop() {
	engine.tankDrive(0, 0);
    }


    public void flankSpeed() {
	engine.tankDrive(0.90, 0.90);
    }

}

