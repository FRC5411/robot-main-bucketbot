package frc.robot.systems.drive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.ctre.phoenix.sensors.WPI_PigeonIMU;
public class BalanceCommand extends CommandBase{
    WPI_PigeonIMU gyro = new WPI_PigeonIMU(41);
    private int state = 1;
    private double tolarance = 3;
    private DriveSubsystem m_drive;
public BalanceCommand(DriveSubsystem drive){
    m_drive = drive;
    addRequirements(m_drive);
}
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {state = 0;}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double pitch = gyro.getPitch();
    //ground state
    if (state == 1){
        if(pitch<tolarance&&pitch>-tolarance) m_drive.arcadeCmd(0.3, 0);
        else state = 2;
    }
    //angle state
    if (state == 2){
        if(pitch>tolarance) m_drive.arcadeCmd(0.2, 0);
        else state = 3;
    }
    //balance state
    if (state == 3){
        if(pitch<-tolarance) m_drive.arcadeCmd(-0.15, 0);
        if(pitch>tolarance) m_drive.arcadeCmd(0.15, 0);
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

