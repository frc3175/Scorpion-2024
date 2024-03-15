package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.lib.util.COTSFalconSwerveConstants;
import frc.lib.util.SwerveModuleConstants;

public final class Constants {

    /*============================
               Swerve 
    ==============================*/

        /* Gyro Constants */
        public static final int PIGEON_ID = 19;
        public static final boolean INVERT_GYRO = false; // Always ensure Gyro is CCW+ CW-

        /* Chosen Module */
        public static final COTSFalconSwerveConstants chosenModule = 
            COTSFalconSwerveConstants.SDSMK4i(COTSFalconSwerveConstants.driveGearRatios.SDS_MK4i_L5);

        /* Drivetrain Constants */
        public static final double TRACK_WIDTH = Units.inchesToMeters(20.75);
        public static final double WHEEL_BASE = Units.inchesToMeters(20.75);
        public static final double WHEEL_CIRCUMFERENCE = chosenModule.wheelCircumference;

        /* Swerve Kinematics */
         public static final Translation2d[] moduleTranslations = new Translation2d[]{
            new Translation2d(WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0),
            new Translation2d(WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0),
            new Translation2d(-WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0),
            new Translation2d(-WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0)};

        public static final SwerveDriveKinematics SWERVE_KINEMATICS = new SwerveDriveKinematics(moduleTranslations);

        public static final double DRIVETRAIN_RADIUS = Units.inchesToMeters(14.67247);

        /* Module Gear Ratios */
        public static final double DRIVE_GEAR_RATIO = chosenModule.driveGearRatio;
        public static final double ANGLE_GEAR_RATIO = chosenModule.angleGearRatio;

        /* Motor Inverts */
        public static final InvertedValue ANGLE_MOTOR_INVERT = InvertedValue.Clockwise_Positive;
        public static final InvertedValue DRIVE_MOTOR_INVERT = InvertedValue.Clockwise_Positive;

        /* Angle Encoder Invert */
        public static final SensorDirectionValue CANCODER_INVERT = chosenModule.cancoderInvert;

        /* Swerve Current Limiting */
        public static final int AZIMUTH_CURRENT_LIMIT = 25;
        public static final int AZIMUTH_CURRENT_THRESHOLD = 40;
        public static final double AZIMUTH_CURRENT_THRESHOLD_TIME = 0.1;
        public static final boolean AZIMUTH_ENABLE_CURRENT_LIMIT = true;

        public static final int DRIVE_CURRENT_LIMIT = 50;   
        public static final int DRIVE_CURRENT_THRESHOLD = 60;
        public static final double DRIVE_CURRENT_THRESHOLD_TIME = 0.1;
        public static final boolean DRIVE_ENABLE_CURRENT_LIMIT = true;

        public static final double OPEN_LOOP_RAMP = 0.25;
        public static final double CLOSED_LOOP_RAMP = 0.0;

        /* Angle Motor PID Values */
        public static final double AZIMUTH_P = chosenModule.angleKP;
        public static final double AZIMUTH_I = chosenModule.angleKI;
        public static final double AZIMUTH_D = chosenModule.angleKD;

        /* Drive Motor PID Values */
        public static final double DRIVE_P = 0.12;
        public static final double DRIVE_I = 0.0;
        public static final double DRIVE_D = 0.0;
        public static final double DRIVE_F = 0.0;

        /* Drive Motor Characterization Values From SYSID */
        public static final double DRIVE_S = 0.32;
        public static final double DRIVE_V = 1.51;
        public static final double DRIVE_A = 0.27;

        public static final double RATE_LIMITER = 1.5;

        /* Swerve Profiling Values */
        /** Meters per Second */
        public static final double MAX_SPEED = Units.feetToMeters(23.25);
        /** Radians per Second */
        public static final double MAX_ANGULAR_VELOCITY = Math.PI * 4.12 * 0.5;

        /* Neutral Modes */
        public static final NeutralModeValue AZIMUTH_NEUTRAL_MODE = NeutralModeValue.Coast;
        public static final NeutralModeValue DRIVE_NEUTRAL_MODE = NeutralModeValue.Brake;

        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final int driveMotorID = 6;
            public static final int angleMotorID = 8;
            public static final int canCoderID = 10;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-11.77);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Front Right Module - Module 1 */
        public static final class Mod1 {
            public static final int driveMotorID = 11;
            public static final int angleMotorID = 9;
            public static final int canCoderID = 7;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(146.43);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }
        
        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            public static final int driveMotorID = 17;
            public static final int angleMotorID = 13;
            public static final int canCoderID = 4;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(134.73);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 { 
            public static final int driveMotorID = 14;
            public static final int angleMotorID = 5;
            public static final int canCoderID = 12;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(122.87);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

    /*============================
                Auton
    ==============================*/

        /* Auto Constraints */
        public static final double AUTO_MAX_SPEED_MPS = 3;
        public static final double AUTO_MAX_ACCELERATION_MPS_SQUARED = 3;
        public static final double AUTO_ANGULAR_SPEED = Math.PI;
        public static final double AUTO_ANGULAR_ACCELERATION = Math.PI * Math.PI;
    
        /* Auto PID Constants */
        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;

        /* Auto Side Offsets */
        public static final double AUTO_AMP_SIDE_START_OFFSET = 64.5; //todo adjust this
        public static final double AUTO_SOURCE_SIDE_START_OFFSET = -64.5; //todo adjust this
    
        /* Constraint for the motion profilied robot angle controller */
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                AUTO_ANGULAR_SPEED, AUTO_ANGULAR_ACCELERATION);

        /* Pathplanner Auton Config */
        public static final HolonomicPathFollowerConfig pathFollowerConfig = new HolonomicPathFollowerConfig(
            new PIDConstants(kPXController, 0, 0), // Translation constants 
            new PIDConstants(kPYController, 0, 0), // Rotation constants 
            MAX_SPEED, 
            Constants.DRIVETRAIN_RADIUS, // Drive base radius (distance from center to furthest module) 
            new ReplanningConfig()
        );

    /*============================
         Controller Constants
    ==============================*/

    public static final double stickDeadband = 0.1;
    public static final int DRIVER_PORT = 0;
    public static final int OPERATOR_PORT = 1;

    /*============================
               Feeder 
    ==============================*/

    /* Feeder ID */
    public static final int FEEDER_ID = 30;

    /* Feeder Iverts and Neutral Mode */
    public static final InvertedValue FEEDER_INVERTED = InvertedValue.Clockwise_Positive;
    public static final NeutralModeValue FEEDER_NEUTRAL_MODE = NeutralModeValue.Coast;

    /* Feeder Current Limits */
    public static final boolean FEEDER_ENABLE_CURRENT_LIMIT = true;
    public static final int FEEDER_SUPPLY_CURRENT_LIMIT = 40;
    public static final int FEEDER_SUPPLY_CURRENT_THRESHOLD = 60; 
    public static final double FEEDER_SUPPLY_TIME_THRESHOLD = 0.1;

    /* Feeder PID Constants */
    public static final double FEEDER_P = 0.01;
    public static final double FEEDER_I = 0;
    public static final double FEEDER_D = 0;
    public static final double FEEDER_V = 0.01;

    /* Feeder Velocity Setpoints - Rotations per Second */
    public static final double FEEDER_RESET = 0;
    public static final double FEEDER_SHOOT = 50;
    public static final double FEEDER_AMP = 40;
    public static final double FEEDER_OUTTAKE = 70;

    /*============================
               Shooter 
    ==============================*/

    /* Shooter ID */
    public static final int SHOOTER_ID = 19;

    /* Shooter Inverts and Neutral Mode */
    public static final InvertedValue SHOOTER_INVERTED = InvertedValue.CounterClockwise_Positive;
    public static final NeutralModeValue SHOOTER_NEUTRAL_MODE = NeutralModeValue.Coast;

    /* Shooter Current Limits */
    public static final boolean SHOOTER_ENABLE_CURRENT_LIMIT = true;
    public static final int SHOOTER_SUPPLY_CURRENT_LIMIT = 40;
    public static final int SHOOTER_SUPPLY_CURRENT_THRESHOLD = 60;
    public static final double SHOOTER_SUPPLY_TIME_THRESHOLD = 0.1;

    /* Shooter PID Constants */
    public static final double SHOOTER_P = 0.01;
    public static final double SHOOTER_I = 0;
    public static final double SHOOTER_D = 0;
    public static final double SHOOTER_V = 0.01;
    public static final double SHOOTER_A = 0;

    /* Shooter Velocities - Rotations per Second */
    public static final double SHOOTER_SHOOT = 50;
    public static final double SHOOTER_FAST_SPEED = 70;
    public static final double SHOOTER_AMP = 0;
    public static final double SHOOTER_RESET = 0;
    public static final double SHOOTER_OUTTAKE = 70;

    /*============================
            Shooter Pivot 
    ==============================*/

    /* Shooter ID */
    public static final int SHOOTER_PIVOT_ID = 23;

    /* Shooter Inverts and Neutral Mode */
    public static final InvertedValue SHOOTER_PIVOT_INVERTED = InvertedValue.Clockwise_Positive;
    public static final NeutralModeValue SHOOTER_PIVOT_NEUTRAL_MODE = NeutralModeValue.Brake;

    /* Shooter Pivot Current Limits */
    public static final boolean SHOOTER_PIVOT_ENABLE_CURRENT_LIMIT = true;
    public static final int SHOOTER_PIVOT_SUPPLY_CURRENT_LIMIT = 20;
    public static final int SHOOTER_PIVOT_SUPPLY_CURRENT_THRESHOLD = 40;
    public static final double SHOOTER_PIVOT_SUPPLY_TIME_THRESHOLD = 0.1;

    /* Shooter Pivot PID Constants */
    public static final double SHOOTER_PIVOT_P = 0.1;
    public static final double SHOOTER_PIVOT_I = 0;
    public static final double SHOOTER_PIVOT_D = 0;
    public static final double SHOOTER_PIVOT_V = 0.01;

    /* Shooter Pivot Motion Magic Constants */
    public static final double SHOOTER_PIVOT_CRUISE_VELOCITY = 75;
    public static final double SHOOTER_PIVOT_ACCELERATION = 140;
    //public static final double SHOOTER_PIVOT_JERK = 80;

    /* Shooter Pivot Positions */
    public static final int SHOOTER_PIVOT_RESET = 0;
    public static final double SHOOTER_PIVOT_SHOOT = 0; //FIXME: if you are TESTING shooter setpoints to tune a distance, TEMPORARILY adjust this number and then put it back to zero!!!
    public static final double SHOOTER_PIVOT_AMP = 17; //16.5
    public static final double SHOOTER_PIVOT_CLIMB = 16.5;
    public static final double SHOOTER_PIVOT_ROBOT_DISTANCE = 3.4;
    public static final double SHOOTER_PIVOT_PODIUM = 5.52;
    public static final double AMP_BOOP = 21;

    /* Delays */
    public static final double AMP_PIVOT_DELAY = 0.3;

    /*============================
            Shooter Encoder 
    ==============================*/

    /* Shooter CANcoder ID */
    public static final int SHOOTER_CANCODER_ID = 3;

    /* Shooter CANcoder Invert */
    public static final SensorDirectionValue SHOOTER_CANCODER_INVERT = SensorDirectionValue.Clockwise_Positive;

    /* Shooter CANcoder Offset */
    public static final double SHOOTER_CANCODER_OFFSET = 0;

     /*============================
               Intake 
    ==============================*/

    /* Intake ID */
    public static final int INTAKE_ID = 20;

    /* Intake Inverts and Neutral Mode */
    public static final InvertedValue INTAKE_INVERTED = InvertedValue.CounterClockwise_Positive;
    public static final NeutralModeValue INTAKE_NEUTRAL_MODE = NeutralModeValue.Coast;

    /* Intake Current Limits */
    public static final boolean INTAKE_ENABLE_CURRENT_LIMIT = true;
    public static final int INTAKE_SUPPLY_CURRENT_LIMIT = 20;
    public static final int INTAKE_SUPPLY_CURRENT_THRESHOLD = 30;
    public static final double INTAKE_SUPPLY_TIME_THRESHOLD = 0.1;

    /* Intake PID Constants */
    public static final double INTAKE_P = 0.01;
    public static final double INTAKE_I = 0;
    public static final double INTAKE_D = 0;
    public static final double INTAKE_V = 0.4;

    /* Intake Velocities - Rotations per Second */
    public static final double INTAKE_RESET = 0;
    public static final double INTAKE_INTAKE = 20;
    public static final double INTAKE_SHOOT = 0;
    public static final double INTAKE_FEED_SHOOT = -10;
    public static final double INTAKE_AMP = -40;

    /*============================
            Intake Pivot 
    ==============================*/

    /* Intake ID */
    public static final int INTAKE_PIVOT_ID = 22;

    /* Intake Inverts and Neutral Mode */
    public static final InvertedValue INTAKE_PIVOT_INVERTED = InvertedValue.CounterClockwise_Positive;
    public static final NeutralModeValue INTAKE_PIVOT_NEUTRAL_MODE = NeutralModeValue.Brake;

    /* Intake Pivot Current Limits */
    public static final boolean INTAKE_PIVOT_ENABLE_CURRENT_LIMIT = true;
    public static final int INTAKE_PIVOT_SUPPLY_CURRENT_LIMIT = 30;
    public static final int INTAKE_PIVOT_SUPPLY_CURRENT_THRESHOLD = 30;
    public static final double INTAKE_PIVOT_SUPPLY_TIME_THRESHOLD = 0.1;

    /* Intake Pivot PID Constants */
    public static final double INTAKE_PIVOT_P = 0.1; //0.1
    public static final double INTAKE_PIVOT_I = 0;
    public static final double INTAKE_PIVOT_D = 0;
    public static final double INTAKE_PIVOT_V = 0.1; //0.01

    /* Intake Pivot Setpoints */
    public static final int INTAKE_PIVOT_RESET = 0;
    public static final double INTAKE_PIVOT_INTAKE = 24;
    public static final int INTAKE_PIVOT_CLIMB = 10;
    public static final double INTAKE_PIVOT_FEED = 0; //FIXME: in order to TEST intake pivot angles for certain shooting distances, adjust this value TEMPORARILY and then put it back to 0!!
    public static final double INTAKE_PIVOT_ROBOT_DISTANCE = 2.1;
    public static final double INTAKE_PIVOT_PODIUM = 2.7;

    /* Intake Pivot Motion Magic */
    public static final double INTAKE_PIVOT_CRUISE_VELOCITY = 95;
    public static final double INTAKE_PIVOT_ACCELERATION = 140;
    //public static final double INTAKE_PIVOT_JERK = 100;

    /*============================
            Intake Encoder 
    ==============================*/

    /* Intake CANcoder ID */
    public static final int INTAKE_CANCODER_ID = 2;

    /* Intake CANcoder Invert */
    public static final SensorDirectionValue INTAKE_CANCODER_INVERT = SensorDirectionValue.Clockwise_Positive;

    /* Intake CANcoder Offset */
    public static final double INTAKE_CANCODER_OFFSET = 0.16;

    /*============================
               Climber 
    ==============================*/

    /* Climber ID */
    public static final int CLIMBER_ID = 10;

    /* Climber Inverts and Neutral Mode */
    public static final InvertedValue CLIMBER_INVERTED = InvertedValue.Clockwise_Positive;
    public static final NeutralModeValue CLIMBER_NEUTRAL_MODE = NeutralModeValue.Brake;

    /* Climber Current Limits */
    public static final boolean CLIMBER_ENABLE_CURRENT_LIMIT = true;
    public static final int CLIMBER_SUPPLY_CURRENT_LIMIT = 35;
    public static final int CLIMBER_SUPPLY_CURRENT_THRESHOLD = 50;
    public static final double CLIMBER_SUPPLY_TIME_THRESHOLD = 0.1;

    /* Climber PID Constants */
    public static final double CLIMBER_P = 0.1;
    public static final double CLIMBER_I = 0;
    public static final double CLIMBER_D = 0;

    /* Climber Positions - Rotations */
    public static final double CLIMBER_UP = 220; //FIXME: climber up setpoint
    public static final double CLIMBER_DOWN = 0; //FIXME: climber down setpoint

    /*
     * 
     * How to tune climber setpoint:
     * 1. go into phoenix tuner
     * 2. make sure the robot is enabled in both driver station and phoenix tuner
     * 3. run the motor in the NEGATIVE direction until it is all the way down
     * 4. power cycle the robot
     * 5. run the robot in phoenix tuner in the POSITIVE direction until it is all the way up
     * 6. look at shuffleboard under the entry "climber position"
     * 7. put this number in line 398 next to CLIMBER_UP
     * 8. run the climber in the NEGATIVE direction until it goes to the down position 
     * 9. look at shuffleboard under the entry "climber position"
     * 10. put this number in line 399 next to CLIMBER_DOWN
     * 11. the down setpoint will probably be close to 0 but you might not want to go all the way to 0 in case it overshoots bc we don't want it breaking the string
     * 
     */

    public static final double CLIMBER_RESET = 0;

    /*============================
                LEDs
    ==============================*/

    public static final int CANDLE_ID = 6;

    /*============================
                Vision
    ==============================*/

    public static final int PRIORITY_ID = 7;
    public static final double LIMELIGHT_MOUNTING_ANGLE = 28;
    public static final double LIMELIGHT_LENS_HEIGHT = 21.25;
    public static final double TARGET_HEIGHT = 53.88;









}
