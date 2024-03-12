package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.LED.CurrentState;
import frc.robot.subsystems.RobotState.BotState;

public class IntakeNote extends Command {

    private Intake m_intake;
    private RobotState m_robotState;
    private LED m_led;
    private Timer m_timer;
    private boolean isFinished;

    public IntakeNote(Intake intake, RobotState robotState, LED led) {

        m_intake = intake;
        m_robotState = robotState;
        m_led = led;

        addRequirements(m_intake, m_robotState, m_led);

    }

    @Override 
    public void initialize() {

        m_robotState.setRobotState(BotState.INTAKE);

        m_intake.intakeRun(m_robotState.getRobotState().intakeState.intakeVelocity);
        m_intake.intakePivot(m_robotState.getRobotState().intakeState.intakeSetpoint);

        m_timer.reset();
        m_timer.start();

        isFinished = false;

    }

    @Override
    public void execute() {

        if(m_intake.getIntakeStatorCurrent() > Constants.INTAKING_NOTE_CURRENT && m_timer.get() > 0.2) {

            m_led.setCurrentState(CurrentState.INTAKING_GOT_PIECE);
            m_led.setLEDs(m_led.getCurrentState().r, m_led.getCurrentState().g, m_led.getCurrentState().b);

            isFinished = true;

        }

    }

    @Override
    public boolean isFinished() {

        return isFinished;

    }
    
}
