package org.usfirst.frc.team6485.robot.commandgroups;

import org.usfirst.frc.team6485.robot.RobotMap.BRIDGE_STATE;
import org.usfirst.frc.team6485.robot.commands.BridgeControl;
import org.usfirst.frc.team6485.robot.commands.IntakePowerRamp;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class CG_PrepareGearLoading extends CommandGroup {

  /**
   * Stop the intake and lower the bridge.
   */
  public CG_PrepareGearLoading() {
    addParallel(new IntakePowerRamp(0.0));
    addParallel(new BridgeControl(BRIDGE_STATE.LOWERED));
  }

}
