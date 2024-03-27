package frc.robot.subsystems;

import frc.lib.geometry.Translation2dPlus;
import frc.robot.Constants;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import java.util.Arrays;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrivetrain extends SubsystemBase {

    private static final Translation2d[] WHEEL_POSITIONS =
        Arrays.copyOf(Constants.moduleTranslations, Constants.moduleTranslations.length);;

    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] m_swerveMods;
    public Pigeon2 m_gyro;

    public SwerveDrivetrain() {

        m_gyro = new Pigeon2(Constants.PIGEON_ID);
        m_gyro.getConfigurator().apply(new Pigeon2Configuration());
        zeroGyro();

        m_swerveMods = new SwerveModule[] {
            new SwerveModule(0, Constants.Mod0.constants),
            new SwerveModule(1, Constants.Mod1.constants),
            new SwerveModule(2, Constants.Mod2.constants),
            new SwerveModule(3, Constants.Mod3.constants)
        };

        swerveOdometry = new SwerveDriveOdometry(Constants.SWERVE_KINEMATICS, getYaw(), getModulePositions());

        AutoBuilder.configureHolonomic(this::getPose, 
                                       this::resetOdometry, 
                                       this::getSpeeds, 
                                       this::driveRobotRelative, 
                                       Constants.pathFollowerConfig, 
                                       () -> {
                                            // Boolean supplier that controls when the path will be mirrored for the red alliance
                                            // This will flip the path being followed to the red side of the field.
                                            // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                                            var alliance = DriverStation.getAlliance();
                                            if (alliance.isPresent()) {
                                            return alliance.get() == DriverStation.Alliance.Red;
                                            }
                                            return false;
                                        }, 
                                        this);
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop, boolean isEvading, boolean isLocked) {
        
        if(isLocked) {

            final SwerveModuleState[] swerveModuleStates = new SwerveModuleState[] {
                new SwerveModuleState(0.1, Rotation2d.fromDegrees(45)),
                new SwerveModuleState(0.1, Rotation2d.fromDegrees(315)),
                new SwerveModuleState(0.1, Rotation2d.fromDegrees(135)),
                new SwerveModuleState(0.1, Rotation2d.fromDegrees(225))
            };

            for(SwerveModule mod : m_swerveMods){
                mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
            }

        } else {

            final Translation2d centerOfRotation;

            if(isEvading && fieldRelative) {
                centerOfRotation = getCenterOfRotation(translation.getAngle(), rotation);
            } else {
                centerOfRotation = new Translation2d();
            }

            final ChassisSpeeds chassisSpeeds;
        
            if(fieldRelative) {

                chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                    translation.getX(), 
                    translation.getY(), 
                    rotation, 
                    getYaw()
                    );

            } else {

                chassisSpeeds = new ChassisSpeeds(
                    translation.getX(), 
                    translation.getY(), 
                    rotation);
            } 

            final var swerveModuleStates = Constants.SWERVE_KINEMATICS.toSwerveModuleStates(chassisSpeeds, centerOfRotation);
            SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.MAX_SPEED);

            for(SwerveModule mod : m_swerveMods){
                mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
            }

        } 

    }  
    
    public void lockWheels() {

        final SwerveModuleState[] swerveModuleStates = new SwerveModuleState[] {
                new SwerveModuleState(0.1, Rotation2d.fromDegrees(45)),
                new SwerveModuleState(0.1, Rotation2d.fromDegrees(315)),
                new SwerveModuleState(0.1, Rotation2d.fromDegrees(135)),
                new SwerveModuleState(0.1, Rotation2d.fromDegrees(225))
        };

        for(SwerveModule mod : m_swerveMods){
                mod.setDesiredState(swerveModuleStates[mod.moduleNumber], true);
         }

    }

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.MAX_SPEED);
        
        for(SwerveModule mod : m_swerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }  
    
    private Translation2d getCenterOfRotation(final Rotation2d direction, final double rotation) {
        final var here = new Translation2dPlus(1.0, direction.minus(getYaw()));

        var cwCenter = WHEEL_POSITIONS[0];
        var ccwCenter = WHEEL_POSITIONS[WHEEL_POSITIONS.length - 1];

        for (int i = 0; i < WHEEL_POSITIONS.length - 1; i++) {
            final var cw = WHEEL_POSITIONS[i];
            final var ccw = WHEEL_POSITIONS[i + 1];

            if (here.isWithinAngle(cw, ccw)) {
                cwCenter = ccw;
                ccwCenter = cw;
            }
        }

        // if clockwise
        if (Math.signum(rotation) == 1.0) {
            return cwCenter;
        } else if (Math.signum(rotation) == -1.0) {
            return ccwCenter;
        } else {
            return new Translation2d();
        }
    }

    public double alignToTarget(double tx, double distanceToTarget) {

        double kP = Constants.LIMELIGHT_P;

        double distanceToGoal = distanceToTarget;
        double cameraOffset = Constants.CAMERA_OFFSET;
        double errorRadians = Math.atan(distanceToGoal/cameraOffset);
        double errorDegrees = errorRadians * (180/3.14159);

        double cameraToTargetDegrees = tx;
        double centerToTargetDegrees = cameraToTargetDegrees + errorDegrees;

        // tx ranges from (-hfov/2) to (hfov/2) in degrees. If your target is on the rightmost edge of 
        // your limelight 3 feed, tx should return roughly 31 degrees.
        double targetingAngularVelocity = centerToTargetDegrees * kP;

        // convert to radians per second for our drive method
        targetingAngularVelocity *= (Constants.MAX_ANGULAR_VELOCITY);

        //invert since tx is positive when the target is to the right of the crosshair
        targetingAngularVelocity *= -1.0;

        return targetingAngularVelocity;

    }

    public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds) {

        ChassisSpeeds targetSpeeds = ChassisSpeeds.discretize(robotRelativeSpeeds, 0.02);
    
        SwerveModuleState[] targetStates = Constants.SWERVE_KINEMATICS.toSwerveModuleStates(targetSpeeds);
        setModuleStates(targetStates);

    }

    public ChassisSpeeds getSpeeds() {

        return Constants.SWERVE_KINEMATICS.toChassisSpeeds(getModuleStates());

    }

    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getYaw(), getModulePositions(), pose);
    }

    public SwerveModuleState[] getModuleStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : m_swerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : m_swerveMods){
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public void zeroGyro(){
        m_gyro.setYaw(0);
    }

    public void adjustGyroZeroSourceSide() {

        m_gyro.setYaw(m_gyro.getYaw().getValueAsDouble() + Constants.AUTO_SOURCE_SIDE_START_OFFSET);

    }

     public void adjustGyroZeroAmpSide() {

        m_gyro.setYaw(m_gyro.getYaw().getValueAsDouble() + Constants.AUTO_AMP_SIDE_START_OFFSET);

    }

    public Rotation2d getYaw() {
        return (Constants.INVERT_GYRO) ? Rotation2d.fromDegrees(360 - m_gyro.getYaw().getValue()) : Rotation2d.fromDegrees(m_gyro.getYaw().getValue());
    }

    public void resetModulesToAbsolute(){
        for(SwerveModule mod : m_swerveMods){
            mod.resetToAbsolute();
        }
    }

    @Override
    public void periodic(){
        swerveOdometry.update(getYaw(), getModulePositions());  

        for(SwerveModule mod : m_swerveMods){
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " CANcoder", mod.getCANcoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Integrated", mod.getPosition().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);    
        }
    }
}