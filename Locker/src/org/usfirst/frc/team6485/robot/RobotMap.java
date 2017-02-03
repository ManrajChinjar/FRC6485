package org.usfirst.frc.team6485.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * <br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class RobotMap {

    public static final int FrontLeftMotor = 0,
	    RearLeftMotor = 1,
	    FrontRightMotor = 2,
	    RearRightMotor = 3;

    public static final int FuelIntakeMotor = 4;

    public static final int LogitechPort = 0,
	    XBOXPort = 1;

    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;

}
