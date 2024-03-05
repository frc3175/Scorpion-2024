package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Climber.ClimberState;
import frc.robot.subsystems.Feeder.FeederState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Shooter.ShooterState;

public class RobotState extends SubsystemBase {

    private BotState m_botState;
    private boolean m_isAmpState;

    public RobotState() {

        //setBotState(BotState.RESET);
        setIsAmpState(false);

    }

    public void setRobotState(BotState state) {

        m_botState = state;

    }

    public BotState getRobotState() {

        return m_botState;

    }

    public boolean isAmpState() {

        return m_isAmpState;

    }

    public void setIsAmpState(boolean isAmpState) {

        m_isAmpState = isAmpState;

    }

    public enum BotState {

        RESET(IntakeState.RESET, FeederState.RESET, ShooterState.RESET, ClimberState.RESET),
        INTAKE(IntakeState.INTAKE, FeederState.RESET, ShooterState.RESET, ClimberState.RESET),
        SHOOT(IntakeState.SHOOT, FeederState.SHOOT, ShooterState.SHOOT, ClimberState.RESET),
        AMP(IntakeState.AMP, FeederState.AMP, ShooterState.AMP, ClimberState.RESET),
        CLIMB_UP(IntakeState.CLIMB, FeederState.RESET, ShooterState.CLIMB, ClimberState.CLIMBER_UP);
        //Figure out control situation for climbing, may want to add a climb down state?

        public IntakeState intakeState;
        public FeederState feederState;
        public ShooterState shooterState;
        public ClimberState climberState;
        private BotState(IntakeState intakeState, FeederState feederState, ShooterState shooterState, ClimberState climberState) {
            this.intakeState = intakeState;
            this.feederState = feederState;
            this.shooterState = shooterState;
            this.climberState = climberState;
        }

    
    }


    
}