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

  // Constants sheet

  public static final double INTAKEPOWERRAMP_TIME_SECONDS = 1.0 / 2.0;

  public static final double FUELINTAKE_NORMAL_PWM = -0.85;

  public static final double AUTODRIVE_GYRO_KP = 0.095;

  public static final double DRIVETRAIN_PWM_UPPER_LIMIT = 0.95, DRIVETRAIN_PWM_LOWER_LIMIT = -0.95;

  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

}
