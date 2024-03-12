package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.LED.CurrentState;

public class DistanceLineup extends Command{

    private Shooter m_shooter;
    private Intake m_intake;
    private RobotState m_robotState;
    private Limelight m_limelight;
    private LED m_led;
    private boolean isAligned;

    public DistanceLineup(Shooter shooter, Intake intake, RobotState robotState, Limelight limelight, LED led) {

        m_shooter = shooter;
        m_intake = intake;
        m_robotState = robotState;
        m_limelight = limelight;
        m_led = led;

        isAligned = false;

        addRequirements(m_shooter, m_intake, m_robotState, m_limelight, m_led);

    }

    @Override
    public void initialize() {

        isAligned = m_limelight.hasTarget();

        if(isAligned) {

            m_led.setCurrentState(CurrentState.SHOOTER_READY);
            m_led.setLEDs(m_led.getCurrentState().r, m_led.getCurrentState().g, m_led.getCurrentState().b);

        } else {

            m_led.setCurrentState(CurrentState.FINDING_TARGET);
            m_led.setLEDs(m_led.getCurrentState().r, m_led.getCurrentState().g, m_led.getCurrentState().b);


        }

        m_shooter.setInteroplationMode(true);

    }

    @Override 
    public void execute() {

        double ty = m_limelight.getDistanceToTarget();

        SmartDashboard.putNumber("distance to target subsystem", ty);

        m_shooter.shooterInterpolate(ty);
        m_intake.interpolateIntake(ty);

        if(m_limelight.hasTarget() && !isAligned) {

            isAligned = true;
            m_led.setCurrentState(CurrentState.SHOOTER_READY);
            m_led.setLEDs(m_led.getCurrentState().r, m_led.getCurrentState().g, m_led.getCurrentState().b);
            
        } else if(!m_limelight.hasTarget() && isAligned) {

            isAligned = false;
            m_led.setCurrentState(CurrentState.FINDING_TARGET);
            m_led.setLEDs(m_led.getCurrentState().r, m_led.getCurrentState().g, m_led.getCurrentState().b);

        }

    }
    
}
