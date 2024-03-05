package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.RobotState;

public class OuttakeAuto extends Command {

    private Intake m_intake;
    private RobotState m_robotState;

    public OuttakeAuto(Intake intake, RobotState robotState) {

        m_intake = intake;
        m_robotState = robotState;

        addRequirements(m_intake, m_robotState);

    }

    @Override
    public void initialize() {

        m_intake.intakeRun(Constants.INTAKE_FEED_SHOOT);

    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
