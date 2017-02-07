package org.usfirst.frc.team6485.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6485.robot.commands.ExampleCommand;
import org.usfirst.frc.team6485.robot.commands.IntakeStop;
import org.usfirst.frc.team6485.robot.commands.TestAuto;
import org.usfirst.frc.team6485.robot.subsystems.DriveTrain;
import org.usfirst.frc.team6485.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake;
import org.usfirst.frc.team6485.robot.subsystems.FuelIntake.IntakeState;

/**
 * @author Kyle Saburao
 */
public class Robot extends IterativeRobot {

    public static OI oi;

    public enum RunningMode {
	DISABLED, TELEOP, AUTO
    }

    public static RunningMode robotMode;

    // SUBSYSTEMS
    public static final DriveTrain drivetrain = new DriveTrain();
    public static final FuelIntake fuelintake = new FuelIntake();
    public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
	oi = new OI();
	chooser.addDefault("Default Auto", new ExampleCommand());
	chooser.addObject("Intake Drive Test", new TestAuto());
	// chooser.addObject("My Auto", new MyAutoCommand());

	SmartDashboard.putData("Auto mode", chooser);
	SmartDashboard.putData("Drive Train", drivetrain);
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
	// STOP INTAKE ROLLER
	new IntakeStop();
	robotMode = RunningMode.DISABLED;
	Robot.drivetrain.getGyro().calibrate();
    }

    @Override
    public void disabledPeriodic() {
	new IntakeStop();
	Scheduler.getInstance().run();
	report();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     *
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
	robotMode = RunningMode.AUTO;
	autonomousCommand = chooser.getSelected();

	/*
	 * String autoSelected = SmartDashboard.getString("Auto Selector",
	 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
	 * = new MyAutoCommand(); break; case "Default Auto": default:
	 * autonomousCommand = new ExampleCommand(); break; }
	 */

	// schedule the autonomous command (example)
	if (autonomousCommand != null)
	    autonomousCommand.start();
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
	// This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to
	// continue until interrupted by another command, remove
	// this line or comment it out.

	robotMode = RunningMode.TELEOP;

	if (autonomousCommand != null)
	    autonomousCommand.cancel();
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
	SmartDashboard.putNumber("Gyroscope Value", Robot.drivetrain.getGyroAngle());
	SmartDashboard.putNumber("X-Axis Logitech Request (NEG)", -Robot.oi.getLJoyX());
	SmartDashboard.putNumber("Y-Axis Logitech Request (NEG)", -Robot.oi.getLJoyY());

	double[] work = Robot.drivetrain.getMotorPWMS();
	SmartDashboard.putNumber("Front Left PWM", work[0]);
	SmartDashboard.putNumber("Rear Left PWM", work[1]);
	SmartDashboard.putNumber("Front Right PWM", work[2]);
	SmartDashboard.putNumber("Rear Right PWM", work[3]);
	SmartDashboard.putNumber("Left PWM Group Difference", Math.abs(work[0] - work[1]));
	SmartDashboard.putNumber("Right PWM Group Difference", Math.abs(work[2] - work[3]));

	String intakeenum = "";
	IntakeState fuelstate = Robot.fuelintake.getDirectionalState();
	switch (fuelstate) {
	case HALT:
	    intakeenum = "HALT";
	    break;
	case IN:
	    intakeenum = "IN";
	    break;
	case EVACUATE:
	    intakeenum = "EVACUATE";
	    break;
	}
	SmartDashboard.putNumber("Intake PWM", Robot.fuelintake.getSpeed());
	SmartDashboard.putString("Intake ENUM", intakeenum);
    }

}
