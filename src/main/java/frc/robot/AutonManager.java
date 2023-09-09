package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.systems.arm.ArmSubsystem;
import frc.robot.systems.drive.DriveCommand;
import frc.robot.systems.drive.DriveSubsystem;

public class AutonManager {

    public DriveSubsystem robotDrive;
    public ArmSubsystem robotArm;
    
    public AutonManager(DriveSubsystem robotDrive, ArmSubsystem robotArm) {
        this.robotDrive = robotDrive;
        this.robotArm = robotArm;
    }

    public Command autonomousCmd(int auton) {
        switch(auton) {
          // ------ CABLE-SIDE AUTON ------
            case 1:
              return new SequentialCommandGroup (
                // ARM CATAPULT 
                new InstantCommand(() -> robotArm.setArmSpeed(0.75)),
                new WaitCommand(0.5),        
                new InstantCommand(() -> robotArm.setArmSpeed(0)),
                new WaitCommand(1.5),
                // DRIVE BACK
                driveBack(1.3),

                // ARM RETURN
                new InstantCommand(() -> robotArm.setArmSpeed(-0.75)),
                new WaitCommand(0.5),
                new InstantCommand(() -> robotArm.setArmSpeed(0))
              );

            // ------ MID AUTON ------
            case 2:
              return new SequentialCommandGroup (
                // ARM CATAPULT 
                new InstantCommand(() -> robotArm.setArmSpeed(0.75)),
                new WaitCommand(0.5),
                new InstantCommand(() -> robotArm.setArmSpeed(0)),
                new WaitCommand(1.5),
                // DRIVE BACK THEN FRONT
                driveBack(1.5),
                driveFront(1),

                // ARM RETURN
                new InstantCommand(() -> robotArm.setArmSpeed(-0.75)),
                new WaitCommand(0.5),
                new InstantCommand(() -> robotArm.setArmSpeed(0))
              );

            // ------ NON-CABLE SIDE AUTON ------
            case 3:
              return new SequentialCommandGroup (
                // ARM CATAPULT 
                new InstantCommand(() -> robotArm.setArmSpeed(0.75)),
                new WaitCommand(0.48),
                new InstantCommand(() -> robotArm.setArmSpeed(0)),
                new WaitCommand(1.5),
                // Drive backward
                driveBack(1),

                new InstantCommand(() -> robotArm.setArmSpeed(-0.75)),
                new WaitCommand(0.48),
                new InstantCommand(() -> robotArm.setArmSpeed(0))
              );

            // ------ JUST SHOOT ------
            case 4:
              return new SequentialCommandGroup (
                // ARM CATAPULT 
                new InstantCommand(() -> robotArm.setArmSpeed(0.75)),
                new WaitCommand(0.48),        
                new InstantCommand(() -> robotArm.setArmSpeed(-0.75)),
                new WaitCommand(0.48),
                new InstantCommand(() -> robotArm.setArmSpeed(0))
              );

            default: 
            return new SequentialCommandGroup (
                new InstantCommand( () -> System.out.println("ERROR: Autonomous Failure."))
            );
        }
    }



    private Command driveBack(double time){
      return new DriveCommand(() -> -1, () -> 0.0, robotDrive).withTimeout(time);
  }

  private Command driveFront(double time){
      return new DriveCommand(() -> 1, () -> 0.0, robotDrive).withTimeout(time);
  }

}