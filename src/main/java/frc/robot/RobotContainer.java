// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.systems.arm.ArmCommand;
import frc.robot.systems.arm.ArmSubsystem;
import frc.robot.systems.drive.DriveCommand;
import frc.robot.systems.drive.DriveForTimeCommand;
import frc.robot.systems.drive.DriveSubsystem;

public class RobotContainer {
  private DriveSubsystem robotDrive; 
  private CommandXboxController driveController;
  private CommandXboxController operatorController;
  private ArmSubsystem robotArm;
  private SendableChooser<Command> autoCommandChooser;


  public RobotContainer() {
    robotDrive = new DriveSubsystem();
    robotArm = new ArmSubsystem();
    driveController = new CommandXboxController(1);
    operatorController = new CommandXboxController(1);
    autoCommandChooser = new SendableChooser<Command>();

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

    // operatorController.y().whileTrue(executeLaunch());
    // operatorController.y().onFalse(new ArmCommand(robotArm, "idle", 0, 0));

    // operatorController.a().onTrue(new ArmCommand(robotArm, "idle", 5, 2.5));
    operatorController.a()
      .onTrue(setArmSetpoint(60))
      .onFalse(new InstantCommand(() -> robotArm.setArmSpeed(0)));


    operatorController.b()
      .onTrue(setArmSetpoint(5))
      .onFalse(new InstantCommand(() -> robotArm.setArmSpeed(0)));
  }

  private Command setArmSetpoint(double setpoint){
    return new ArmCommand(setpoint, robotArm);
  }

  public Command mobilityAuton(){
      return new DriveForTimeCommand(3, -1, robotDrive);
  }

  public Command getAutonomousCommand() {
    return null;
  }
}