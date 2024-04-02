package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.LED.CurrentState;
import frc.robot.subsystems.RobotState.BotState;

public class Reset extends Command {

    private Feeder m_feeder;
    private Shooter m_shooter;
    private Climber m_climber;
    private Intake m_intake;
    private RobotState m_robotState;
    private LED m_led;

    private Timer m_timer;

    private boolean isFinished = false;

    public Reset(Feeder feeder, Shooter shooter, Intake intake, Climber climber, RobotState robotState, LED led) {

        m_feeder = feeder;
        m_shooter = shooter;
        m_intake = intake;
        m_climber = climber;
        m_robotState = robotState; 
        m_timer = new Timer();
        m_led = led;

        addRequirements(m_feeder, m_shooter, m_climber, m_intake, m_robotState);

    }

    @Override
    public void initialize() {

        m_timer.reset();
        m_timer.start();

        m_shooter.setInteroplationMode(false);

    }

    @Override 
    public void execute() {

        m_robotState.setRobotState(BotState.RESET);

        m_feeder.feederRunPercentOutput(m_robotState.getRobotState().feederState.feederVelocity);
        m_shooter.shooterPercentOutput(m_robotState.getRobotState().shooterState.shooterVelocity);
        m_shooter.shooterPivot(m_robotState.getRobotState().shooterState.shooterPivot);
        m_intake.intakePercentOutput(m_robotState.getRobotState().intakeState.intakeVelocity);
        m_intake.intakePivot(m_robotState.getRobotState().intakeState.intakeSetpoint);

        isFinished = true;

        
    }

    @Override
    public boolean isFinished() {

        return isFinished;

    }

    @Override
    public void end(boolean isFinished) {

        if(m_led.getCurrentState() == CurrentState.INTAKING_GOT_PIECE) {
            m_led.setCurrentState(CurrentState.INTAKE_HAS_PIECE);
            m_led.setLEDs(m_led.getCurrentState().r, m_led.getCurrentState().g, m_led.getCurrentState().b);
        } else {
            m_led.setCurrentState(CurrentState.RESET);
            m_led.setLEDs(m_led.getCurrentState().r, m_led.getCurrentState().g, m_led.getCurrentState().b);
        }

    }
    
}
