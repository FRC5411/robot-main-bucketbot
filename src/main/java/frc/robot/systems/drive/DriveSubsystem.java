package frc.robot.systems.drive;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class DriveSubsystem extends SubsystemBase {
  private WPI_TalonSRX m_frontLeft;
  private WPI_TalonSRX m_frontRight;
  private WPI_TalonSRX m_backRight;
  private WPI_TalonSRX m_backLeft;

  private MotorControllerGroup leftMotors;
  private MotorControllerGroup rightMotors;

  private DifferentialDrive m_drive;
 
  public DriveSubsystem() {
    
    m_frontLeft = new WPI_TalonSRX(13);
    m_frontRight = new WPI_TalonSRX(14);
    m_backLeft = new WPI_TalonSRX(11);
    m_backRight = new WPI_TalonSRX(12);

    leftMotors = new MotorControllerGroup(m_frontLeft, m_backLeft);
    rightMotors = new MotorControllerGroup(m_frontRight, m_backRight);

    m_backRight.setInverted(true);
    m_frontRight.setInverted(true);
    
    configMotor(m_backLeft);
    configMotor(m_backRight);
    configMotor(m_frontLeft);
    configMotor(m_frontRight);
    m_drive = new DifferentialDrive(leftMotors, rightMotors);
  }

  private void configMotor(WPI_TalonSRX motor){
    motor.configPeakCurrentLimit(40);
    motor.configPeakCurrentDuration(10000);
  }


  @Override
  public void periodic() {
   
  }

  @Override
  public void simulationPeriodic() {
    
  }

  public void arcadeCmd(double speed, double rotation) {
    m_drive.arcadeDrive(speed, rotation);
  }

  

 
}