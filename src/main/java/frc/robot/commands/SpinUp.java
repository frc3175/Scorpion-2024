package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.RobotState.BotState;

public class SpinUp extends Command {

    private Feeder m_feeder;
    private Shooter m_shooter;
    private Climber m_climber;
    private Intake m_intake;
    private RobotState m_robotState;

    public SpinUp(Feeder feeder, Shooter shooter, Intake intake, Climber climber, RobotState robotState) {

        m_feeder = feeder;
        m_shooter = shooter;
        m_intake = intake;
        m_climber = climber;
        m_robotState = robotState;

        addRequirements(m_feeder, m_shooter, m_climber, m_intake, m_robotState);

    }

    @Override 
    public void initialize() {

        m_robotState.setRobotState(BotState.SHOOT);
        m_robotState.setIsAmpState(false);

        m_feeder.feederRun(m_robotState.getRobotState().feederState.feederVelocity);
        m_shooter.shooterRun(m_robotState.getRobotState().shooterState.shooterVelocity);
        m_intake.intakeRun(m_robotState.getRobotState().intakeState.intakeVelocity);

    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
