package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.led.*;
import com.ctre.phoenix.led.CANdle.LEDStripType;

public class LED extends SubsystemBase {

  private CANdle m_candle;
  private boolean configured = false;
  private CurrentState m_currentState;

  public LED() {

    m_candle = new CANdle(Constants.CANDLE_ID, "elevatoryiboi");
    Configure();
    setLEDs(255, 0, 0);
    setCurrentState(CurrentState.RESET);
      
  }

  /** Creates a new Candle. */
  public void Configure() {
    if(!configured){
    CANdleConfiguration configAll = new CANdleConfiguration();
    configAll.statusLedOffWhenActive = true;
        configAll.disableWhenLOS = false;
        configAll.stripType = LEDStripType.RGB;
        configAll.brightnessScalar = 1;
        m_candle.configAllSettings(configAll, 100);
        configured = true;
    }

        
  }

  public void setCurrentState(CurrentState state) {

    m_currentState = state;

  }

  public CurrentState getCurrentState() {

    return m_currentState;

  }

  public enum CurrentState {

    INTAKE_HAS_PIECE(140, 32, 210, false),
    RESET(255, 0, 0, false),
    INTAKING_GOT_PIECE(0, 255, 0, false),
    FINDING_TARGET(255, 255, 0, false),
    SHOOTER_READY(0, 255, 0, false);

    public int r;
    public int g;
    public int b;
    public boolean isBlinking;
    private CurrentState(int r, int g, int b, boolean isBlinking) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.isBlinking = isBlinking;
    }

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void setLEDs(int r, int g, int b){
    m_candle.setLEDs(r, g, b);
  }
}