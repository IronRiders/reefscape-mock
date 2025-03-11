package org.ironriders.climb;

public class ClimbConstants {
    public static final int CLIMBER_MOTOR_CAN_ID = 17;
    public static final int CURRENT_LIMIT = 40;

    public static final double GEAR_RATIO = 1/100;

    public static final double P = 0.1;
    public static final double I = 0; 
    public static final double D = 0;

    public static final double MAX_ACC = 2;
    public static final double MAX_VEL = 2;

    public static final double T = .02;

    public enum State {
        UP(-0.3),
        STOP(0),
        DOWN(0.3);

        public final double speed;

        State(double speed) { this.speed = speed; }
    }

    public enum Targets {
        HOME(0),
        MAX(-123.5),
        TARGET(40); //TEST and figure out (40 should be safe and visible) (This is supposed to be the angle where the robot is off the ground but not touching the chain)

        public final double pos;

        Targets(double pos) { this.pos = pos; }

    }
}
