package org.usfirst.frc.team6485.robot.autonomous;

import org.usfirst.frc.team6485.robot.commands.AutoGyroTurn;
import org.usfirst.frc.team6485.robot.commands.Delay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class TC_A_Spin extends CommandGroup {

  public TC_A_Spin() {

    addSequential(new AutoGyroTurn(90));
    addSequential(new Delay(1));
    addSequential(new AutoGyroTurn(90));
    addSequential(new Delay(1));
    addSequential(new AutoGyroTurn(90));
    addSequential(new Delay(1));
    addSequential(new AutoGyroTurn(90));

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
