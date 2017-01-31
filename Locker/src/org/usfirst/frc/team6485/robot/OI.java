package org.usfirst.frc.team6485.robot;

import org.usfirst.frc.team6485.robot.commands.DriveSquareShape;
import org.usfirst.frc.team6485.robot.commands.GyroscopeTurn;
import org.usfirst.frc.team6485.robot.commands.TestCaseSpin;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * <br><br>
 * <i>Kyle Saburao 2017</i>
 */
public class OI {

    // LOGITECH EXTREME 3D PRO CONTROLS

    public Joystick logitechController = new Joystick(RobotMap.LogitechPort);
    Button mainTrigger = new JoystickButton(logitechController, 1);
    Button buttonLogitech9 = new JoystickButton(logitechController, 9);
    Button buttonLogitech10 = new JoystickButton(logitechController, 10); // Random unused button I picked for testing.
    Button buttonLogitech11 = new JoystickButton(logitechController, 11);
    Button buttonLogitech12 = new JoystickButton(logitechController, 12);


    /**
     * 
     * @return The x-axis of the Logitech joystick
     */
    public double getLJoyX() {

	return logitechController.getRawAxis(0);

    }


    /**
     * 
     * @return The y-axis of the Logitech joystick
     */
    public double getLJoyY() {

	return logitechController.getRawAxis(1);

    }


    public double getLRotation() {

	return logitechController.getRawAxis(2);

    }


    public double getLSlider() {

	return logitechController.getRawAxis(3);

    }


    /**
     * Return a double with range (0 - 1) on the slider scale
     * with 0 with full drop and 1 as full raise.<br>
     * The slider has a deadzone of 5% on either end.
     */
    public double getLSliderScale() {

	double scale = (-logitechController.getRawAxis(3) + 1) / 2;
	if (scale > 0.95)
	    scale = 1;
	if (scale < 0.05)
	    scale = 0;
	return scale;

    }


    // This is the main trigger
    public boolean getLMainTrigger() {

	return logitechController.getRawButton(1);

    }


    // Return press state of the passed button
    public boolean getLButtonPressed(int button) {

	return logitechController.getRawButton(button);

    }

    // XBOX CONTROLS

    public Joystick xboxController = new Joystick(RobotMap.XBOXPort);


    public double getXBOXLeftJoyX() {

	return xboxController.getRawAxis(0);

    }


    public double getXBOXLeftJoyY() {

	return xboxController.getRawAxis(1);

    }      	


    public double getXBOXRightJoyX() {

	return xboxController.getRawAxis(4);

    }


    public double getXBOXRightJoyY() {

	return xboxController.getRawAxis(5);

    }


    public boolean getXBOXSafety() {

	return xboxController.getRawButton(6);

    }


    public boolean getXBOXButtonPressed(int button) {

	return xboxController.getRawButton(button);

    }

    public OI() {

	new JoystickButton(logitechController, 9).whenPressed(new TestCaseSpin());
	new JoystickButton(logitechController, 10).whenPressed(new DriveSquareShape());
	new JoystickButton(logitechController, 11).whenPressed(new GyroscopeTurn(-90));
	new JoystickButton(logitechController, 12).whenPressed(new GyroscopeTurn(90));

    }

    // TRIGGER COMMMAND

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    //// joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
