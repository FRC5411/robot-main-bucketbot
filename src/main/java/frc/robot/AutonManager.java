package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.systems.arm.ArmSubsystem;
import frc.robot.systems.drive.BalanceCommand;
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
                driveBack(1.3,1),

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
                new WaitCommand(1.6),

                // ARM RETURN
                new InstantCommand(() -> robotArm.setArmSpeed(-0.75)),
                new WaitCommand(0.5),
                new InstantCommand(() -> robotArm.setArmSpeed(0)),

                //drive back
                driveBack(3.1,0.7),

                //balance
                autoBalanceCmd()                
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
                driveBack(1,1),

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
            // ----- Balance test -----
            case 5:
              return new SequentialCommandGroup(
              autoBalanceCmd()
              
              );

            default: 
            return new SequentialCommandGroup (
                new InstantCommand( () -> System.out.println("ERROR: Autonomous Failure."))
            );
        }
    }



  private Command driveBack(double time, double speed){
      return new DriveCommand(() -> -speed, () -> 0.0, robotDrive).withTimeout(time);
  }

  private Command driveFront(double time, double speed){
      return new DriveCommand(() -> speed, () -> 0.0, robotDrive).withTimeout(time);
  }

  public Command autoBalanceCmd(){
    return new BalanceCommand(robotDrive);
    
  }


}