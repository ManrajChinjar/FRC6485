package org.usfirst.frc.team6485.robot.commands;

import org.usfirst.frc.team6485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 1. a) Lower the bridge 1. b) Start the intake motor to intake mode 1. c) Drive forwards at 60%
 * for 1.5 seconds 2. Turn right 45 degrees 3. Drive forwards at 85% for 2.0 seconds 4. Turn left 45
 * degrees 5. Drive forwards at 85% for 2.5 seconds 6. Set the intake motor to reverse speed 7. Wait
 * 2.0 seconds 8. a) Raise the bridge 8. b) Stop the intake motor
 * 
 * @author Kyle Saburao
 */
public class TC_CG_Auto2 extends CommandGroup {

  public TC_CG_Auto2() {
    addParallel(new LowerBridge());
    addParallel(new IntakePowerRamp(RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new AutoDrive(0.60, 1.5));
    addSequential(new AutoGyroTurn(45.0));
    addSequential(new AutoDrive(0.85, 2.0));
    addSequential(new AutoGyroTurn(-45.0));
    addSequential(new AutoDrive(0.85, 2.5));
    addSequential(new IntakePowerRamp(-RobotMap.FUELINTAKE_NORMAL_PWM));
    addSequential(new Delay(2.0));
    addParallel(new RaiseBridge());
    addSequential(new IntakePowerRamp(0.0));
  }
}
