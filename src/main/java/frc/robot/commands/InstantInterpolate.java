package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class InstantInterpolate extends Command {

    private Intake m_intake;
    private Shooter m_shooter;
    private Limelight m_limelight;

    public InstantInterpolate(Limelight limelight, Intake intake, Shooter shooter) {

        m_limelight = limelight;
        m_shooter = shooter;
        m_intake = intake;

        addRequirements(m_intake, m_shooter, m_limelight);

    }

    @Override 
    public void initialize() {

        m_shooter.shooterInterpolate(m_limelight.getDistanceToTarget());
        m_intake.interpolateIntake(m_limelight.getDistanceToTarget());

    }

    @Override
    public boolean isFinished() {

        return true;

    }
    
}
