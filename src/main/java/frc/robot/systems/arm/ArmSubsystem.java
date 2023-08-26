// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.systems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsytem. */

  private CANSparkMax m_arm;
  private double m_gearRatio = 12.0;
  public ArmSubsystem() {
    m_arm = new CANSparkMax(Constants.Arm.k_armID, MotorType.kBrushless);
    m_arm.setIdleMode(IdleMode.kBrake);
    m_arm.setInverted(false);

    m_arm.setSmartCurrentLimit(40);
  }

  public void setArmSpeed(double speed){
    m_arm.set(speed);
  }

  public double getEncoderPosition(){
    return (m_arm.getEncoder().getPosition() * 360.0) / m_gearRatio;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Encoder Value", getEncoderPosition());
  }
}
