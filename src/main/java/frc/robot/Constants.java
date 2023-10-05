package frc.robot;

public class Constants {
    public static class DriveBase{
        public static final double k_rotationOffset = 0;
    }

    public static class Arm{
        public static final double k_launchForward = 200;
        public static final double k_launchReturn = 0;
        public static final double k_idle = 50;
        public static final int k_armID = 4;
    }
    public static class Intake{
        public static final int k_intakeID = 5;
        public static final double k_inSpeed = -0.7;
        public static final double k_outSpeed = 1.0;
    }
}
