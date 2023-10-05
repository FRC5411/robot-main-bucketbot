// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.systems.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsytem. */

  private CANSparkMax m_intake;
  
  public IntakeSubsystem() {
    m_intake = new CANSparkMax(Constants.Intake.k_intakeID, MotorType.kBrushless);
    m_intake.setIdleMode(IdleMode.kBrake);
    m_intake.setSmartCurrentLimit(30);
  }

  public void intakeIn(){
    m_intake.set(Constants.Intake.k_inSpeed);
  }
  public void intakeOut(){
    m_intake.set(Constants.Intake.k_outSpeed);
  }
  public void intakeStop(){
    m_intake.set(0);
  }


  @Override
  public void periodic() {

  }
}
