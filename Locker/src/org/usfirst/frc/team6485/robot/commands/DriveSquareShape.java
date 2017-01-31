package org.usfirst.frc.team6485.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Test case that will drive a robot along a square perimeter.<br>
 * Will return to original position and orientation if the drive train is working properly.<br>
 * <br>
 * Line: 0.65 speed for 2 seconds<br>
 * Rotation: 90 degrees right<br>
 * <br>
 * <i>Kyle Saburao 2017</i>
 */
public class DriveSquareShape extends CommandGroup {

    public DriveSquareShape() {
	
	addSequential(new DriveForward(0.65, 3));
	addSequential(new GyroscopeTurn(90));
	addSequential(new DriveForward(0.65, 3));
	addSequential(new GyroscopeTurn(90));
	addSequential(new DriveForward(0.65, 3));
	addSequential(new GyroscopeTurn(90));
	addSequential(new DriveForward(0.65, 3));
	addSequential(new GyroscopeTurn(90));
	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
	
    }
}
