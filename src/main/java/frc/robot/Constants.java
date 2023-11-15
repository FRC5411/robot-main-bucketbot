package frc.robot;

public class Constants {
    public static class DriveBase{
        public static final double k_rotationOffset = 0;
    }

    public static class ArmConstants {
        public static final double k_launchForward = 200;
        public static final double k_launchReturn = 0;
        public static final double k_idle = 50;
        public static final int k_armID = 4;

        public static final double kP = 0.0001;
        public static final double kI = 0.0;
        public static final double kD = 0.0;

        public static final double kS = 0.0;
        public static final double kG = 0.0;
        public static final double kV = 0.0;
        public static final double kA = 0.0;

        public static final double arm_velocity = 0.0;
        public static final double arm_acceleration = 0.0;

        public static final double pid_tolerance = 2.0;

        public static final double idleSetpoint = 1.0;
        public static final double pickupSetpoint = 1.0;
        
    }
}
