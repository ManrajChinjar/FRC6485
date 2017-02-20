package org.usfirst.frc.team6485.robot;

import org.usfirst.frc.team6485.robot.RobotMap.BRIDGE_STATE;
import org.usfirst.frc.team6485.robot.autonomous.TC_A_Auto;
import org.usfirst.frc.team6485.robot.autonomous.TC_A_Auto2;
import org.usfirst.frc.team6485.robot.commands.AutoDrive;
import org.usfirst.frc.team6485.robot.commands.AutoGyroTurn;
import org.usfirst.frc.team6485.robot.commands.BridgeControl;
import org.usfirst.frc.team6485.robot.commands.IntakePowerRamp;
import org.usfirst.frc.team6485.robot.commands.IntakeRampReversal;
import org.usfirst.frc.team6485.robot.commands.KillScheduler;
import org.usfirst.frc.team6485.robot.commands.StopBridgeManually;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands
 * and command groups that allow control of the robot.
 * 
 * @author Kyle Saburao
 */
public class OI {

  // LOGITECH EXTREME 3D PRO CONTROLS

  public final Joystick logitechController = new Joystick(RobotMap.LOGITECH_PORT);

  // public final JoystickButton L1 = new JoystickButton(logitechController, 1);
  // public final JoystickButton L2 = new JoystickButton(logitechController, 2);
  // public final JoystickButton L3 = new JoystickButton(logitechController, 3);
  public final JoystickButton L4 = new JoystickButton(logitechController, 4);
  public final JoystickButton L5 = new JoystickButton(logitechController, 5);
  public final JoystickButton L6 = new JoystickButton(logitechController, 6);
  public final JoystickButton L7 = new JoystickButton(logitechController, 7);
  public final JoystickButton L8 = new JoystickButton(logitechController, 8);
  // public final JoystickButton L9 = new JoystickButton(logitechController, 9);
  // public final JoystickButton L10 = new JoystickButton(logitechController, 10);
  public final JoystickButton L11 = new JoystickButton(logitechController, 11);
  public final JoystickButton L12 = new JoystickButton(logitechController, 12);

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
   * Return a double with range (0 - 1) on the slider scale with 0 with full drop and 1 as full
   * raise.<br>
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

  public int getLPOV() {
    return logitechController.getPOV();
  }

  public int getLPOV(int pov) {
    return logitechController.getPOV(pov);
  }

  // XBOX CONTROLS

  public Joystick xboxController = new Joystick(RobotMap.XBOX_PORT);
  public final JoystickButton X1 = new JoystickButton(xboxController, 1);
  public final JoystickButton X2 = new JoystickButton(xboxController, 2);
  public final JoystickButton X3 = new JoystickButton(xboxController, 3);
  public final JoystickButton X4 = new JoystickButton(xboxController, 4);
  public final JoystickButton X5 = new JoystickButton(xboxController, 5);
  public final JoystickButton X6 = new JoystickButton(xboxController, 6);

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
    // SmartDashboard buttons
    SmartDashboard.putData("Auto Drive Test", new AutoDrive(0.75, 3.0));
    SmartDashboard.putData("TestAuto", new TC_A_Auto());
    SmartDashboard.putData("TestAuto2", new TC_A_Auto2());
    SmartDashboard.putData("Stop Bridge", new StopBridgeManually());

    // Logitech controller buttons
    // L4.whenPressed(new IntakePowerRamp(0.0));

    // L5.whenPressed(new IntakeRampReversal());

    // L6.whenPressed(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMALPWM));

    L11.whenPressed(new AutoGyroTurn(-90.0));

    L12.whenPressed(new AutoGyroTurn(90.0));

    // XBOX controller buttons
    X1.whenPressed(new IntakeRampReversal());
    X2.whenPressed(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMALPWM));
    X3.whenPressed(new BridgeControl(BRIDGE_STATE.LOWERED));
    X4.whenPressed(new BridgeControl(BRIDGE_STATE.RAISED));
    X5.whenPressed(new StopBridgeManually());
    X6.whenPressed(new IntakePowerRamp(0.0));
  }

}
