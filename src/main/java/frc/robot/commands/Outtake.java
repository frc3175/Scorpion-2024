package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.LED.CurrentState;

public class Outtake extends Command {

    private Intake m_intake;
    private Shooter m_shooter;
    private Feeder m_feeder;
    private RobotState m_robotState;
    private Limelight m_limelight;
    private LED m_led;
    private boolean isFinished;
    private SwerveDrivetrain m_drivetrain;

    public Outtake(Intake intake, Shooter shooter, Feeder feeder, RobotState robotState, Limelight limelight, LED led, SwerveDrivetrain drivetrain) {

        m_intake = intake;
        m_shooter = shooter;
        m_feeder = feeder;
        m_robotState = robotState;
        m_limelight = limelight;
        m_led = led;
        m_drivetrain = drivetrain;

        isFinished = false;

        addRequirements(m_intake, m_shooter, m_feeder, m_robotState, m_limelight, m_led, m_drivetrain);

    }

    @Override
    public void initialize() {

        isFinished = false;

    }

    @Override
    public void execute() {

        if(m_robotState.isAmpState()) {

            m_feeder.feederRun(Constants.FEEDER_OUTTAKE);
            m_shooter.shooterRun(Constants.SHOOTER_OUTTAKE);

            isFinished = true;

        } else {

            if(m_shooter.getInterpolationMode()) {

                            double rotation = m_limelight.aimToTarget();

                m_drivetrain.drive(
                new Translation2d(0, 0).times(Constants.MAX_SPEED), 
                rotation, 
                false,
                true,
                false,
                false
                );

                if(m_limelight.isAtTarget()) {

                    //m_drivetrain.lockWheels();

                    m_intake.intakeRun(Constants.INTAKE_FEED_SHOOT);

                    isFinished = true;

                }

            } else {

                m_intake.intakeRun(Constants.INTAKE_FEED_SHOOT);
                isFinished = true;

            }

        }

    }

    @Override
    public boolean isFinished() {

        return isFinished;

    }
    
}
