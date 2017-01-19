package org.usfirst.frc.team6485.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {


    public Joystick logitechController = new Joystick(RobotMap.LogitechPort);


    public double getLJoyX() {
	return logitechController.getRawAxis(0);
    }


    public double getLJoyY() {
	return logitechController.getRawAxis(1);
    }


    public double getLRotation() {
	return logitechController.getRawAxis(2);
    }


    public double getLSlider() {
	return logitechController.getRawAxis(3);
    }


    /*
     * Return a double with range (0 - 1) on the slider scale
     * with 0 with full drop and 1 as full raise.
     * 
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


        	public Joystick xboxController = new Joystick(1);
        	
        	
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
