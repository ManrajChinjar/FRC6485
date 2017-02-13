package org.usfirst.frc.team6485.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class TC_CG_Auto extends CommandGroup {

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
   * 
   */
  public TC_CG_Auto() {
    addParallel(new CG_PrepareFuelIntake());
    addSequential(new AutoDrive(0.75, 3.5));
    addSequential(new Delay(1.0));
    addSequential(new AutoGyroTurn(180.0));
    addSequential(new AutoDrive(0.75, 3.5));
    addSequential(new Delay(1.0));
    addSequential(new AutoGyroTurn(180.0));
    addSequential(new CG_PrepareFuelDump());
  }

}
