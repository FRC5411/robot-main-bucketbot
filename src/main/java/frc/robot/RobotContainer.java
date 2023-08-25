// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.systems.drive.DriveCommand;
import frc.robot.systems.drive.DriveSubsystem;

public class RobotContainer {
  private DriveSubsystem robotDrive; 
  private XboxController driveController;


  public RobotContainer() {
    robotDrive = new DriveSubsystem();
    driveController = new XboxController(0);

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
    
  }

  public Command getAutonomousCommand() {
    return null;
  }
}