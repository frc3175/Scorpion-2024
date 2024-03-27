package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveDrivetrain;

public class SnapToTarget extends Command {
    
    private Limelight m_limelight;
    private SwerveDrivetrain m_drivetrain;

    public SnapToTarget(Limelight limelight, SwerveDrivetrain drivetrain) {

        m_drivetrain = drivetrain;
        m_limelight = limelight;

        addRequirements(m_limelight, m_drivetrain);

    }

    @Override
    public void execute() {

        double rotation = m_limelight.aimToTarget();

        m_drivetrain.drive(
            new Translation2d(0, 0).times(Constants.MAX_SPEED), 
            rotation, 
            false,
            true,
            false,
            false,
            true
        );

    }
    
}
