// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.systems.arm.ArmCommand;
import frc.robot.systems.arm.ArmSubsystem;
import frc.robot.systems.drive.DriveCommand;
import frc.robot.systems.drive.DriveSubsystem;

public class RobotContainer {
  private DriveSubsystem robotDrive; 
  private CommandXboxController driveController;
  private CommandXboxController operatorController;
  private ArmSubsystem robotArm;


  public RobotContainer() {
    robotDrive = new DriveSubsystem();
    robotArm = new ArmSubsystem();
    driveController = new CommandXboxController(0);
    operatorController = new CommandXboxController(1);

    robotDrive.setDefaultCommand(
      new DriveCommand(
        () -> driveController.getLeftY(), 
        () -> driveController.getRightX(), 
        robotDrive));

    configureBindings();
  }

  private void configureBindings() {
    // Drive Bindings
    

    // Arm Bindings
    operatorController.y().whileTrue(executeLaunch());
    operatorController.y().onFalse(new ArmCommand(robotArm, "idle", 0, 0));

    operatorController.a().onTrue(new ArmCommand(robotArm, "idle", 5, 2.5));
  }

  public Command executeLaunch(){
    return new SequentialCommandGroup(
      new ArmCommand(robotArm, "launchForward", 5, 2.5),
      new ArmCommand(robotArm, "launchReturn", 10, 5)
    );
  }

  public Command getAutonomousCommand() {
    return null;
  }
}