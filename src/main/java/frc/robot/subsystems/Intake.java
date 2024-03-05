package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Intake extends SubsystemBase {

    private TalonFX m_intake;
    private TalonFX m_intakePivot;
    private CANcoder m_intakeEncoder;

    private VelocityDutyCycle intakeVelocity = new VelocityDutyCycle(0);
    private DutyCycleOut intakePercentOutput = new DutyCycleOut(0);
    private PositionDutyCycle intakePivotPosition = new PositionDutyCycle(0);
    private DutyCycleOut intakePivotPercentOutput = new DutyCycleOut(0);

    public Intake() {

        m_intake = new TalonFX(Constants.INTAKE_ID, "elevatoryiboi");
        m_intakePivot = new TalonFX(Constants.INTAKE_PIVOT_ID, "elevatoryiboi");
        m_intakeEncoder = new CANcoder(Constants.INTAKE_CANCODER_ID, "elevatoryiboi");

        configIntakeCANCoder();
        configIntakeMotor();
        configIntakePivotMotor();

    }

    public void intakeRun(double velocity) {

        intakeVelocity.Velocity = velocity;
        m_intake.setControl(intakeVelocity);

    }

    public void intakePercentOutput(double percentOutput) {

        intakePercentOutput.Output = percentOutput;
        m_intake.setControl(intakePercentOutput);

    }

    public void intakePivot(double position) {

        intakePivotPosition.Position = position;
        m_intakePivot.setControl(intakePivotPosition);

    }

    public void intakePivotPercentOutput(double percentOutput) {

        intakePivotPercentOutput.Output = percentOutput;
        m_intakePivot.setControl(intakePivotPercentOutput);

    }

    public double getIntakeRollerEncoderPosition() {

        return m_intake.getRotorPosition().getValueAsDouble();

    }

    public void resetIntakeEncoder() {

        m_intake.setPosition(0);

    }

    public void resetIntakePivotEncoder() {

        m_intake.setPosition(0);

    }

    public void configIntakeMotor() {

        m_intake.getConfigurator().apply(new TalonFXConfiguration());
        m_intake.getConfigurator().apply(Robot.ctreConfigs.intakeFXConfig);
        resetIntakeEncoder();

    }

    public void configIntakePivotMotor() {

        m_intakePivot.getConfigurator().apply(new TalonFXConfiguration());
        m_intakePivot.getConfigurator().apply(Robot.ctreConfigs.intakePivotFXConfig);
        resetIntakePivotEncoder();

    }

    public void configIntakeCANCoder() {


    }

    public double getIntakeVelocity() {

        return m_intake.getVelocity().getValueAsDouble();

    }

    public double getIntakePivotPosition() {

        return m_intakePivot.getPosition().getValueAsDouble();

    }

    public double getIntakePivotRotorPosition() {

        return m_intakePivot.getRotorPosition().getValueAsDouble();

    }

    public double getIntakeEncoderPosition() {

        return m_intakeEncoder.getAbsolutePosition().getValueAsDouble();

    }

    public enum IntakeState {

        RESET(Constants.INTAKE_PIVOT_RESET, Constants.INTAKE_RESET),
        INTAKE(Constants.INTAKE_PIVOT_INTAKE, Constants.INTAKE_INTAKE),
        SHOOT(Constants.INTAKE_PIVOT_FEED, Constants.INTAKE_RESET),
        AMP(Constants.INTAKE_RESET, Constants.INTAKE_AMP),
        CLIMB(Constants.INTAKE_PIVOT_CLIMB, Constants.INTAKE_RESET);

        public double intakeSetpoint;
        public double intakeVelocity;
        private IntakeState(double pivotSetpoint, double intakeVelocity) {
            intakeSetpoint = pivotSetpoint;
            this.intakeVelocity = intakeVelocity;
        }

    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Intake Velocity", getIntakeVelocity());
        SmartDashboard.putNumber("Intake Pivot Position", getIntakePivotPosition());
        SmartDashboard.putNumber("Intake Encoder Position", getIntakeEncoderPosition());
        SmartDashboard.putNumber("Intake Pivot Rotor Position", getIntakePivotRotorPosition());

    }
    
}
