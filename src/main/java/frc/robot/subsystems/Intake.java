package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.util.InterpolatingTreeMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

@SuppressWarnings("all")
public class Intake extends SubsystemBase {

    private TalonFX m_intake;
    private TalonFX m_intakePivot;
    private CANcoder m_intakeEncoder;

    private VelocityDutyCycle intakeVelocity = new VelocityDutyCycle(0);
    private DutyCycleOut intakePercentOutput = new DutyCycleOut(0);
    private PositionDutyCycle intakePivotPosition = new PositionDutyCycle(0);
    private DutyCycleOut intakePivotPercentOutput = new DutyCycleOut(0);

    private InterpolatingTreeMap<Double, Double> intakePivotTable = new InterpolatingTreeMap<>();

    public Intake() {

        m_intake = new TalonFX(Constants.INTAKE_ID, "elevatoryiboi");
        m_intakePivot = new TalonFX(Constants.INTAKE_PIVOT_ID, "elevatoryiboi");
        m_intakeEncoder = new CANcoder(Constants.INTAKE_CANCODER_ID, "elevatoryiboi");

        intakePivotTable.put(40.0, 0.0);
        intakePivotTable.put(53.5, 1.66);
        intakePivotTable.put(63.0, 2.83);
        intakePivotTable.put(83.5, 3.0);
        intakePivotTable.put(94.0, 3.25);
        intakePivotTable.put(94.0, 3.5);
        intakePivotTable.put(142.5, 3.5);
        //FIXME: the values in this line are guesses and inaccurate
        //FIXME: this is where you add interpolation values. instructions are below


        /*94
         * 
         * To add a value to the interpolation table, create a new line and copy paste this information
         * intakePivotTable.put(0, 1);
         * Replace the 0 with the distance from the goal
         * Note that this is distance from the actual speeaker, not the distance from the subwoofer
         * Replace the 1 with the shooter pivot setpoint that corresponds to that distance
         * 
         * Testing shooter distance instructions:
         * 1. put controller into test mode (controller 5) 
         * 2. measure the distance from the robot to the SPEAKER (NOT SUBWOOFER)
         * 3. adjust constants.java lines 356 and 274 to test different shooter pivot and intake angles
         * 4. adjust robotcontainer.java lines 161 and 163 to whatever shooter speed you want (note that it is in percent output mode)
         * 5. tune constants.java lines 356 and 274 until satisfied
         * 6. add a line to shooter.java around line 36 following the instructions above
         * 7. add a line to intake.java around line 38 following the instructions above
         * 8. the velocity is automatically increased when vision is activated and the robot is a few inches away from the sub, so don't worry about making the velocity 0
         * 9. make constants.java lines 356 and 274 zero when finished
         * 10. return robotcontainer.java lines 161 and 163 to 0.25
         * 
         */


        configIntakeCANCoder();
        configIntakeMotor();
        configIntakePivotMotor();

    }

    public void interpolateIntake(double ty) {

        double position = intakePivotTable.get(ty);
        intakePivotPosition.Position = position;
        m_intakePivot.setControl(intakePivotPosition);

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

    public double getIntakeStatorCurrent() {

        return m_intake.getStatorCurrent().getValueAsDouble();

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
        SmartDashboard.putNumber("Intake current", m_intake.getStatorCurrent().getValueAsDouble());

    }
    
}
