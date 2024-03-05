package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;

public class ClimbDown extends Command {

    private Climber m_climber;
    private RobotState m_robotState;

    public ClimbDown(Climber climber, RobotState robotState) {

        m_climber = climber;
        m_robotState = robotState;

        addRequirements(m_climber, m_robotState);

    }

    @Override
    public void initialize() {

        m_climber.climbPosition(Constants.CLIMBER_DOWN);

    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
