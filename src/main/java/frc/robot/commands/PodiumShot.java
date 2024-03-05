package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class PodiumShot extends Command {

    private Intake m_intake;
    private Shooter m_shooter;

    public PodiumShot(Intake intake, Shooter shooter) {

        m_intake = intake;
        m_shooter = shooter;

        addRequirements(m_intake, m_shooter);

    }

    @Override
    public void initialize() {

        m_shooter.shooterPivot(Constants.SHOOTER_PIVOT_PODIUM);
        m_intake.intakePivot(Constants.INTAKE_PIVOT_PODIUM);

    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
