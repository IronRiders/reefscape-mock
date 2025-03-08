package org.ironriders.climb;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

public class ClimbSubsystem extends SubsystemBase {

    private final SparkMax climbMotor = new SparkMax(ClimbConstants.CLIMBER_MOTOR_CAN_ID, SparkLowLevel.MotorType.kBrushless);
    private final SparkMaxConfig climbMotorConfig = new SparkMaxConfig();
    private final ClimbCommands commands;

    private double deadReckoning = 0;

    public ClimbSubsystem() {
        climbMotorConfig // if there's an issue with breaks and stuff, the docs show that limit switch is enabled by default
                .smartCurrentLimit(ClimbConstants.CURRENT_LIMIT)
                //.voltageCompensation(ClimbContstants.COMPENSATION)
                .idleMode(IdleMode.kBrake);
        climbMotor.configure(climbMotorConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

        commands = new ClimbCommands(this);
    }

    @Override
    public void periodic() {
        this.deadReckoning += climbMotor.get();
        SmartDashboard.putNumber("Climb Encoder Value", deadReckoning);
    }

    public void set(ClimbConstants.State state) {
        // if (deadReckoning >= ClimbConstants.CLIMBER_LIMIT)
        //     return;
        climbMotor.set(state.speed);
    }

    public ClimbCommands getCommands() {
        return commands;
    }
}
