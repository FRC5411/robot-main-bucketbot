// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.systems.arm.ArmSubsystem;
import frc.robot.systems.drive.DriveCommand;
import frc.robot.systems.drive.DriveSubsystem;
import frc.robot.systems.intake.IntakeSubsystem;

public class RobotContainer {
  private DriveSubsystem robotDrive; 
  private CommandXboxController driveController;
  private CommandXboxController operatorController;
  private ArmSubsystem robotArm;
  private IntakeSubsystem robotIntake;
  private AutonManager autonManager;
  private SendableChooser<Command> autonChooser;
    public RobotContainer() {
    robotDrive = new DriveSubsystem();
    robotArm = new ArmSubsystem();
    robotIntake = new IntakeSubsystem();
    autonManager = new AutonManager(robotDrive, robotArm);
    driveController = new CommandXboxController(0);
    operatorController = new CommandXboxController(1);
    

    robotDrive.setDefaultCommand(
      new DriveCommand(
        () -> -driveController.getLeftY(), 
        () -> -driveController.getRightX(), 
        robotDrive));

      autonChooser = new SendableChooser<>();
        
    Shuffleboard.getTab("Autonomous: ").add(autonChooser);
    
    autonChooser.addOption("SHOOT MOBILITY, CABLE SIDE", autonManager.autonomousCmd(1));
    autonChooser.addOption("SHOOT MOBILITY DOCK", autonManager.autonomousCmd(2));
    autonChooser.addOption("SHOOT MOBILITY, NO CABLE", autonManager.autonomousCmd(3));
    autonChooser.setDefaultOption("SHOOT", autonManager.autonomousCmd(4));
    autonChooser.addOption("balance test",autonManager.autonomousCmd(5));
    configureBindings();
  }

  private void configureBindings() {
    // Drive Bindings
    

    // Arm Bindings
    operatorController.rightTrigger()
      .whileTrue(new InstantCommand(() -> robotArm.setArmSpeed(.27)))
      .onFalse(new InstantCommand(() -> robotArm.setArmSpeed(0)));

    operatorController.leftTrigger()
      .whileTrue(new InstantCommand(() -> robotArm.setArmSpeed(-0.4)))// -.15
      .onFalse(new InstantCommand(() -> robotArm.setArmSpeed(0)));


    operatorController.rightBumper()
      .whileTrue(new InstantCommand(() -> robotIntake.intakeIn()))
      .onFalse(new InstantCommand(() -> robotIntake.intakeStop()));
    
    operatorController.leftBumper()
      .whileTrue(new InstantCommand(() -> robotIntake.intakeOut()))
      .onFalse(new InstantCommand(() -> robotIntake.intakeStop()));
  }

  

  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }
}