package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Feeder extends SubsystemBase {

    private TalonFX m_feeder;

    private final VelocityDutyCycle feederVelocity = new VelocityDutyCycle(0);
    private final DutyCycleOut feederPercentOutput = new DutyCycleOut(0);

    public Feeder() {

        m_feeder = new TalonFX(Constants.FEEDER_ID, "elevatoryiboi");
        resetEncoder();
        configFeederMotor();

    }

    public void feederRun(double velocity) {

        feederVelocity.Velocity = velocity;
        m_feeder.setControl(feederVelocity);

    }

    public void feederRunPercentOutput(double percentOutput) {

        feederPercentOutput.Output = percentOutput;
        m_feeder.setControl(feederPercentOutput);

    }

    public void resetEncoder() {

        m_feeder.setPosition(0);

    }

    public void configFeederMotor() {

        m_feeder.getConfigurator().apply(Robot.ctreConfigs.feederFXConfig);

    }

    public double getFeederEncoder() {

        return m_feeder.getPosition().getValueAsDouble();

    }

    public double getFeederVelocity() {

        return m_feeder.getVelocity().getValueAsDouble();

    }

    public enum FeederState {

        RESET(Constants.FEEDER_RESET),
        INTAKE(Constants.FEEDER_RESET),
        SHOOT(Constants.FEEDER_SHOOT),
        AMP(Constants.FEEDER_AMP),
        CLIMB(Constants.FEEDER_RESET);

        public double feederVelocity;
        private FeederState(double velocity) {
            feederVelocity = velocity;
        }

    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Feeder velocity", getFeederVelocity());
        SmartDashboard.putNumber("Feeder Encoder", getFeederEncoder());


    }


    
}
