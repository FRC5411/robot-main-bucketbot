package frc.robot.systems.drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.sensors.WPI_PigeonIMU;
public class BalanceCommand extends CommandBase{
    WPI_PigeonIMU gyro = new WPI_PigeonIMU(41);
    private int state;
    private double tolarance = 10.2;
    private DriveSubsystem m_drive;
    private DoubleSupplier pitch = () -> gyro.getPitch();
public BalanceCommand(DriveSubsystem drive){
    m_drive = drive;
    addRequirements(m_drive);
}
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {state = 1;}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("pitch", pitch.getAsDouble());
    SmartDashboard.putNumber("state", state);
    //ground state
    if (state == 1){
        if(pitch.getAsDouble()<tolarance&&pitch.getAsDouble()>-tolarance) m_drive.arcadeCmd(0.7, 0);
        else {
          state = 2;
        }
    }
    //angle state
    if (state == 2){
        if(pitch.getAsDouble()>tolarance) m_drive.arcadeCmd(0.5, 0);
        else state = 3;
    }
    //balance state
    if (state == 3){
        if(pitch.getAsDouble()<-tolarance) m_drive.arcadeCmd(-0.43, 0);
        if(pitch.getAsDouble()>tolarance) m_drive.arcadeCmd(0.43, 0);
    }
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

