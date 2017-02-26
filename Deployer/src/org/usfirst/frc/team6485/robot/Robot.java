package org.usfirst.frc.team6485.robot;

import org.usfirst.frc.team6485.robot.RobotMap.RUNNING_MODE;
import org.usfirst.frc.team6485.robot.autonomous.A_BlueAllianceCentre;
import org.usfirst.frc.team6485.robot.autonomous.A_BlueAllianceLeft;
import org.usfirst.frc.team6485.robot.autonomous.A_BlueAllianceRight;
import org.usfirst.frc.team6485.robot.autonomous.A_RedAllianceCentre;
import org.usfirst.frc.team6485.robot.autonomous.A_RedAllianceLeft;
import org.usfirst.frc.team6485.robot.autonomous.A_RedAllianceRight;
import org.usfirst.frc.team6485.robot.commandgroups.CG_PassBaseLine;
import org.usfirst.frc.team6485.robot.subsystems.Bridge;
import org.usfirst.frc.team6485.robot.subsystems.DriveTrain;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake;
import org.usfirst.frc.team6485.robot.subsystems.Offloader;
import org.usfirst.frc.team6485.robot.utility.BridgeReporter;
import org.usfirst.frc.team6485.robot.utility.DriveTrainReporter;
import org.usfirst.frc.team6485.robot.utility.IntakeReporter;
import org.usfirst.frc.team6485.robot.utility.OffloaderReporter;
import org.usfirst.frc.team6485.robot.utility.PowerDistributionPanelReporter;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * CG_: Command Group <br>
 * TC_: Test case <br>
 * A_: Autonomous command group
 * 
 * @author Kyle Saburao
 */
public class Robot extends IterativeRobot {

  public static RUNNING_MODE robotMode;

  public static OI oi;
  public static DriveTrain DRIVETRAIN;
  public static FuelIntake FUELINTAKE;
  public static Bridge BRIDGE;
  public static Offloader OFFLOADER;
  public static DriverStation DRIVERSTATION;
  public static Alliance ALLIANCECOLOUR;
  public static boolean FMS_CONNECTED;

  private double mCycleTimeOld, mCycleTimeNew;

  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    DRIVETRAIN = new DriveTrain();
    FUELINTAKE = new FuelIntake();
    BRIDGE = new Bridge();
    OFFLOADER = new Offloader();

    DRIVERSTATION = DriverStation.getInstance();
    ALLIANCECOLOUR = DRIVERSTATION.getAlliance();
    FMS_CONNECTED = DRIVERSTATION.isFMSAttached();

    oi = new OI();

    // All autonomous modes will first pass the baseline. (1.65 metres)
    // When the FMS is connected, only the proper alliance commands will be displayed.
    if (FMS_CONNECTED) {
      chooser.addDefault("Pass Baseline", new CG_PassBaseLine());
      if (ALLIANCECOLOUR == Alliance.Blue) {
        chooser.addObject("BA Left", new A_BlueAllianceLeft());
        chooser.addObject("BA Centre", new A_BlueAllianceCentre());
        chooser.addObject("BA Right", new A_BlueAllianceRight());
      } else if (ALLIANCECOLOUR == Alliance.Red) {
        chooser.addObject("RA Left", new A_RedAllianceLeft());
        chooser.addObject("RA Centre", new A_RedAllianceCentre());
        chooser.addObject("RA Right", new A_RedAllianceRight());
      }
    } else {
      chooser.addDefault("Pass Baseline", new CG_PassBaseLine());
      chooser.addObject("BA Left", new A_BlueAllianceLeft());
      chooser.addObject("BA Centre", new A_BlueAllianceCentre());
      chooser.addObject("BA Right", new A_BlueAllianceRight());
      chooser.addObject("RA Left", new A_RedAllianceLeft());
      chooser.addObject("RA Centre", new A_RedAllianceCentre());
      chooser.addObject("RA Right", new A_RedAllianceRight());
    }

    SmartDashboard.putData("Auto Mode", chooser);
    SmartDashboard.putData("Drive Train", DRIVETRAIN);
    SmartDashboard.putData("Fuel Intake", FUELINTAKE);
    SmartDashboard.putData("Bridge", BRIDGE);
    SmartDashboard.putData("Offloader", OFFLOADER);

    CameraServer.getInstance().startAutomaticCapture();
    // UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    // camera.setFPS(20);
    // camera.setResolution(640, 360);
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You can use it to reset
   * any subsystem information you want to clear when the robot is disabled.
   */
  @Override
  public void disabledInit() {
    // Stop the subsystem motors.
    Robot.FUELINTAKE.stop();
    Robot.DRIVETRAIN.stop();
    Robot.BRIDGE.stop();
    Robot.OFFLOADER.stop();

    // Cancel all queued commands.
    Scheduler.getInstance().removeAll();
    robotMode = RUNNING_MODE.DISABLED;

    // Reset encoders
    Robot.DRIVETRAIN.getEncoder().reset();
    Robot.BRIDGE.getEncoder().reset();
  }

  @Override
  public void disabledPeriodic() {
    Robot.FUELINTAKE.stop();
    Robot.DRIVETRAIN.stop();
    Robot.BRIDGE.stop();
    Robot.OFFLOADER.stop();

    Scheduler.getInstance().run();
    report();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString code to get the auto name from the text box below the Gyro
   *
   * You can add additional auto modes by adding additional commands to the chooser code above (like
   * the commented example) or additional comparisons to the switch structure below with additional
   * strings & commands.
   */
  @Override
  public void autonomousInit() {
    robotMode = RUNNING_MODE.AUTO;
    autonomousCommand = chooser.getSelected();

    // Reset encoders
    Robot.DRIVETRAIN.getEncoder().reset();
    Robot.BRIDGE.getEncoder().reset();
    Robot.DRIVETRAIN.getGyro().reset();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new MyAutoCommand(); break; case
     * "Default Auto": default: autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    report();
  }

  @Override
  public void teleopInit() {
    robotMode = RUNNING_MODE.TELEOP;
    Robot.DRIVETRAIN.getGyro().reset();
    Robot.DRIVETRAIN.getEncoder().reset();
    Robot.BRIDGE.getEncoder().reset();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    report();
  }

  /**
   * This function is called periodically during test mode
   */
  @Override
  public void testPeriodic() {
    LiveWindow.run();
  }

  public void report() {
    mCycleTimeNew = Timer.getFPGATimestamp();
    String robotmode_string = "";
    switch (robotMode) {
      case DISABLED:
        robotmode_string = "DISABLED";
        break;
      case TELEOP:
        robotmode_string = "TELEOP";
        break;
      case AUTO:
        robotmode_string = "AUTO";
        break;
    }
    SmartDashboard.putString("ROBOT MODE", robotmode_string);

    // Subsystem Reporters
    DriveTrainReporter.report();
    IntakeReporter.report();
    BridgeReporter.report();
    OffloaderReporter.report();
    PowerDistributionPanelReporter.report();
    SmartDashboard.putNumber("Robot Report Cycle Time", mCycleTimeNew - mCycleTimeOld);
    mCycleTimeOld = mCycleTimeNew;
  }

}
