package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.RobotState.BotState;

public class ResetIntake extends Command {

    private Intake m_intake;
    private RobotState m_robotState;

    public ResetIntake(Intake intake, RobotState robotState) {

        m_intake = intake;
        m_robotState = robotState;

        addRequirements(m_intake, m_robotState);

    }

    @Override 
    public void initialize() {

        m_robotState.setRobotState(BotState.RESET);

        m_intake.intakePercentOutput(m_robotState.getRobotState().intakeState.intakeVelocity);
        m_intake.intakePivot(m_robotState.getRobotState().intakeState.intakeSetpoint);

        
    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
