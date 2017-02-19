package org.usfirst.frc.team6485.robot.commandgroups;

import org.usfirst.frc.team6485.robot.RobotMap;
import org.usfirst.frc.team6485.robot.RobotMap.BRIDGE_STATE;
import org.usfirst.frc.team6485.robot.commands.BridgeMover;
import org.usfirst.frc.team6485.robot.commands.IntakePowerRamp;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Kyle Saburao
 */
public class CG_PrepareFuelIntake extends CommandGroup {

  /**
   * Lower the bridge and start the intake motor.
   */
  public CG_PrepareFuelIntake() {
    addParallel(new BridgeMover(BRIDGE_STATE.LOWERED));
    addParallel(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMAL_PWM));
  }

}
