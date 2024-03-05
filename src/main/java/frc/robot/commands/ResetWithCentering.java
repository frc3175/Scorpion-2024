package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.RobotState.BotState;

public class ResetWithCentering extends Command {

    private Feeder m_feeder;
    private Shooter m_shooter;
    private Climber m_climber;
    private Intake m_intake;
    private RobotState m_robotState;
    private Timer m_timer;
    private double m_rotations;

    public ResetWithCentering(Feeder feeder, Shooter shooter, Intake intake, Climber climber, RobotState robotState) {

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

        m_robotState.setRobotState(BotState.RESET);

        m_feeder.feederRun(m_robotState.getRobotState().feederState.feederVelocity);
        m_shooter.shooterPercentOutput(m_robotState.getRobotState().shooterState.shooterVelocity);
        m_shooter.shooterPivot(m_robotState.getRobotState().shooterState.shooterPivot);
        m_intake.intakePercentOutput(m_robotState.getRobotState().intakeState.intakeVelocity);
        m_intake.intakePivot(m_robotState.getRobotState().intakeState.intakeSetpoint);

        m_timer.reset();
        m_timer.start();

        m_rotations = m_intake.getIntakeRollerEncoderPosition();

        
    }

    @Override
    public void execute() {

        /* if(m_timer.get() > 0.5 && m_timer.get() < 1) {

            m_intake.intakeRun(-20);
            m_feeder.feederRun(20);
            m_shooter.shooterRun(-20);

        } else if(m_timer.get() > 1 && m_timer.get() < 1.4) {

            m_intake.intakeRun(20);
            m_feeder.feederRun(-20);
            m_shooter.shooterPercentOutput(0);
        } else if(m_timer.get() > 1.4){

            m_intake.intakePercentOutput(0);
            m_feeder.feederRunPercentOutput(0);

        } */

        if(m_timer.get() < 0.5) {

            m_intake.intakePercentOutput(0);

        } else if(m_intake.getIntakeRollerEncoderPosition() > m_rotations - 10) {

            m_intake.intakeRun(-20);
            m_feeder.feederRun(20);
            m_shooter.shooterRun(-20);

        } else if(m_intake.getIntakeRollerEncoderPosition() < m_rotations) {

            m_intake.intakeRun(20);
            m_feeder.feederRun(-20);
            m_shooter.shooterPercentOutput(0);
        } else {

            m_intake.intakePercentOutput(0);
            m_feeder.feederRunPercentOutput(0);

        }


    }

    @Override
    public boolean isFinished() {

        if(m_timer.get() > 1.5) {
            return true;
        } else {
            return false;
        }

    }
    
}
