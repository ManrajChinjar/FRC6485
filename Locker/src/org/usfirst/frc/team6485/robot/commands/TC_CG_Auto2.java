package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TC_CG_Auto2 extends CommandGroup {

  public TC_CG_Auto2() {

    addParallel(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new AutoDrive(0.60, 1.5));
    addSequential(new AutoGyroTurn(45.0));
    addSequential(new AutoDrive(0.85, 2.0));
    addSequential(new AutoGyroTurn(-45.0));
    addSequential(new AutoDrive(0.85, 2.5));
    addSequential(new IntakePowerRamp(-RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new Delay(2.0));
    addSequential(new IntakePowerRamp(0.0));

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
