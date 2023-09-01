// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.systems.arm;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmCommand extends CommandBase {
  double setPointAngle;
  ArmSubsystem robotArm;
  ProfiledPIDController armPID;

  public ArmCommand(double targetSetpoint, ArmSubsystem robotArmSS) {
    setPointAngle = targetSetpoint;
    robotArm = robotArmSS;
    addRequirements(robotArm);
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double kP = 0.03;
    double kI = 0;
    double kD = 0;
    
    double armVelocity = 0;
    double armAcceleration = 0;
    
    armPID = new ProfiledPIDController(kP, kI, kD, 
    new TrapezoidProfile.Constraints(armVelocity, armAcceleration)
    );
    armPID.reset(robotArm.getEncoderPosition());
    armPID.setTolerance(2);

    System.out.println("Command ARM COMMAND has started");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double calc = armPID.calculate(robotArm.getEncoderPosition(), setPointAngle);
    robotArm.setArmSpeed(calc);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    robotArm.setArmSpeed(0);
    System.out.println("Command TELEOP ARM ALIGN has ended");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
