package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;


public class SwerveDrive extends Command {    
    private SwerveDrivetrain m_swerveDrivetrain;    
    private DoubleSupplier m_translationSup;
    private DoubleSupplier m_strafeSup;
    private DoubleSupplier m_rotationSup;
    private BooleanSupplier m_robotCentricSup;
    private SlewRateLimiter m_xAxisLimiter;
    private SlewRateLimiter m_yAxisLimiter;
    private BooleanSupplier m_isEvading;
    private BooleanSupplier m_isLocked;
    private BooleanSupplier m_isRotatingFast;
    private DoubleSupplier m_tx;
    private DoubleSupplier m_isAligning;

    public SwerveDrive(SwerveDrivetrain swerveDrivetrain, 
                       DoubleSupplier translationSup, 
                       DoubleSupplier strafeSup, 
                       DoubleSupplier rotationSup, 
                       BooleanSupplier robotCentricSup, 
                       BooleanSupplier isEvading,
                       BooleanSupplier isLocked,
                       BooleanSupplier isRotatingFast,
                       DoubleSupplier tx,
                       DoubleSupplier isAligning) {

        m_swerveDrivetrain = swerveDrivetrain;
        addRequirements(m_swerveDrivetrain);

        m_translationSup = translationSup;
        m_strafeSup = strafeSup;
        m_rotationSup = rotationSup;
        m_robotCentricSup = robotCentricSup;
        m_isEvading = isEvading;
        m_isLocked = isLocked;
        m_isRotatingFast = isRotatingFast;
        m_tx = tx;
        m_isAligning = isAligning;

        m_xAxisLimiter = new SlewRateLimiter(Constants.RATE_LIMITER);
        m_yAxisLimiter = new SlewRateLimiter(Constants.RATE_LIMITER);

    }

    @Override
    public void execute() {

        double rotationScalar = 1;

        if(m_isRotatingFast.getAsBoolean()) {
            rotationScalar = 2;
        } else {
            rotationScalar = 1;
        }

        /* Get Values, Deadband*/
        double xAxis = MathUtil.applyDeadband(m_translationSup.getAsDouble(), Constants.stickDeadband);
        double yAxis = MathUtil.applyDeadband(m_strafeSup.getAsDouble(), Constants.stickDeadband);
        double rAxis = MathUtil.applyDeadband(m_rotationSup.getAsDouble(), Constants.stickDeadband);

        double xAxisSquared = xAxis * xAxis * Math.signum(xAxis);
        double yAxisSquared = yAxis * yAxis * Math.signum(yAxis);
        double rAxisSquared = rAxis * rAxis * Math.signum(rAxis) * rotationScalar;

        double xAxisFiltered = m_xAxisLimiter.calculate(xAxisSquared);
        double yAxisFiltered = m_yAxisLimiter.calculate(yAxisSquared);

        boolean isAligning;

        if(m_isAligning.getAsDouble() > 0.1) {
            isAligning = true;
        } else {
            isAligning = false;
        }

        double rAxisActual;

        if(isAligning) {
            rAxisActual = m_swerveDrivetrain.alignToTarget(m_tx.getAsDouble());
        } else {
            rAxisActual = rAxisSquared * Constants.MAX_ANGULAR_VELOCITY * -1;
        }

        /* Drive */
        m_swerveDrivetrain.drive(
            new Translation2d(-xAxisFiltered, -yAxisFiltered).times(Constants.MAX_SPEED), 
            rAxisActual, 
            !m_robotCentricSup.getAsBoolean(), 
            true,
            m_isEvading.getAsBoolean(),
            m_isLocked.getAsBoolean()
        );
    }
}