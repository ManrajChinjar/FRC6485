package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.commands.StickDriver;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Drive Train subsystem.<br>
 * <br>
 * Forwards: 1, Back: -1<br>
 * Left: 1, Right: -1
 * 
 * @author Kyle Saburao
 */
public class DriveTrain extends Subsystem {

  private Spark mFrontLeftMotor;
  private Spark mRearLeftMotor;
  private Spark mFrontRightMotor;
  private Spark mRearRightMotor;

  // private final Encoder mDriveEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
  // Signal A to DIO-0 S, Signal B to DIO-1 S, GND to DIO-0 SYMBOL THING, 5V to DIO-0 V, DETERMINE
  // BOOLEAN STATE

  private RobotDrive engine;

  private ADXRS450_Gyro gyroscope;

  private final double kDriveTrainPWMMagnitudeLimit = RobotMap.DRIVETRAIN_PWM_LIMIT;

  // Initialize drive train
  public DriveTrain() {

    mFrontLeftMotor = new Spark(RobotMap.FRONT_LEFT_MOTOR);
    mRearLeftMotor = new Spark(RobotMap.REAR_LEFT_MOTOR);
    mFrontRightMotor = new Spark(RobotMap.FRONT_RIGHT_MOTOR);
    mRearRightMotor = new Spark(RobotMap.REAR_RIGHT_MOTOR);
    engine = new RobotDrive(mFrontLeftMotor, mRearLeftMotor, mFrontRightMotor, mRearRightMotor);

    gyroscope = new ADXRS450_Gyro();

    engine.setSafetyEnabled(true);
    engine.setExpiration(0.125);
    engine.setMaxOutput(1.00);
    engine.setSensitivity(1.00);

    LiveWindow.addActuator("DRIVETRAIN", "FL", mFrontLeftMotor);
    LiveWindow.addActuator("DRIVETRAIN", "RL", mRearRightMotor);
    LiveWindow.addActuator("DRIVETRAIN", "FR", mFrontRightMotor);
    LiveWindow.addActuator("DRIVETRAIN", "RR", mRearRightMotor);
  }

  @Override
  public void initDefaultCommand() {
    // Default command is operator control over drive system
    setDefaultCommand(new StickDriver());
  }

  private double fixArgument(double num) {
    if (num > kDriveTrainPWMMagnitudeLimit)
      num = kDriveTrainPWMMagnitudeLimit;
    else if (num < -kDriveTrainPWMMagnitudeLimit)
      num = -kDriveTrainPWMMagnitudeLimit;
    return num;
  }

  /**
   * Standard tank drive controls.<br>
   * Bad arguments outside of [-.95, .95] are truncated down to the limits. No scaling is preserved.
   * 
   * @param leftStick Left Motor Group request
   * @param rightStick Right Motor Group request
   */
  public void tankDrive(double leftStick, double rightStick) {
    engine.tankDrive(fixArgument(leftStick), fixArgument(rightStick));
  }

  /**
   * Standard arcade drive controls.<br>
   * Bad arguments outside of [-.95, .95] are truncated down to the limits. No scaling is preserved.
   * 
   * @param leftStick Forward and backwards request
   * @param rightStick Turning request
   */
  public void arcadeDrive(double leftStick, double rightStick) {
    engine.arcadeDrive(fixArgument(leftStick), fixArgument(rightStick));
  }

  public void drive(double speed, double curve) {
    engine.drive(speed, curve);
  }

  /**
   * Only allows the drive train to drive back and forth. <br>
   * <b>WARNING:</b> MAY BE DEPRECATED WHEN THE GYROSCOPE cPT METHOD IS IMPROVED.
   * 
   * @param speed Obvious (1 full forward, -1 full backwards)
   */
  public void forwardBackDrive(double speed) {
    tankDrive(speed, speed);
  }

  /**
   * Only allows the drive train to conduct a point turn.
   * 
   * @param turnrate 1: Full Rotate Left, -1: Full Rotate Right
   */
  public void turnOnSpot(double turnrate) {
    tankDrive(-turnrate, turnrate);
  }

  /**
   * Brings the drive train to a full halt.
   */
  public void stop() {
    tankDrive(0, 0);
  }

  /**
   * Requests the drive train to go full-ahead at +95% PWM.
   */
  public void flankSpeed() {
    tankDrive(0.95, 0.95);
  }

  /**
   * @return The gyroscope of the drive train.
   */
  public ADXRS450_Gyro getGyro() {
    return gyroscope;
  }

  public double getMotorPWM(int index) {
    switch (index) {
      case 0:
        return mFrontLeftMotor.getSpeed();
      case 1:
        return mRearLeftMotor.getSpeed();
      case 2:
        return mFrontRightMotor.getSpeed();
      case 3:
        return mRearRightMotor.getSpeed();

      // Required piece that does nothing at all.
      default:
        return mFrontLeftMotor.getSpeed();
    }
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

}
