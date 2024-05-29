package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.RobotState.BotState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;

public class AmpBoop extends Command {

    private Intake m_intake;
    private Shooter m_shooter;
    private Feeder m_feeder;
    private RobotState m_robotState;
    private Limelight m_limelight;
    private LED m_led;
    private boolean isFinished;
    private SwerveDrivetrain m_drivetrain;
    private Timer m_timer;

    public AmpBoop(Intake intake, Shooter shooter, Feeder feeder, RobotState robotState, Limelight limelight, LED led, SwerveDrivetrain drivetrain) {

        m_intake = intake;
        m_shooter = shooter;
        m_feeder = feeder;
        m_robotState = robotState;
        m_limelight = limelight;
        m_led = led;
        m_drivetrain = drivetrain;
        m_timer = new Timer();

        isFinished = false;

        addRequirements(m_intake, m_shooter, m_feeder, m_robotState, m_limelight, m_led, m_drivetrain);

    }

    @Override
    public void initialize() {

        isFinished = false;
        m_timer.reset();
        m_timer.start();

    }

    @Override
    public void execute() {

        while(m_robotState.getRobotState() == BotState.AMP && m_timer.get() < 0.3) {

            m_shooter.shooterPivot(Constants.AMP_BOOP + 1);

        }

        m_shooter.shooterPivot(Constants.SHOOTER_PIVOT_AMP);

    }

    @Override
    public boolean isFinished() {

        return isFinished;

    }
    
}
