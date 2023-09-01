// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.systems.arm.ArmSubsystem;
import frc.robot.systems.drive.DriveCommand;
import frc.robot.systems.drive.DriveSubsystem;

public class RobotContainer {
  private DriveSubsystem robotDrive; 
  private CommandXboxController driveController;
  private CommandXboxController operatorController;
  private ArmSubsystem robotArm;
  private AutonManager autonManager;


  public RobotContainer() {
    robotDrive = new DriveSubsystem();
    robotArm = new ArmSubsystem();
    autonManager = new AutonManager(robotDrive, robotArm);
    driveController = new CommandXboxController(0);
    operatorController = new CommandXboxController(1);
    

    robotDrive.setDefaultCommand(
      new DriveCommand(
        () -> -driveController.getLeftY(), 
        () -> driveController.getRightX(), 
        robotDrive));

      // autoCommandChooser.setDefaultOption("Mobility", mobilityAuton());
      // autoCommandChooser.addOption("One Piece Mobility", onePieceMobilityAuton());

    configureBindings();
  }

  private void configureBindings() {
    // Drive Bindings
    

    // Arm Bindings
    operatorController.rightTrigger()
      .whileTrue(new InstantCommand(() -> robotArm.setArmSpeed(0.25)))
      .onFalse(new InstantCommand(() -> robotArm.setArmSpeed(0)));

      operatorController.leftTrigger()
      .whileTrue(new InstantCommand(() -> robotArm.setArmSpeed(-0.25)))
      .onFalse(new InstantCommand(() -> robotArm.setArmSpeed(0)));
  }

  

  public Command getAutonomousCommand() {
    return autonManager.autonomousCmd(3);
  }
}