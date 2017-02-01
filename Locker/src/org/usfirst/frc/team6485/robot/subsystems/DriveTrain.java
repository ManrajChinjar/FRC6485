package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.commands.StickDriver;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drive Train subsystem.<br>
 * <br>
 * Forwards: 1, Back: -1<br>
 * Left: 1, Right: -1<br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class DriveTrain extends Subsystem {

    private final Spark mFrontLeftMotor = new Spark(RobotMap.FrontLeftMotor);
    private final Spark mRearLeftMotor = new Spark(RobotMap.RearLeftMotor);
    private final Spark mFrontRightMotor = new Spark(RobotMap.FrontRightMotor);
    private final Spark mRearRightMotor = new Spark(RobotMap.RearRightMotor);
    private RobotDrive engine;

    private ADXRS450_Gyro gyroscope = new ADXRS450_Gyro();
    private final double mkPGyro = 0.017;
    private double mGyroStraightStartAngle,
    		mGyroCurrentAngle,
    		mCalculatedProportionalTurn;
    private boolean mGyroZSet;

    //	public double BaseAngle;
    //	public boolean GyroFlag;

    
    // Initialize drive train
    public DriveTrain() {

	engine = new RobotDrive(
		mFrontLeftMotor,
		mRearLeftMotor, 
		mFrontRightMotor, 
		mRearRightMotor
		);

	engine.setSafetyEnabled(true);
	engine.setExpiration(0.10);
	engine.setMaxOutput(1.00);
	engine.setSensitivity(1.00);

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
	} 
	else if (num < -0.95) {
	    num = -0.95;
	}
	return num;

    }


    /**
     * Standard tank drive controls.<br>
     * Bad arguments outside of [-1, 1] are truncated down to the limits. No scaling is preserved.
     * @param leftStick Left Motor Group request
     * @param rightStick Right Motor Group request
     */ 
    public void tankDrive(double leftStick, double rightStick) {

	leftStick = mFixArgument(leftStick);
	rightStick = mFixArgument(rightStick);
	engine.tankDrive(leftStick, rightStick);

    }


    /**
     * Standard arcade drive controls.<br>
     * Bad arguments outside of [-1, 1] are truncated down to the limits. No scaling is preserved.
     * @param leftStick Forward and backwards request
     * @param rightStick Turning request
     */
    public void arcadeDrive(double leftStick, double rightStick) {

	leftStick = mFixArgument(leftStick);
	rightStick = mFixArgument(rightStick);
	engine.arcadeDrive(leftStick, rightStick);

    }


    /**
     * Only allows the drive train to drive back and forth. <br>
     * <b>WARNING:</b> MAY BE DEPRECATED WHEN THE GYROSCOPE cPT METHOD IS IMPROVED.
     * @param speed Obvious (1 full forward, -1 full backwards)
     */
    public void forwardBackDrive(double speed) {

	engine.tankDrive(speed, speed);

    }


    /**
     * <b>WORK IN PROGRESS</b><br>
     * <br>
     * The ADXRS450 gyroscope is used to determine any rotational drift and if so,<br>
     * correct the error according to an exponential graphing function.<br>
     * Rotational oscillation means that kP is too high, while not working 
     * at all means that it's too low.
     * <br><br>
     * The initial angle is only conserved when this method is continuously requested
     * by the StickDriver subsystem,<br>
     * therefore this only works with the aforementioned subsystem. (For now)
     * @param speed A double value within the range [-1, 1] back or forth
     */
    public void gyroStraightDrive(double speed) {

	if (!mGyroZSet) {
	    mGyroStraightStartAngle = gyroscope.getAngle();
	    mGyroZSet = true;
	}

	mGyroCurrentAngle = gyroscope.getAngle();

	mCalculatedProportionalTurn = -(mGyroCurrentAngle - mGyroStraightStartAngle) * mkPGyro;

	if (speed < 0)
	    mCalculatedProportionalTurn *= -1;

	// -(current - initial) * kP
	engine.drive(speed, mCalculatedProportionalTurn);

    }


    /**
     * Only allows the drive train to conduct a point turn through arcade controls.
     * @param turnrate Usually the x-axis analog request of the controller [-1, 1]
     */
    public void turnOnSpot(double turnrate) {

	engine.arcadeDrive(0, turnrate);

    }


    /**
     * Brings the drive train to a full halt.
     */
    public void stop() {

	engine.tankDrive(0, 0);

    }


    /**
     * Requests the drive train to go full-ahead at +95% PWM.
     */
    public void flankSpeed() {

	engine.tankDrive(0.95, 0.95);

    }


    /**
     * The preferred way to get the gyroscope angle is call Robot.drivetrain.getGyro().getAngle()
     * @return The gyroscope of the drive train.
     */
    public ADXRS450_Gyro getGyro() {

	return gyroscope;

    }

    /**
     * 
     * @return The current ADXRS450 gyroscope angle.
     */
    public double getGyroAngle() {

	return gyroscope.getAngle();

    }


    /**
     * @return The Calculated Proportional Turn (cPT) Value as a double.
     */

    public double getcPT() {

	return mCalculatedProportionalTurn;

    }


    /**
     * 
     * @return All of the motors PWM values as an array of 4 doubles.
     */
    public double[] getMotorPWMS() {

	double[] motorArray = new double[4];

	motorArray[0] = mFrontLeftMotor.getSpeed();
	motorArray[1] = mRearLeftMotor.getSpeed();
	motorArray[2] = mFrontRightMotor.getSpeed();
	motorArray[3] = mRearRightMotor.getSpeed();

	return motorArray;

    }


    /**
     * Sets the gyroZSet boolean in the DriveTrain subsystem according to the parameter.<br>
     * The variable is private to hide it from outside the subsystem 
     * and to avoid accidental breakage by users.
     * @param state true | false
     */
    public void setGyroZSet(boolean state) {

	mGyroZSet = state;

    }


    public void update() {



    }
}

