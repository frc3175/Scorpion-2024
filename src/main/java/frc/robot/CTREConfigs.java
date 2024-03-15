package frc.robot;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;

public final class CTREConfigs {

    public TalonFXConfiguration swerveAngleFXConfig = new TalonFXConfiguration();
    public TalonFXConfiguration swerveDriveFXConfig = new TalonFXConfiguration();
    public CANcoderConfiguration swerveCANcoderConfig = new CANcoderConfiguration();
    public TalonFXConfiguration feederFXConfig = new TalonFXConfiguration();
    public TalonFXConfiguration shooterFXConfig = new TalonFXConfiguration();
    public TalonFXConfiguration shooterPivotFXConfig = new TalonFXConfiguration();
    public CANcoderConfiguration shooterCANcoderConfig = new CANcoderConfiguration();
    public TalonFXConfiguration intakeFXConfig = new TalonFXConfiguration();
    public TalonFXConfiguration intakePivotFXConfig = new TalonFXConfiguration();
    public CANcoderConfiguration intakeCANcoderConfig = new CANcoderConfiguration();
    public TalonFXConfiguration climberFXConfig = new TalonFXConfiguration();

    public CTREConfigs(){

        /*============================
               Swerve Azimuth
        ==============================*/

        /* Angle Motor Inverts and Neutral Mode */
        MotorOutputConfigs angleMotorOutput = swerveAngleFXConfig.MotorOutput;
        angleMotorOutput.Inverted = Constants.ANGLE_MOTOR_INVERT;
        angleMotorOutput.NeutralMode = Constants.AZIMUTH_NEUTRAL_MODE;
        
        /* Angle Current Limiting */
        CurrentLimitsConfigs angleCurrentLimits = swerveAngleFXConfig.CurrentLimits;
        angleCurrentLimits.SupplyCurrentLimitEnable = Constants.AZIMUTH_ENABLE_CURRENT_LIMIT;
        angleCurrentLimits.SupplyCurrentLimit = Constants.AZIMUTH_CURRENT_LIMIT;
        angleCurrentLimits.SupplyCurrentThreshold = Constants.AZIMUTH_CURRENT_THRESHOLD;
        angleCurrentLimits.SupplyTimeThreshold = Constants.AZIMUTH_CURRENT_THRESHOLD_TIME;

        /* Angle PID Config */
        Slot0Configs angleSlot0 = swerveAngleFXConfig.Slot0;
        angleSlot0.kP = Constants.AZIMUTH_P;
        angleSlot0.kI = Constants.AZIMUTH_I;
        angleSlot0.kD = Constants.AZIMUTH_D;

        /*============================
               Swerve Drive
        ==============================*/

        /* Drive Motor Inverts and Neutral Mode */
        var driveMotorOutput = swerveDriveFXConfig.MotorOutput;
        driveMotorOutput.Inverted = Constants.DRIVE_MOTOR_INVERT;
        driveMotorOutput.NeutralMode = Constants.DRIVE_NEUTRAL_MODE;

        /* Drive Current Limiting */
        var driveCurrentLimits = swerveDriveFXConfig.CurrentLimits;
        driveCurrentLimits.SupplyCurrentLimitEnable = Constants.DRIVE_ENABLE_CURRENT_LIMIT;
        driveCurrentLimits.SupplyCurrentLimit = Constants.DRIVE_CURRENT_LIMIT;
        driveCurrentLimits.SupplyCurrentThreshold = Constants.DRIVE_CURRENT_THRESHOLD;
        driveCurrentLimits.SupplyTimeThreshold = Constants.DRIVE_CURRENT_THRESHOLD_TIME;

        /* Drive PID Config */
        var driveSlot0 = swerveDriveFXConfig.Slot0;
        driveSlot0.kP = Constants.DRIVE_P;
        driveSlot0.kI = Constants.DRIVE_I;
        driveSlot0.kD = Constants.DRIVE_D;

        /* Drive Open and Closed Loop Ramping */
        swerveDriveFXConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = Constants.OPEN_LOOP_RAMP;
        swerveDriveFXConfig.OpenLoopRamps.VoltageOpenLoopRampPeriod = Constants.OPEN_LOOP_RAMP;

        swerveDriveFXConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = Constants.CLOSED_LOOP_RAMP;
        swerveDriveFXConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = Constants.CLOSED_LOOP_RAMP;

        /*============================
               Swerve CANCoder
        ==============================*/
        
        /** Swerve CANCoder Configuration */
        swerveCANcoderConfig.MagnetSensor.SensorDirection = Constants.CANCODER_INVERT;

        /*============================
                    Feeder
        ==============================*/

        /* Feeder Output and Neutral Mode */
        var feederOutput = feederFXConfig.MotorOutput;
        feederOutput.Inverted = Constants.FEEDER_INVERTED;
        feederOutput.NeutralMode = Constants.FEEDER_NEUTRAL_MODE;

        /* Feeder Current Limits */
        var feederCurrentLimits = feederFXConfig.CurrentLimits;
        feederCurrentLimits.SupplyCurrentLimitEnable = Constants.FEEDER_ENABLE_CURRENT_LIMIT;
        feederCurrentLimits.SupplyCurrentLimit = Constants.FEEDER_SUPPLY_CURRENT_LIMIT;
        feederCurrentLimits.SupplyCurrentThreshold = Constants.FEEDER_SUPPLY_CURRENT_THRESHOLD;
        feederCurrentLimits.SupplyTimeThreshold = Constants.FEEDER_SUPPLY_TIME_THRESHOLD;

        /* Feeder PID config */
        var feederSlot0 = feederFXConfig.Slot0;
        feederSlot0.kP = Constants.FEEDER_P;
        feederSlot0.kI = Constants.FEEDER_I;
        feederSlot0.kD = Constants.FEEDER_D;
        feederSlot0.kV = Constants.FEEDER_V;

        /*============================
                Shooter Motor
        ==============================*/

        /* Shooter Output and Neutral Mode */
        var shooterOutput = shooterFXConfig.MotorOutput;
        shooterOutput.Inverted = Constants.SHOOTER_INVERTED;
        shooterOutput.NeutralMode = Constants.SHOOTER_NEUTRAL_MODE;

        /* Shooter Current Limits */
        var shooterCurrentLimits = shooterFXConfig.CurrentLimits;
        shooterCurrentLimits.SupplyCurrentLimitEnable = Constants.SHOOTER_ENABLE_CURRENT_LIMIT;
        shooterCurrentLimits.SupplyCurrentLimit = Constants.SHOOTER_SUPPLY_CURRENT_LIMIT;
        shooterCurrentLimits.SupplyCurrentThreshold = Constants.SHOOTER_SUPPLY_CURRENT_THRESHOLD;
        shooterCurrentLimits.SupplyTimeThreshold = Constants.SHOOTER_SUPPLY_TIME_THRESHOLD;

        /* Shooter PID Config */
        var shooterSlot0 = shooterFXConfig.Slot0;
        shooterSlot0.kP = Constants.SHOOTER_P;
        shooterSlot0.kI = Constants.SHOOTER_I;
        shooterSlot0.kD = Constants.SHOOTER_D;
        shooterSlot0.kV = Constants.SHOOTER_V;
        shooterSlot0.kA = Constants.SHOOTER_A;

        /*============================
                Shooter Pivot
        ==============================*/

        /* Shooter Pivot Output and Neutral Mode */
        var shooterPivotOutput = shooterPivotFXConfig.MotorOutput;
        shooterPivotOutput.Inverted = Constants.SHOOTER_PIVOT_INVERTED;
        shooterPivotOutput.NeutralMode = Constants.SHOOTER_PIVOT_NEUTRAL_MODE;

        /* Shooter Pivot Current Limits */
        var shooterPivotCurrentLimits = shooterPivotFXConfig.CurrentLimits;
        shooterPivotCurrentLimits.SupplyCurrentLimitEnable = Constants.SHOOTER_PIVOT_ENABLE_CURRENT_LIMIT;
        shooterPivotCurrentLimits.SupplyCurrentLimit = Constants.SHOOTER_PIVOT_SUPPLY_CURRENT_LIMIT;
        shooterPivotCurrentLimits.SupplyCurrentThreshold = Constants.SHOOTER_PIVOT_SUPPLY_CURRENT_THRESHOLD;
        shooterPivotCurrentLimits.SupplyTimeThreshold = Constants.SHOOTER_PIVOT_SUPPLY_TIME_THRESHOLD;

        /* Shooter Pivot PID Config */
        var shooterPivotSlot0 = shooterPivotFXConfig.Slot0;
        shooterPivotSlot0.kP = Constants.SHOOTER_PIVOT_P;
        shooterPivotSlot0.kI = Constants.SHOOTER_PIVOT_I;
        shooterPivotSlot0.kD = Constants.SHOOTER_PIVOT_D;
        shooterPivotSlot0.kV = Constants.SHOOTER_PIVOT_V;

        var shooterPivotMotionMagic = shooterPivotFXConfig.MotionMagic;
        shooterPivotMotionMagic.MotionMagicCruiseVelocity = Constants.SHOOTER_PIVOT_CRUISE_VELOCITY;
        shooterPivotMotionMagic.MotionMagicAcceleration = Constants.SHOOTER_PIVOT_ACCELERATION;

        /* Shooter Pivot Feedback Config */
        //var shooterPivotFeedback = shooterPivotFXConfig.Feedback;
        /* shooterPivotFeedback.FeedbackRemoteSensorID = Constants.SHOOTER_CANCODER_ID;
        shooterPivotFeedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;
        shooterPivotFeedback.SensorToMechanismRatio = 0.4;
        shooterPivotFeedback.RotorToSensorRatio = 0.1; */
 
        /*============================
               Shooter CANCoder
        ==============================*/

        /* Shooter CANcoder Configuration */
        shooterCANcoderConfig.MagnetSensor.SensorDirection = Constants.SHOOTER_CANCODER_INVERT;
        shooterCANcoderConfig.MagnetSensor.MagnetOffset = Constants.SHOOTER_CANCODER_OFFSET;

        /*============================
                Intake Motor
        ==============================*/

        /* Intake Output and Neutral Mode */
        var intakeOutput = intakeFXConfig.MotorOutput;
        intakeOutput.Inverted = Constants.INTAKE_INVERTED;
        intakeOutput.NeutralMode = Constants.INTAKE_NEUTRAL_MODE;

        /* Intake Current Limits */
        var intakeCurrentLimits = intakeFXConfig.CurrentLimits;
        intakeCurrentLimits.SupplyCurrentLimitEnable = Constants.INTAKE_ENABLE_CURRENT_LIMIT;
        intakeCurrentLimits.SupplyCurrentLimit = Constants.INTAKE_SUPPLY_CURRENT_LIMIT;
        intakeCurrentLimits.SupplyCurrentThreshold = Constants.INTAKE_SUPPLY_CURRENT_THRESHOLD;
        intakeCurrentLimits.SupplyTimeThreshold = Constants.INTAKE_SUPPLY_TIME_THRESHOLD;

        /* Intake PID Config */
        var intakeSlot0 = intakeFXConfig.Slot0;
        intakeSlot0.kP = Constants.INTAKE_P;
        intakeSlot0.kI = Constants.INTAKE_I;
        intakeSlot0.kD = Constants.INTAKE_D;
        intakeSlot0.kV = Constants.INTAKE_V;

       /*============================
                Intake Pivot
        ==============================*/

        /* Intake Pivot Output and Neutral Mode */
        var intakePivotOutput = intakePivotFXConfig.MotorOutput;
        intakePivotOutput.Inverted = Constants.INTAKE_PIVOT_INVERTED;
        intakePivotOutput.NeutralMode = Constants.INTAKE_PIVOT_NEUTRAL_MODE;

        /* Intake Pivot Current Limits */
        var intakePivotCurrentLimits = intakePivotFXConfig.CurrentLimits;
        intakePivotCurrentLimits.SupplyCurrentLimitEnable = Constants.INTAKE_PIVOT_ENABLE_CURRENT_LIMIT;
        intakePivotCurrentLimits.SupplyCurrentLimit = Constants.INTAKE_PIVOT_SUPPLY_CURRENT_LIMIT;
        intakePivotCurrentLimits.SupplyCurrentThreshold = Constants.INTAKE_PIVOT_SUPPLY_CURRENT_THRESHOLD;
        intakePivotCurrentLimits.SupplyTimeThreshold = Constants.INTAKE_PIVOT_SUPPLY_TIME_THRESHOLD;

        /* Intake Pivot PID Config */
        var intakePivotSlot0 = intakePivotFXConfig.Slot0;
        intakePivotSlot0.kP = Constants.INTAKE_PIVOT_P;
        intakePivotSlot0.kI = Constants.INTAKE_PIVOT_I;
        intakePivotSlot0.kD = Constants.INTAKE_PIVOT_D;
        intakePivotSlot0.kV = Constants.INTAKE_PIVOT_V;

        /* Intake Pivot Feedback Config */
        //var intakePivotFeedback = intakePivotFXConfig.Feedback;
        /* intakePivotFeedback.FeedbackRemoteSensorID = Constants.INTAKE_CANCODER_ID;
        intakePivotFeedback.FeedbackSensorSource = FeedbackSensorSourceValue.SyncCANcoder;
        intakePivotFeedback.SensorToMechanismRatio = 1;
        intakePivotFeedback.RotorToSensorRatio = 25; */

        var intakePivotMotionMagic = intakePivotFXConfig.MotionMagic;
        intakePivotMotionMagic.MotionMagicCruiseVelocity = Constants.INTAKE_PIVOT_CRUISE_VELOCITY;
        intakePivotMotionMagic.MotionMagicAcceleration = Constants.INTAKE_PIVOT_ACCELERATION;

       /*============================
               Intake CANCoder
        ==============================*/

        /* Intake CANcoder Configuration */
        intakeCANcoderConfig.MagnetSensor.SensorDirection = Constants.INTAKE_CANCODER_INVERT;
        intakeCANcoderConfig.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;
        intakeCANcoderConfig.MagnetSensor.MagnetOffset = Constants.INTAKE_CANCODER_OFFSET;

        /*============================
                Climber Motor
        ==============================*/

        /* Climber Output and Neutral Mode */
        var climberOutput = climberFXConfig.MotorOutput;
        climberOutput.Inverted = Constants.CLIMBER_INVERTED;
        climberOutput.NeutralMode = Constants.CLIMBER_NEUTRAL_MODE;

        /* Climber Current Limits */
        var climberCurrentLimits = climberFXConfig.CurrentLimits;
        climberCurrentLimits.SupplyCurrentLimitEnable = Constants.CLIMBER_ENABLE_CURRENT_LIMIT;
        climberCurrentLimits.SupplyCurrentLimit = Constants.CLIMBER_SUPPLY_CURRENT_LIMIT;
        climberCurrentLimits.SupplyCurrentThreshold = Constants.CLIMBER_SUPPLY_CURRENT_THRESHOLD;
        climberCurrentLimits.SupplyTimeThreshold = Constants.CLIMBER_SUPPLY_TIME_THRESHOLD;

        /* Climber PID Config */
        var climberSlot0 = climberFXConfig.Slot0;
        climberSlot0.kP = Constants.CLIMBER_P;
        climberSlot0.kI = Constants.CLIMBER_I;
        climberSlot0.kD = Constants.CLIMBER_D;

    }

}