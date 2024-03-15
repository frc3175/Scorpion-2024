package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.util.InterpolatingTreeMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

@SuppressWarnings("all")
public class Shooter extends SubsystemBase {

    private TalonFX m_shooter;
    private TalonFX m_shooterPivot;

    private VelocityDutyCycle shooterVelocity = new VelocityDutyCycle(0);
    private DutyCycleOut shooterPercentOutput = new DutyCycleOut(0);
    private MotionMagicDutyCycle shooterPivotPosition = new MotionMagicDutyCycle(0);
    private DutyCycleOut shooterPivotPercentOutput = new DutyCycleOut(0);

   private InterpolatingTreeMap<Double, Double> shooterPivotTable = new InterpolatingTreeMap<>();

   private boolean isInInterpolationMode;

    public Shooter() {

        m_shooter = new TalonFX(Constants.SHOOTER_ID, "elevatoryiboi");
        m_shooterPivot = new TalonFX(Constants.SHOOTER_PIVOT_ID, "elevatoryiboi");

        shooterPivotTable.put(39.25, 0.0);
        shooterPivotTable.put(72.25, 3.4);
        shooterPivotTable.put(144.25, 5.52); //FIXME: the values in this line are guesses and inaccurate
        //FIXME: this is where you add interpolation values. instructions are below

        /*
         * 
         * To add a value to the interpolation table, create a new line and copy paste this information
         * shooterPivotTable.put(0, 1);
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

        configShooterMotor();
        configShooterPivotMotor();

        isInInterpolationMode = false;

    }

    public void setInteroplationMode(boolean isInterpolating) {

        isInInterpolationMode = isInterpolating;

    }

    public boolean getInterpolationMode() {

        return isInInterpolationMode;

    }

    public void shooterInterpolate(double ty) {

        double position = shooterPivotTable.get(ty);
        shooterPivotPosition.Position = position;
        m_shooterPivot.setControl(shooterPivotPosition);

    }

    public void shooterRun(double velocity) {

        shooterVelocity.Velocity = velocity;
        m_shooter.setControl(shooterVelocity);

    }

    public void shooterPercentOutput(double percentOutput) {

        shooterPercentOutput.Output = percentOutput;
        m_shooter.setControl(shooterPercentOutput);

    }

    public void shooterPivot(double position) {

        shooterPivotPosition.Position = position;
        m_shooterPivot.setControl(shooterPivotPosition);

    }

    public void shooterPivotPercentOutput(double percentOutput) {

        shooterPivotPercentOutput.Output = percentOutput;
        m_shooterPivot.setControl(shooterPivotPercentOutput);

    }

    public void resetShooterEncoder() {

        m_shooter.setPosition(0);

    }

    public void resetShooterPivotEncoder() {

        m_shooterPivot.setPosition(0);

    }

    public void configShooterMotor() {

        m_shooter.getConfigurator().apply(new TalonFXConfiguration());
        m_shooter.getConfigurator().apply(Robot.ctreConfigs.shooterFXConfig);
        resetShooterEncoder();

    }

    public void configShooterPivotMotor() {

        m_shooterPivot.getConfigurator().apply(new TalonFXConfiguration());
        m_shooterPivot.getConfigurator().apply(Robot.ctreConfigs.shooterPivotFXConfig);
        resetShooterPivotEncoder();

    }

    public double getShooterVelocity() {

        return m_shooter.getRotorVelocity().getValueAsDouble();

    }

    public double getShooterPivotPosition() {

        return m_shooterPivot.getPosition().getValueAsDouble();

    }

    public double getShooterPivotRotorPosition() {

        return m_shooterPivot.getRotorPosition().getValueAsDouble();

    }

    public enum ShooterState {

        RESET(Constants.SHOOTER_PIVOT_RESET, Constants.SHOOTER_RESET),
        SHOOT(Constants.SHOOTER_PIVOT_SHOOT, Constants.SHOOTER_SHOOT),
        AMP(Constants.SHOOTER_PIVOT_AMP, Constants.SHOOTER_AMP),
        CLIMB(Constants.SHOOTER_PIVOT_CLIMB, Constants.SHOOTER_RESET);

        public double shooterPivot;
        public double shooterVelocity;
        private ShooterState(double shooterPivot, double shooterVelocity) {
            this.shooterPivot = shooterPivot;
            this.shooterVelocity = shooterVelocity;
        }

    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Shooter Velocity", getShooterVelocity());
        SmartDashboard.putNumber("Shooter Pivot Position", getShooterPivotPosition());
        SmartDashboard.putNumber("Shooter Pivot Rotor Position", getShooterPivotRotorPosition());

    }
    
}
