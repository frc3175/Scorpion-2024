package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LED;

public class LightLed extends Command {
  private int r;
  private int g;
  private int b;
  private LED m_LED;
  private boolean flash;
  private boolean isCop;

  /** Creates a new LightCandle. */
  public LightLed(LED led, int red, int green, int blue, boolean flashing, boolean copMode) {
    m_LED = led;
    r = red;
    g = green;
    b = blue;
    flash = flashing;
    isCop = copMode;
    addRequirements(m_LED);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_LED.Configure();
    if(flash){
          m_LED.setLEDs(r, g, b);
          Timer.delay(0.1);
          m_LED.setLEDs(0, 0, 0);
          Timer.delay(0.1);
        }
    else if(isCop){
          m_LED.setLEDs(0, 0, 255);
          Timer.delay(0.1);
          m_LED.setLEDs(255, 0, 0);
          Timer.delay(0.1);
        }
      else{
          m_LED.setLEDs(r, g, b);
        }
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}