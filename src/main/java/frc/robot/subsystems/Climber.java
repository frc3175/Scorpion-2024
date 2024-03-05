package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Climber extends SubsystemBase {

    private TalonFX m_climber;

    private DutyCycleOut climbPercentOutput = new DutyCycleOut(0);
    private VelocityDutyCycle climbVelocity = new VelocityDutyCycle(0);
    private PositionDutyCycle climbPosition = new PositionDutyCycle(0);

    public Climber() {

        m_climber = new TalonFX(Constants.CLIMBER_ID, "elevatoryiboi");

        configClimberMotor();

    }

    public void climbPosition(double position) {

        climbPosition.Position = position;
        m_climber.setControl(climbPosition);

    }

    public void climbVelocity(double velocity) {

        climbVelocity.Velocity = velocity;
        m_climber.setControl(climbVelocity);

    }

    public void climbPercentOutput(double percentOutput) {

        climbPercentOutput.Output = percentOutput;
        m_climber.setControl(climbPercentOutput);

    }

    public void resetClimberEncoder() {

       m_climber.setPosition(0);

    }

    public void configClimberMotor() {

        resetClimberEncoder();
        m_climber.getConfigurator().apply(new TalonFXConfiguration());
        m_climber.getConfigurator().apply(Robot.ctreConfigs.climberFXConfig);

    }

    public double getClimberEncoder() {

       return m_climber.getPosition().getValueAsDouble();

    }

    public double getClimberVelocity() {

       return m_climber.getVelocity().getValueAsDouble();

    }

    public enum ClimberState {

        RESET(Constants.CLIMBER_RESET),
        CLIMBER_UP(Constants.CLIMBER_UP),
        CLIMBER_DOWN(Constants.CLIMBER_DOWN);

        public double climberPosition;
        private ClimberState(double climberPosition) {
            this.climberPosition = climberPosition;
        }

    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Climber position", getClimberEncoder());
        SmartDashboard.putNumber("Climber velocity", getClimberVelocity());

    }
    
}
