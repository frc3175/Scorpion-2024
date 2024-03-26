package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final XboxController driver = new XboxController(Constants.DRIVER_PORT);
    private final XboxController operator = new XboxController(Constants.OPERATOR_PORT);
    private final XboxController test = new XboxController(5);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton isEvading = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton outtake = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final POVButton isRotatingFast = new POVButton(driver, 270);
    private final POVButton shooterIn = new POVButton(driver, 90);
    //private final JoystickButton driverB = new JoystickButton(driver, XboxController.Button.kB.value);
    //private final JoystickButton forceOuttake = new JoystickButton(driver, XboxController.Button.kY.value);
    private final int alignAxis = XboxController.Axis.kRightTrigger.value;
    private final JoystickButton cop = new JoystickButton(driver, XboxController.Button.kStart.value);
    private final JoystickButton snowPlow = new JoystickButton(driver, XboxController.Button.kBack.value);
    
    /* Operator Buttons */
    private final JoystickButton amp = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton reset = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton intake = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    private final JoystickButton spinUpShooter = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    private final POVButton climbUp = new POVButton(operator, 0);
    private final POVButton climbDown = new POVButton(operator, 180);
    private final JoystickButton start = new JoystickButton(operator, XboxController.Button.kStart.value);
    private final JoystickButton x = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton b = new JoystickButton(operator, XboxController.Button.kB.value);
    private final JoystickButton back = new JoystickButton(operator, XboxController.Button.kBack.value);
    private final JoystickButton rightJoystick = new JoystickButton(operator, XboxController.Button.kRightStick.value);

    /* Systems Check Buttons */
   private final JoystickButton testAmp = new JoystickButton(test, XboxController.Button.kY.value);
    private final JoystickButton testReset = new JoystickButton(test, XboxController.Button.kA.value);
    private final JoystickButton testIntake = new JoystickButton(test, XboxController.Button.kRightBumper.value);
    private final JoystickButton testShooter = new JoystickButton(test, XboxController.Button.kLeftBumper.value);
    private final POVButton testClimbUp = new POVButton(test, 0);
    private final POVButton testClimbDown = new POVButton(test, 180);
    private final JoystickButton testOuttake = new JoystickButton(test, XboxController.Button.kBack.value);
    private final JoystickButton testStart = new JoystickButton(test, XboxController.Button.kStart.value);
    private final JoystickButton testSprawl = new JoystickButton(test, XboxController.Button.kRightStick.value);

    /* Subsystems */
    private final SwerveDrivetrain m_swerveDrivetrain = new SwerveDrivetrain();
    private final Intake m_intake = new Intake();
    private final Shooter m_shooter = new Shooter();
    private final Feeder m_feeder = new Feeder();
    private final Climber m_climber = new Climber();
    private final LED m_Led = new LED();
    private final RobotState m_robotState = new RobotState();
    private final Limelight m_limelight = new Limelight();

    /* Auton Chooser */
    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {

        m_swerveDrivetrain.setDefaultCommand(
            new SwerveDrive(
                m_swerveDrivetrain, 
                () -> driver.getRawAxis(translationAxis), 
                () -> driver.getRawAxis(strafeAxis), 
                () -> driver.getRawAxis(rotationAxis), 
                () -> false,
                () -> isEvading.getAsBoolean(),
                () -> false,
                () -> isRotatingFast.getAsBoolean(),
                () -> m_limelight.getTX(),
                () -> driver.getRawAxis(alignAxis)
            )
        );

        /* Pathplanner Named Commands */
        NamedCommands.registerCommand("Intake", new IntakeNote(m_intake, m_robotState, m_Led));
        NamedCommands.registerCommand("Reset", new ResetIntake(m_intake, m_robotState));
        NamedCommands.registerCommand("Spin Up Shooter", new SpinUp(m_feeder, m_shooter, m_intake, m_climber, m_robotState));
        NamedCommands.registerCommand("Interpolate", new InstantInterpolate(m_limelight, m_intake, m_shooter));
        NamedCommands.registerCommand("Shoot", new OuttakeAuto(m_intake, m_robotState));
        NamedCommands.registerCommand("Shoottake", new IntakeAndShoot(m_intake, m_shooter, m_feeder, m_robotState));
        NamedCommands.registerCommand("Reset All", new Reset(m_feeder, m_shooter, m_intake, m_climber, m_robotState, m_Led));
        NamedCommands.registerCommand("Gyro Offset Amp", new InstantCommand(() -> m_swerveDrivetrain.adjustGyroZeroAmpSide()));
        NamedCommands.registerCommand("Gyro Offset Source", new InstantCommand(() -> m_swerveDrivetrain.adjustGyroZeroSourceSide()));

        /* Autos */
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);

        // Configure the button bindings
        configureButtonBindings();

    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        
        zeroGyro.onTrue(new InstantCommand(() -> m_swerveDrivetrain.zeroGyro()));

        intake.onTrue(new IntakeNote(m_intake, m_robotState, m_Led));
        intake.onFalse(new Reset(m_feeder, m_shooter, m_intake, m_climber, m_robotState, m_Led));

        spinUpShooter.onTrue(new SpinUp(m_feeder, m_shooter, m_intake, m_climber, m_robotState));
        spinUpShooter.onFalse(new Reset(m_feeder, m_shooter, m_intake, m_climber, m_robotState, m_Led));

        outtake.onTrue(new Outtake(m_intake, m_shooter, m_feeder, m_robotState, m_limelight, m_Led, m_swerveDrivetrain));
        outtake.onFalse(new Reset(m_feeder, m_shooter, m_intake, m_climber, m_robotState, m_Led));

        amp.onTrue(new Amp(m_feeder, m_shooter, m_intake, m_climber, m_robotState));
        reset.onTrue(new Reset(m_feeder, m_shooter, m_intake, m_climber, m_robotState, m_Led));

        x.onTrue(new RobotDistanceShot(m_intake, m_shooter));
        x.onTrue(new InstantCommand(() -> m_shooter.shooterRun(Constants.SHOOTER_FAST_SPEED)));
        x.onFalse(new ShooterReset(m_intake, m_shooter));

        b.whileTrue(new Interpolate(m_feeder, m_shooter, m_intake, m_climber, m_robotState, m_Led, m_limelight));
        b.onFalse(new ShooterReset(m_intake, m_shooter));

        back.onTrue(new InstantCommand(() -> m_shooter.shooterRun(-40)));
        back.onTrue(new InstantCommand(() -> m_feeder.feederRun(5)));
        back.onFalse(new InstantCommand(() -> m_shooter.shooterPercentOutput(0)));
        back.onFalse(new InstantCommand(() -> m_feeder.feederRunPercentOutput(0)));
        
        //start.onTrue(new LightLed(m_Led, 0, 0, 255, true, false)).onFalse(new LightLed(m_Led, 255, 0, 0, false, false));
        start.onTrue(new InstantCommand(() -> m_shooter.shooterRun(0)));
         start.onTrue(new InstantCommand(() -> m_feeder.feederRun(0)));

        rightJoystick.onTrue(new ClimbUp(m_feeder, m_shooter, m_intake, m_climber, m_robotState));

        climbDown.onTrue(new ClimbDown(m_climber, m_robotState));

        climbUp.onFalse(new InstantCommand(() -> m_climber.climbPercentOutput(0)));
        climbUp.onTrue(new InstantCommand(() -> m_climber.climbPosition(Constants.CLIMBER_UP)));

        shooterIn.onTrue(new InstantCommand(() -> m_shooter.shooterPivot(0)));

        cop.onTrue(new LightLed(m_Led, 0, 0, 0, false, true)).onFalse(new LightLed(m_Led, 255, 0, 0, false, false));

        snowPlow.onTrue(new LightLed(m_Led, 255, 165, 0, true, false)).onFalse(new LightLed(m_Led, 255, 0, 0, false, false));

        //driverB.whileTrue(new SnapToTarget(m_limelight, m_swerveDrivetrain));

        // Systems Check Buttons

        testShooter.onTrue(new InstantCommand(() -> m_shooter.shooterPercentOutput(0.25))); //FIXME: adjust this value if you want to test shooting from distances, THEN RETURN TO 0.25
        testShooter.onFalse(new InstantCommand(() -> m_shooter.shooterPercentOutput(0)));
        testShooter.onTrue(new InstantCommand(() -> m_feeder.feederRunPercentOutput(0.25))); //FIXME: adjust this value if you want to test shooting from distances, THEN RETURN TO 0.25
        testShooter.onFalse(new InstantCommand(() -> m_feeder.feederRunPercentOutput(0)));
        testShooter.onTrue(new InstantCommand(() -> m_shooter.shooterPivot(Constants.SHOOTER_PIVOT_SHOOT)));
        testShooter.onTrue(new InstantCommand(() -> m_shooter.shooterPivot(0)));

        testAmp.onTrue(new Amp(m_feeder, m_shooter, m_intake, m_climber, m_robotState));
        testReset.onTrue(new Reset(m_feeder, m_shooter, m_intake, m_climber, m_robotState, m_Led));

        testOuttake.onTrue(new Outtake(m_intake, m_shooter, m_feeder, m_robotState, m_limelight, m_Led, m_swerveDrivetrain));
        testOuttake.onFalse(new Reset(m_feeder, m_shooter, m_intake, m_climber, m_robotState, m_Led));

        testClimbUp.onFalse(new InstantCommand(() -> m_climber.climbPercentOutput(0)));
        testClimbUp.onTrue(new InstantCommand(() -> m_climber.climbPercentOutput(0.2)));

        testClimbDown.onTrue(new InstantCommand(() -> m_climber.climbPercentOutput(-0.2)));
        testClimbDown.onFalse(new InstantCommand(() -> m_climber.climbPercentOutput(0)));
        
        testIntake.onTrue(new IntakeNote(m_intake, m_robotState, m_Led));
        testIntake.onFalse(new Reset(m_feeder, m_shooter, m_intake, m_climber, m_robotState, m_Led));

        testSprawl.onTrue(new ClimbUp(m_feeder, m_shooter, m_intake, m_climber, m_robotState));

        testStart.onTrue(new LightLed(m_Led, 0, 0, 255, true, false)).onFalse(new LightLed(m_Led, 255, 0, 0, false, false));




    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {

        return autoChooser.getSelected();

    }
}
