package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;

public class DistanceLineup extends Command{

    private Shooter m_shooter;
    private Intake m_intake;
    private RobotState m_robotState;
    private Limelight m_limelight;

    public DistanceLineup(Shooter shooter, Intake intake, RobotState robotState, Limelight limelight) {

        m_shooter = shooter;
        m_intake = intake;
        m_robotState = robotState;
        m_limelight = limelight;

        addRequirements(m_shooter, m_intake, m_robotState, m_limelight);

    }

    @Override 
    public void execute() {

        double ty = m_limelight.getDistanceToTarget();

        SmartDashboard.putNumber("distance to target subsystem", ty);

        m_shooter.shooterInterpolate(ty);
        m_intake.interpolateIntake(ty);

    }
    
}
