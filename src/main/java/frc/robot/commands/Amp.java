package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.RobotState.BotState;

public class Amp extends Command {

    private Feeder m_feeder;
    private Shooter m_shooter;
    private Climber m_climber;
    private Intake m_intake;
    private RobotState m_robotState;
    private Timer m_timer;

    public Amp(Feeder feeder, Shooter shooter, Intake intake, Climber climber, RobotState robotState) {

        m_feeder = feeder;
        m_shooter = shooter;
        m_intake = intake;
        m_climber = climber;
        m_robotState = robotState;
        m_timer = new Timer();

        addRequirements(m_feeder, m_shooter, m_climber, m_intake, m_robotState);

    }

    @Override 
    public void initialize() {

        m_robotState.setRobotState(BotState.AMP);
        m_robotState.setIsAmpState(true);

        m_shooter.shooterPivot(0);
        m_intake.intakePivot(m_robotState.getRobotState().intakeState.intakeSetpoint);

        m_timer.reset();
        m_timer.start();

    }

    @Override
    public void execute() {

        while(m_timer.get() < Constants.AMP_PIVOT_DELAY) {

            m_feeder.feederRun(m_robotState.getRobotState().feederState.feederVelocity);
            m_intake.intakeRun(m_robotState.getRobotState().intakeState.intakeVelocity);
            m_shooter.shooterRun(-10);

        }

        m_feeder.feederRunPercentOutput(0);
        //m_shooter.shooterRun(40);
        m_shooter.shooterPivot(m_robotState.getRobotState().shooterState.shooterPivot);
        m_intake.intakePercentOutput(0);

    }

    @Override
    public boolean isFinished() {

        return m_timer.get() < Constants.AMP_PIVOT_DELAY;

    }

    
}
