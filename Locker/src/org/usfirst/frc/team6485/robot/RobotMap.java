package org.usfirst.frc.team6485.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into to a variable name.
 * This provides flexibility changing wiring, makes checking the wiring easier and significantly
 * reduces the number of magic numbers floating around.<br>
 * 
 * @author Kyle Saburao
 */
public class RobotMap {

  public static final int FRONT_LEFT_MOTOR = 0, REAR_LEFT_MOTOR = 1, FRONT_RIGHT_MOTOR = 2,
      REAR_RIGHT_MOTOR = 3;

  public static final int FUEL_INTAKE_MOTOR = 4;

  public static final int LOGITECH_PORT = 0, XBOX_PORT = 1;

  public static final int BRIDGE_MOTOR = 5;

  public static final int BRIDGE_LOWER_LIMIT_SWITCH = 0, BRIDGE_UPPER_LIMIT_SWITCH = 1;

  // Constants sheet

  public static final double FUELINTAKE_NORMAL_PWM = -0.88; // Negative is intake

  public static final double INTAKEPOWERRAMP_TIME_SECONDS = 1.0 / 3.0;

  public static final double DRIVETRAIN_PWM_LIMIT = 0.95;

  public static final double AUTODRIVE_GYRO_KP = 0.080;

  public static final double AUTODRIVE_RAMP_PERIOD_SECONDS = 0.10;

  public static final double BRIDGE_NORMAL_PWM = 0.09; // Positive raises the bridge

  public static final double BRIDGE_MAX_SAFE_PWM = 0.10;

  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

}
