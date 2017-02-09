package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class TestAuto extends CommandGroup {

  /**
   * Test case that:
   * <ol>
   * <li>Starts the intake motor to intake mode</li>
   * <li>Drives forward at 75% for 1.5 seconds</li>
   * <li>Does a complete 180 degree turn to the right</li>
   * <li>Drives forward at 75% for 1.5 seconds</li>
   * <li>Shuts off the intake motor</li>
   * <li>Does a complete 180 degree turn to the left</li>
   * </ol>
   */
  public TestAuto() {

    addParallel(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new AutoDrive(0.65, 2.5));
    addSequential(new Delay(1.0));
    addSequential(new GyroscopeTurn(180.0));
    addSequential(new AutoDrive(0.65, 2.5));
    addSequential(new Delay(1.0));
    addParallel(new IntakePowerRamp(0.0));
    addSequential(new GyroscopeTurn(-180.0));

    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
