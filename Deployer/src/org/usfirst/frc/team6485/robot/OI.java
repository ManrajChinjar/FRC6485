package org.usfirst.frc.team6485.robot;

import org.usfirst.frc.team6485.robot.autonomous.TC_A_2MetreBox;
import org.usfirst.frc.team6485.robot.commands.BridgeAutoMove;
import org.usfirst.frc.team6485.robot.commands.DriveDistance;
import org.usfirst.frc.team6485.robot.commands.DriveTrainAutoTurn;
import org.usfirst.frc.team6485.robot.commands.IntakePowerRamp;
import org.usfirst.frc.team6485.robot.commands.IntakeRampReversal;
import org.usfirst.frc.team6485.robot.utility.DualButton;
import org.usfirst.frc.team6485.robot.utility.Logitech3DPro;
import org.usfirst.frc.team6485.robot.utility.XBOX;

import edu.wpi.first.wpilibj.DriverStation;
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

  private final Logitech3DPro LOGITECHCONTROLLER = new Logitech3DPro(RobotMap.LOGITECH_PORT);

  public final JoystickButton L1 = new JoystickButton(LOGITECHCONTROLLER, 1);
  public final JoystickButton L2 = new JoystickButton(LOGITECHCONTROLLER, 2);
  public final JoystickButton L3 = new JoystickButton(LOGITECHCONTROLLER, 3);
  public final JoystickButton L4 = new JoystickButton(LOGITECHCONTROLLER, 4);
  public final JoystickButton L5 = new JoystickButton(LOGITECHCONTROLLER, 5);
  public final JoystickButton L6 = new JoystickButton(LOGITECHCONTROLLER, 6);
  public final JoystickButton L7 = new JoystickButton(LOGITECHCONTROLLER, 7);
  public final JoystickButton L8 = new JoystickButton(LOGITECHCONTROLLER, 8);
  public final JoystickButton L9 = new JoystickButton(LOGITECHCONTROLLER, 9);
  public final JoystickButton L10 = new JoystickButton(LOGITECHCONTROLLER, 10);
  public final JoystickButton L11 = new JoystickButton(LOGITECHCONTROLLER, 11);
  public final JoystickButton L12 = new JoystickButton(LOGITECHCONTROLLER, 12);
  public final DualButton L1_L11 = new DualButton(L1, L11);
  public final DualButton L1_L12 = new DualButton(L1, L12);
  
  /**
   * Superclass specific methods are: <br>
   * <i>
   * .getJoyX(),
   * .getJoyY(),
   * .getRotation(),
   * .getSlider(),
   * .getSliderScale(),
   * .getButton()
   * @return The Logitech Controller object
   */
  public Logitech3DPro getLogitech() {
    return LOGITECHCONTROLLER;
  }

  // XBOX CONTROLS

  private XBOX mXBOXController = new XBOX(RobotMap.XBOX_PORT);
  public final JoystickButton X1 = new JoystickButton(mXBOXController, 1);
  public final JoystickButton X2 = new JoystickButton(mXBOXController, 2);
  public final JoystickButton X3 = new JoystickButton(mXBOXController, 3);
  public final JoystickButton X4 = new JoystickButton(mXBOXController, 4);
  public final JoystickButton X5 = new JoystickButton(mXBOXController, 5);
  public final JoystickButton X6 = new JoystickButton(mXBOXController, 6);
  
  /**
   * Superclass specific methods are: <br>
   * <i>
   * .getLeftJoyX(),
   * .getLeftJoyY(),
   * .getRightJoyX(),
   * .getRightJoyY(),
   * .getButton()
   * @return The Logitech Controller object
   */
  public XBOX getXBOX() {
    return mXBOXController;
  }

  public OI() {
    // SmartDashboard buttons
    SmartDashboard.putData("Intake Start", new IntakePowerRamp(RobotMap.FUELINTAKE_NORMALPWM));
    SmartDashboard.putData("Intake Stop", new IntakePowerRamp(0.0));
    SmartDashboard.putData("2 metre perimeter", new TC_A_2MetreBox());
    SmartDashboard.putData("Drive +1m", new DriveDistance(1.0, 0.65));
    SmartDashboard.putData("Drive -1m", new DriveDistance(-1.0, 0.65));

    // Check if OI still works with this expression
    if (DriverStation.getInstance().isOperatorControl()) {
      // Logitech controller buttons

      // Angular rate test buttons are 9 and 10
      L9.whenPressed(new DriveTrainAutoTurn(-90.0, true));
      L10.whenPressed(new DriveTrainAutoTurn(90.0, true));
      L1_L11.whenPressed(new DriveTrainAutoTurn(-90.0, false));
      L1_L12.whenPressed(new DriveTrainAutoTurn(90.0, false));

      // XBOX controller buttons
      // Offloader and bridge motor manual controls are mapped to the joysticks.
      X1.whenPressed(new IntakeRampReversal());
      X2.whenPressed(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMALPWM));
      X3.whenPressed(new BridgeAutoMove(false));
      X4.whenPressed(new BridgeAutoMove(true));
      X6.whenPressed(new IntakePowerRamp(0.0));
    }
  }

}
