package org.usfirst.frc.team6485.robot.subsystems;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.commands.StickDriver;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
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

  private Spark mFrontLeftController, mRearLeftController, mFrontRightController,
      mRearRightController;
  private RobotDrive mEngine;

  private final Encoder mDriveEncoder;
  private ADXRS450_Gyro mDriveGyroscope;

  private final double kDriveTrainPWMMagnitudeLimit = RobotMap.DRIVETRAIN_PWMLIMIT;

  // Initialize drive train
  public DriveTrain() {

    mFrontLeftController = new Spark(RobotMap.FRONT_LEFT_MOTOR);
    mRearLeftController = new Spark(RobotMap.REAR_LEFT_MOTOR);
    mFrontRightController = new Spark(RobotMap.FRONT_RIGHT_MOTOR);
    mRearRightController = new Spark(RobotMap.REAR_RIGHT_MOTOR);

    mEngine = new RobotDrive(mFrontLeftController, mRearLeftController, mFrontRightController,
        mRearRightController);
    mDriveEncoder = new Encoder(0, 1, false, EncodingType.k4X);

    mDriveGyroscope = new ADXRS450_Gyro();

    mEngine.setSafetyEnabled(true);
    mEngine.setExpiration(1.00);
    mEngine.setMaxOutput(1.00);
    mEngine.setSensitivity(1.00);

    // mDriveEncoder.setDistancePerPulse(RobotMap.DRIVETRAIN_WHEELCIRCUMFERENCEMETRES / 1440.0);
    mDriveEncoder.setDistancePerPulse(1.0 / 1440.0);
    mDriveEncoder.setSamplesToAverage(6);
    mDriveEncoder.setMaxPeriod(0.150);

    LiveWindow.addActuator("DRIVETRAIN", "FL", mFrontLeftController);
    LiveWindow.addActuator("DRIVETRAIN", "RL", mRearRightController);
    LiveWindow.addActuator("DRIVETRAIN", "FR", mFrontRightController);
    LiveWindow.addActuator("DRIVETRAIN", "RR", mRearRightController);
    LiveWindow.addSensor("DRIVETRAIN", "ENCODER", mDriveEncoder);
    LiveWindow.addSensor("DRIVETRAIN", "GYROSCOPE", mDriveGyroscope);
  }

  private double limit(double num) {
    if (num > kDriveTrainPWMMagnitudeLimit) {
      num = kDriveTrainPWMMagnitudeLimit;
    } else if (num < -kDriveTrainPWMMagnitudeLimit) {
      num = -kDriveTrainPWMMagnitudeLimit;
    }
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
    mEngine.tankDrive(limit(leftStick), limit(rightStick));
  }

  /**
   * Standard arcade drive controls.<br>
   * Bad arguments outside of [-.95, .95] are truncated down to the limits. No scaling is preserved.
   * 
   * @param leftStick Forward and backwards request
   * @param rightStick Turning request
   */
  public void arcadeDrive(double leftStick, double rightStick) {
    mEngine.arcadeDrive(limit(leftStick), limit(rightStick));
  }

  public void drive(double speed, double curve) {
    mEngine.drive(speed, curve);
  }

  /**
   * Only allows the drive train to drive back and forth. <br>
   * 
   * @param speed Obvious (1 full forward, -1 full backwards)
   */
  @Deprecated
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
  @Deprecated
  public void flankSpeed() {
    tankDrive(0.95, 0.95);
  }

  /**
   * @return The gyroscope of the drive train.
   */
  public ADXRS450_Gyro getGyro() {
    return mDriveGyroscope;
  }

  /**
   * 
   * @param index The PWM index of the motor.
   * @return The motor PWM Rate of motor[i]
   */
  public double getMotorPWM(int index) {
    switch (index) {
      case 0:
        return mFrontLeftController.getSpeed();
      case 1:
        return mRearLeftController.getSpeed();
      case 2:
        return mFrontRightController.getSpeed();
      case 3:
        return mRearRightController.getSpeed();

      // Required piece that does nothing at all.
      default:
        return mFrontLeftController.getSpeed();
    }
  }

  /**
   * 
   * @return All of the motors PWM values as an array of 4 floats.
   */
  public double[] getMotorPWMS() {
    double[] motorArray = new double[4];

    motorArray[0] = mFrontLeftController.getSpeed();
    motorArray[1] = mRearLeftController.getSpeed();
    motorArray[2] = mFrontRightController.getSpeed();
    motorArray[3] = mRearRightController.getSpeed();

    return motorArray;
  }

  public Encoder getEncoder() {
    return mDriveEncoder;
  }

  @Override
  public void initDefaultCommand() {
    // Default command is operator control over drive system
    setDefaultCommand(new StickDriver());
  }

}
