// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.systems.arm;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Constants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ArmCommand extends ProfiledPIDCommand {
  /** Creates a new ArmCommand. */
  public ArmCommand(ArmSubsystem m_arm, String position, double vel, double acc) {
    super(
        // The ProfiledPIDController used by the command
        new ProfiledPIDController(
            // The PID gains
            0.1,
            0,
            0,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(vel, acc)),
        // This should return the measurement
        () -> m_arm.getEncoderPosition(),
        // This should return the goal (can also be a constant)
        () -> convertStringToDouble(position),
        // This uses the output
        (output, setpoint) -> {
          // Use the output (and setpoint, if desired) here
          SmartDashboard.putNumber("Encoder Output", output);

          m_arm.setArmSpeed(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
          addRequirements(m_arm);
          getController().setTolerance(2.5);
  }

  public static double convertStringToDouble(String pos){
    if(pos.equals("launchForward")){

      return Constants.Arm.k_launchForward;

    } else if(pos.equals("launchReturn")){

      return Constants.Arm.k_launchReturn;

    } else if(pos.equals("idle")){
      
      return Constants.Arm.k_idle;

    } else{

      return Constants.Arm.k_idle;

    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atGoal();
  }
}
