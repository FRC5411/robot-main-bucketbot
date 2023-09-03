// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.systems.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveCommand extends CommandBase {
  /** Creates a new DriveCommand. */
  private DoubleSupplier m_speed;
  private DoubleSupplier m_rotation;
  private DriveSubsystem m_drive;
  public DriveCommand(DoubleSupplier speed, DoubleSupplier rotation, DriveSubsystem drive) {

    m_speed = speed;
    m_rotation = rotation;
    m_drive = drive;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speedDouble = m_speed.getAsDouble();
    double rotationDouble = m_rotation.getAsDouble();
    
    if(Math.abs(speedDouble) < 0.1) speedDouble = 0;
    if(Math.abs(rotationDouble) < 0.1) rotationDouble = 0;

    m_drive.arcadeCmd(m_speed.getAsDouble() , m_rotation.getAsDouble() * 0.8);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
