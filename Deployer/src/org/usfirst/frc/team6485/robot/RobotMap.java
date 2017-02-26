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

  public static final int BRIDGE_MOTOR = 5;

  public static final int OFFLOADER_MOTOR = 6;

  public static final int PDP_FL = 0, PDP_FR = 15, PDP_RL = 1, PDP_RR = 14;

  // FIX THESE
  public static final int PDP_BRIDGE = 11, PDP_INTAKE = 2, PDP_OFFLOADER = 6;

  public static final int LOGITECH_PORT = 0, XBOX_PORT = 1;

  // Constants sheet

  // 95% because full motor speed would saturate the motors and prevent normal turning via
  // increasing one side's motors.
  public static final double DRIVETRAIN_PWMLIMIT = 0.95;

  public static final double FUELINTAKE_NORMALPWM = -0.72; // Negative is intake

  public static final double BRIDGE_IDLECURRENTMAGNITUDE = 0.1;

  public static final double BRIDGE_NORMALPWM = 0.20; // Positive raises the bridge

  public static final double BRIDGE_RAISEPWM = 0.24, BRIDGE_LOWERPWM = -0.20,
      BRIDGE_MAXSAFEPWM = 0.26;

  public static final double OFFLOADER_MAXSAFEPWM = 1.00;

  public static final double OFFLOADER_IDLECURRENT = 1.375, OFFLOADER_MAXWORKINGCURRENT = 1.5;
  
  // TODO CHECK
  public static final double FUELINTAKE_NORMALRUNNINGCURRENT = 3.0, FUELINTAKE_STALLCURRENT = 9.0;

  public static final double DRIVETRAIN_WHEELCIRCUMFERENCEMETRES = Math.PI * 0.1524;

  public static final double INTAKEPOWERRAMP_TIMESECONDS = 0.40;

  public static final double DRIVEDISTANCE_MINIMUMALLOWABLEPWMMAGNITUDE = 0.37;

  public static final double DRIVEDISTANCE_RAMPINGDISTANCEMETRES = 0.30;

  public static final double AUTODRIVE_GYROKP = 0.07; // Try 0.06

  public static final double DRIVETIMED_RAMPPERIODSECONDS = 0.50;

  // ENUMS

  public enum IntakeState {
    IN, HALT, EVACUATE
  }

  public enum RUNNING_MODE {
    DISABLED, TELEOP, AUTO
  }

  public enum OFFLOADER_STATE {
    TAUT, FREE
  }

  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

}
