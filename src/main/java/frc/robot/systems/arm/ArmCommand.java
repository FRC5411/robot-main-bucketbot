// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.systems.arm;

import java.util.function.DoubleSupplier;

import javax.swing.AbstractListModel;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.ArmConstants;

public class ArmCommand extends CommandBase {
  /** Creates a new ArmCommand. */
  private ArmSubsystem arm;
  private ProfiledPIDController PID;
  private ArmFeedforward FF;
  private double setpoint;

  public ArmCommand(ArmSubsystem arm, double setpoint) {
    this.arm = arm;
    this.setpoint = setpoint;
    addRequirements(arm);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    arm = new ArmSubsystem(setpoint);
    PID = new ProfiledPIDController(ArmConstants.kP, ArmConstants.kI, ArmConstants.kD, 
    new TrapezoidProfile.Constraints(ArmConstants.arm_velocity, ArmConstants.arm_acceleration));
    FF = new ArmFeedforward(ArmConstants.kS, ArmConstants.kG, ArmConstants.kV);
    
    PID.setTolerance(ArmConstants.pid_tolerance);

  }

  public double setPID(double setpoint) {
    return PID.calculate(Math.toDegrees(arm.getEncoderPosition()), setpoint);
  }

  public double setFF() {
    return FF.calculate(Math.toDegrees(arm.getEncoderPosition()), Math.toRadians(arm.getVelocity()));
  }
  
  public Command goToSetpoint(double setpoint) {
    return new InstantCommand(
    () -> {
    double PID = setPID(setpoint);
    double FF = setFF();

    arm.setVoltage(PID + FF);
    });
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   goToSetpoint(setpoint);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.setVoltage(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
