// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.systems.arm;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsytem. */

  private CANSparkMax m_arm;
  private double m_gearRatio = 25.0;
  private static double armSpeed;
  private double setpoint;

  public ArmSubsystem(double setpoint) {
    m_arm = new CANSparkMax(Constants.ArmConstants.k_armID, MotorType.kBrushless);
    m_arm.setIdleMode(IdleMode.kBrake);
    m_arm.setInverted(false);
    m_arm.getEncoder().setVelocityConversionFactor(1/42/25);
    m_arm.setSmartCurrentLimit(60);
    this.setpoint = setpoint;
  }

  public void setArmSpeed(double speed){
    armSpeed = speed;
    m_arm.set(speed);
  }


  public double getEncoderPosition(){
    return (m_arm.getEncoder().getPosition() * 360.0) / m_gearRatio;
  }

  public double getVelocity() {
    return m_arm.getEncoder().getVelocity();
  }

  public boolean limitArmSpeed() {
    double bicepEncoderPos = getEncoderPosition();
    if (
      (bicepEncoderPos > 60 && armSpeed > 0) || 
      (bicepEncoderPos < 50 && armSpeed < 0)
    ) { 
      setArmSpeed(0); System.out.println("ENCODER LIMITS IN EFFECT!!!");
      return false;
    }
    else {
      return true;
    }
  }

  public void setVoltage(double voltage) {
    if (limitArmSpeed()) {
    m_arm.setVoltage(voltage);
    }
    else {
      m_arm.setVoltage(0);
    }
  }

  /*public Command goToSetpoint() {
    return new InstantCommand(
      () -> {
        double PID = setPID(setpoint);
        double FF = setFF();
    
        arm.setVoltage(PID + FF);
      }
    )
  }*/

  @Override
  public void periodic() {
    limitArmSpeed();
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("arm velocity", getVelocity());
    SmartDashboard.putNumber("arm voltage", m_arm.getAppliedOutput());
  }
}
