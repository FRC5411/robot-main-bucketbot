package frc.robot.systems.drive;


import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class DriveSubsystem extends SubsystemBase {
  private WPI_TalonSRX m_frontLeft;
  private WPI_TalonSRX m_frontRight;
  private WPI_TalonSRX m_backRight;
  private WPI_TalonSRX m_backLeft;

  private DifferentialDrive m_drive;
 
  public DriveSubsystem() {
    m_frontLeft = new WPI_TalonSRX(13);
    m_frontRight = new WPI_TalonSRX(14);
    m_backLeft = new WPI_TalonSRX(11);
    m_backRight = new WPI_TalonSRX(12);

    m_backLeft.follow(m_frontLeft);
    m_backRight.follow(m_frontRight);

    m_backLeft.setInverted(true);
    m_frontLeft.setInverted(true);
    

    m_drive = new DifferentialDrive(m_frontLeft, m_frontRight);
  }


  @Override
  public void periodic() {
   
  }

  @Override
  public void simulationPeriodic() {
    
  }

  public void arcadeCmd(double speed, double rotation) {
    double rotationOffset = Constants.DriveBase.k_rotationOffset;
    if(speed == 0) {rotationOffset = 0;} 
    else if (speed > 0) { rotationOffset = Constants.DriveBase.k_rotationOffset; }
    else {rotationOffset = -Constants.DriveBase.k_rotationOffset;}
    m_drive.arcadeDrive(speed, rotation + rotationOffset);
  }

 
}