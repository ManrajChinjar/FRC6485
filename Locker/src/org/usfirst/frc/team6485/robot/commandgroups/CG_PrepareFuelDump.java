package org.usfirst.frc.team6485.robot.commandgroups;

import org.usfirst.frc.team6485.robot.RobotMap.BRIDGE_STATE;
import org.usfirst.frc.team6485.robot.commands.BridgeControl;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class CG_PrepareFuelDump extends CommandGroup {

  /**
   * Stop the intake and raise the bridge. <br>
   * Will attempt to evacuate fuel stuck in the ramp.
   */
  public CG_PrepareFuelDump() {
    addSequential(new CG_ClearIntakeRamp());
    addSequential(new BridgeControl(BRIDGE_STATE.RAISED));
  }

}
