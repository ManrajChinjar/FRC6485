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

	private Spark frontLeftMotor = new Spark(RobotMap.FrontLeftMotor);
	private Spark rearLeftMotor = new Spark(RobotMap.RearLeftMotor);
	private Spark frontRightMotor = new Spark(RobotMap.FrontRightMotor);
	private Spark rearRightMotor = new Spark(RobotMap.RearRightMotor);
	private RobotDrive engine;

	private ADXRS450_Gyro gyroscope = new ADXRS450_Gyro();
	private double kPGyro = 0.03;

	private boolean gtestrunning;
	
//	public double BaseAngle;
//	public boolean GyroFlag;

	// Initialize drive train
	public DriveTrain() {

		engine = new RobotDrive(
				frontLeftMotor,
				rearLeftMotor, 
				frontRightMotor, 
				rearRightMotor
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

		if (num > 0.95) {
			num = 0.95;
		} else if (num < -0.95) {
			num = -0.95;
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
		engine.tankDrive(speed, speed);
	}

	
	public void gyroTest(double speed, int time) {

	    if (gtestrunning) return;
	    gtestrunning = true;
	    double gyroStartAngle = gyroscope.getAngle();
	    double gyroCurrentAngle;
	    double cPT; // calculatedProportionalTurn
	    int targetCount = (time * 1000)/20; // Temporary as timers will be used instead
	    int currentCount = 0;
	    
	    while (currentCount <= targetCount) {
		gyroCurrentAngle = gyroscope.getAngle();

		cPT = -(gyroCurrentAngle - gyroStartAngle) * kPGyro;
		if (cPT > 1) cPT = 1;
		if (cPT < -1) cPT = -1;
	
		// -(current - initial) * kP
		engine.drive(speed, cPT); // TODO Might have to invert cPT
		currentCount++;
	    }
	    
	    engine.tankDrive(0, 0);
	    gtestrunning = false;

	}

	// Stop the robot's drive motors
	public void stop() {

		engine.tankDrive(0, 0);

	}


	public void flankSpeed() {

		engine.tankDrive(0.95, 0.95);

	}

}

