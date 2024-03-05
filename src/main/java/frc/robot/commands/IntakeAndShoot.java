package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.RobotState.BotState;

public class IntakeAndShoot extends Command {

    private Intake m_intake;
    private RobotState m_robotState;
    private Shooter m_shooter;
    private Feeder m_feeder;

    public IntakeAndShoot(Intake intake, Shooter shooter, Feeder feeder, RobotState robotState) {

        m_intake = intake;
        m_shooter = shooter;
        m_feeder = feeder;
        m_robotState = robotState;

        addRequirements(m_intake, m_shooter, m_robotState);

    }

    @Override 
    public void initialize() {

        m_robotState.setRobotState(BotState.INTAKE);

        m_shooter.shooterRun(Constants.SHOOTER_SHOOT);
        m_feeder.feederRun(Constants.FEEDER_SHOOT);
        m_intake.intakeRun(m_robotState.getRobotState().intakeState.intakeVelocity);
        m_intake.intakePivot(m_robotState.getRobotState().intakeState.intakeSetpoint);

    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
