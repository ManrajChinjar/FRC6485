package org.usfirst.frc.team6485.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team6485.robot.commands.ExampleCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    	
    public Joystick logitechController = new Joystick(0);


    public double getJoyX() {
	return logitechController.getRawAxis(0);
    }


    public double getJoyY() {
	return logitechController.getRawAxis(1);
    }


    public double getInvertedY() {
	return -logitechController.getRawAxis(1);
    }


    public double getRotation() {
	return logitechController.getRawAxis(2);
    }

    
    public double getSlider() {
	return logitechController.getRawAxis(3);
    }
    
    
    /*
     * Return a double with range (0 - 1) on the slider scale
     * with 0 with full drop and 1 as full raise.
     * 
     * The slider has a deadzone of 5% on either end.
     */
    public double getSliderScale() {
	double scale = (-Robot.oi.getSlider() + 1) / 2;
	if (scale > 0.95) scale = 1;
	if (scale < 0.05) scale = 0;
	return scale;
    }
    

    // This is the main trigger
    public boolean getMainTrigger() {
	return logitechController.getRawButton(0);
    }

    
    // Whatever button this is
    public boolean getButtonOne() {
	return logitechController.getRawButton(1);
    }
		
		
//    	public Joystick xboxController = new Joystick(0);
//    	
//    	public double getLeftJoyX() {
//    		return xboxController.getRawAxis(0);
//    	}
//    	
//    	public double getLeftJoyY() {
//    		return xboxController.getRawAxis(1);
//    	}
//    	
//    	public double getRightJoyX() {
//    		return xboxController.getRawAxis(4);
//    	}
//    	
//    	public double getRightJoyY() {
//    		return xboxController.getRawAxis(5);
//    	}
//    	
//    	public Joystick getJoy() {
//    		return xboxController;
//    	}
    	
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
