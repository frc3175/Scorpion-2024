package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;

public class Outtake extends Command {

    private Intake m_intake;
    private Shooter m_shooter;
    private Feeder m_feeder;
    private RobotState m_robotState;

    public Outtake(Intake intake, Shooter shooter, Feeder feeder, RobotState robotState) {

        m_intake = intake;
        m_shooter = shooter;
        m_feeder = feeder;
        m_robotState = robotState;

        addRequirements(m_intake, m_shooter, m_feeder, m_robotState);

    }

    @Override
    public void initialize() {

        if(m_robotState.isAmpState()) {

            m_feeder.feederRun(Constants.FEEDER_OUTTAKE);
            m_shooter.shooterRun(Constants.SHOOTER_OUTTAKE);

        } else {

            m_intake.intakeRun(Constants.INTAKE_FEED_SHOOT);

        }

    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
