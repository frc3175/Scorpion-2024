package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.LED.CurrentState;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.RobotState.BotState;

public class SpinUpAndInterpolate extends Command {

    private Feeder m_feeder;
    private Shooter m_shooter;
    private Climber m_climber;
    private Intake m_intake;
    private RobotState m_robotState;
    private LED m_led;
    private Limelight m_limelight;

    public SpinUpAndInterpolate(Feeder feeder, Shooter shooter, Intake intake, Climber climber, RobotState robotState, LED led, Limelight limelight) {

        m_feeder = feeder;
        m_shooter = shooter;
        m_intake = intake;
        m_climber = climber;
        m_robotState = robotState;
        m_led = led;
        m_limelight = limelight;

        addRequirements(m_feeder, m_shooter, m_climber, m_intake, m_robotState, m_led, m_limelight);

    }

    @Override 
    public void initialize() {

        m_robotState.setRobotState(BotState.SHOOT);
        m_robotState.setIsAmpState(false);

        if(m_limelight.getDistanceToTarget() > 60) {

            m_feeder.feederRun(Constants.SHOOTER_FAST_SPEED);
            m_shooter.shooterRun(Constants.SHOOTER_FAST_SPEED);
            m_intake.intakeRun(m_robotState.getRobotState().intakeState.intakeVelocity);

        } else {

            m_feeder.feederRun(m_robotState.getRobotState().feederState.feederVelocity);
            m_shooter.shooterRun(m_robotState.getRobotState().shooterState.shooterVelocity);
            m_intake.intakeRun(m_robotState.getRobotState().intakeState.intakeVelocity);

        }

        m_led.setCurrentState(CurrentState.SHOOTER_READY);
        m_led.setLEDs(m_led.getCurrentState().r, m_led.getCurrentState().g, m_led.getCurrentState().b);

        m_shooter.setInteroplationMode(true);

    }

    @Override 
    public void execute() {

        double ty = m_limelight.getDistanceToTarget();

        if(ty > 75) {

            SmartDashboard.putNumber("distance to target subsystem", ty);

            m_shooter.shooterInterpolate(ty);
            m_intake.interpolateIntake(ty);

        }

        if(m_limelight.isAtTarget()) {

            m_led.setCurrentState(CurrentState.SHOOTER_READY);
            m_led.setLEDs(m_led.getCurrentState().r, m_led.getCurrentState().g, m_led.getCurrentState().b);

        } else {

            m_led.setCurrentState(CurrentState.INTAKE_HAS_PIECE);
            m_led.setLEDs(m_led.getCurrentState().r, m_led.getCurrentState().g, m_led.getCurrentState().b);

        }


    }
    
}
