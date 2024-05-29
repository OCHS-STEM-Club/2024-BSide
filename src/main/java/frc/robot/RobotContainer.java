// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AprilTag.TagAlignmentTeleopCmd;
import frc.robot.commands.Climber.ClimberDownCmd;
import frc.robot.commands.Climber.ClimberDownOverrideCmd;
import frc.robot.commands.Climber.ClimberUpCmd;
import frc.robot.commands.Climber.ClimberUpOverrideCmd;
import frc.robot.commands.ArmDownCmd;
import frc.robot.commands.AprilTag.TagAlignmentAutoCmd;
import frc.robot.commands.Drive.AbsoluteDriveAdv;
import frc.robot.commands.Drive.AbsoluteFieldDrive;
import frc.robot.commands.Indexer.IndexerInCmd;
import frc.robot.commands.Indexer.IndexerOverrideCmd;
import frc.robot.commands.Intake.IntakeInCmd;
import frc.robot.commands.Intake.IntakeOutCmd;
import frc.robot.commands.Shooter.ShooterShuttleCmd;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

import java.io.File;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
  private double rot;
  private double rot_limelight;

  // Auto Builder Sendable Chooser 
  private final SendableChooser<Command> autoChooser;

  // All Subsystems
  private final SwerveSubsystem m_swerveSubsystem = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve/neo"));
  private final ArmSubsystem m_armSubsystem = new ArmSubsystem();
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  private final IndexerSubsystem m_indexerSubsystem = new IndexerSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private final LimelightSubsystem m_limelightSubsystem = new LimelightSubsystem();
  private final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();

  // Intake Cmd
  IntakeInCmd m_intakeInCmd = new IntakeInCmd(m_intakeSubsystem, m_armSubsystem, m_indexerSubsystem);
  IntakeOutCmd m_intakeOutCmd = new IntakeOutCmd(m_intakeSubsystem, m_indexerSubsystem);
  // Indexer Cmd
  IndexerOverrideCmd m_indexerOverrideCmd = new IndexerOverrideCmd(m_indexerSubsystem);
  IndexerInCmd m_indexerInCmd = new IndexerInCmd(m_indexerSubsystem, m_armSubsystem);
  TagAlignmentTeleopCmd m_tagAlignmentCmd = new TagAlignmentTeleopCmd(m_swerveSubsystem, m_shooterSubsystem, m_armSubsystem);
  // Climber Cmd
  ClimberDownOverrideCmd m_climberDownOverrideCmd = new ClimberDownOverrideCmd(m_climberSubsystem);
  ClimberUpOverrideCmd m_climberUpOverrideCmd = new ClimberUpOverrideCmd(m_climberSubsystem);
  ClimberDownCmd m_climberDownCommand = new ClimberDownCmd(m_climberSubsystem);
  ClimberUpCmd m_climberUpCommand = new ClimberUpCmd(m_climberSubsystem);

  // Arm Cmd
  ArmDownCmd m_armDownCmd = new ArmDownCmd(m_armSubsystem);

  //Shooter Cmd
  ShooterShuttleCmd m_shooterShuttle = new ShooterShuttleCmd(m_shooterSubsystem);
  
  // Controllers
  final CommandXboxController driverXbox = new CommandXboxController(Constants.OperatorConstants.kDriveJoystickPort);
  final CommandXboxController ButtonBox = new CommandXboxController(Constants.OperatorConstants.kOperatorControllerPort);

  // final CommandJoystick driveJoystick = new CommandJoystick(Constants.OperatorConstants.kDriveJoystickPort);
  // final CommandJoystick rotJoystick = new CommandJoystick(Constants.OperatorConstants.kRotJoystickPort);
  // final CommandXboxController ButtonBox = new CommandXboxController(Constants.OperatorConstants.kOperatorControllerPort);


  public RobotContainer()
  {
    // Indexer
    NamedCommands.registerCommand("Indexer in Override", Commands.runOnce(m_indexerSubsystem::indexerIn));
    NamedCommands.registerCommand("Indexer in", new IndexerInCmd(m_indexerSubsystem, m_armSubsystem));
    NamedCommands.registerCommand("Indexer Off", Commands.runOnce(m_indexerSubsystem::indexerOff));

    // Intake
    NamedCommands.registerCommand("Intake & Indexer Out", new IntakeOutCmd(m_intakeSubsystem, m_indexerSubsystem));
    NamedCommands.registerCommand("Intake in", new IntakeInCmd(m_intakeSubsystem, m_armSubsystem, m_indexerSubsystem));
    NamedCommands.registerCommand("Intake Off", Commands.runOnce(m_intakeSubsystem::intakeOff));
    
    // Shooter
    NamedCommands.registerCommand("Shooter On", Commands.runOnce(() -> m_shooterSubsystem.shooterSpeed(0.4)));
    NamedCommands.registerCommand("Shooter On 4 Piece", Commands.runOnce(() -> m_shooterSubsystem.shooterSpeed(0.45)));
    NamedCommands.registerCommand("Shooter Off", Commands.runOnce(m_shooterSubsystem::shooterOff));

    // Arm Normal Setpoints
    NamedCommands.registerCommand("Arm to Intake", Commands.runOnce(m_armSubsystem::intakeSetpoint));
    NamedCommands.registerCommand("Arm to Amp", Commands.runOnce(m_armSubsystem::ampSetpoint));
    NamedCommands.registerCommand("Arm to Shooter Subwoofer", Commands.runOnce(m_armSubsystem::shooterSetpoint));

    NamedCommands.registerCommand("Arm to Shooter 1st Piece Middle", Commands.runOnce(() -> m_armSubsystem.setReference(27)));
    NamedCommands.registerCommand("Arm to Shooter Shuttle", Commands.runOnce(() -> m_armSubsystem.setReference(27)));
    NamedCommands.registerCommand("Arm to Shooter 4 Piece", Commands.runOnce(() -> m_armSubsystem.setReference(31)));
    NamedCommands.registerCommand("Arm to Shooter 4 Piece 1st", Commands.runOnce(() -> m_armSubsystem.setReference(37)));
    NamedCommands.registerCommand("Arm to Shooter Sides", Commands.runOnce(() -> m_armSubsystem.setReference(7)));

    // In Use
    NamedCommands.registerCommand("Arm to Shooter Midfield 2 piece", Commands.runOnce(() -> m_armSubsystem.setReference(25)));
    // 
    NamedCommands.registerCommand("Arm to Shooter Side Source 1st Piece", Commands.runOnce(() -> m_armSubsystem.setReference(26.5)));
    NamedCommands.registerCommand("Arm to Shooter Side Source 1st Piece Test", Commands.runOnce(() -> m_armSubsystem.setReference(23)));
    // Tag Alignment
    NamedCommands.registerCommand("April Tag Alignment", new TagAlignmentAutoCmd(m_swerveSubsystem, m_shooterSubsystem, m_armSubsystem).withTimeout(0.75));




    // Auto Chooser builder
    autoChooser = AutoBuilder.buildAutoChooser();

    // Configure the trigger bindings
    configureBindings();

    // Command driveFieldOrientedAnglularVelocity = m_swerveSubsystem.driveCommand(
    //     () -> MathUtil.applyDeadband(-driveJoystick.getRawAxis(1) * OperatorConstants.TRANSLATION_Y_CONSTANT, OperatorConstants.LEFT_Y_DEADBAND),
    //     () -> MathUtil.applyDeadband(-driveJoystick.getRawAxis(0) * OperatorConstants.TRANSLATION_X_CONSTANT, OperatorConstants.LEFT_X_DEADBAND),
    //     () -> -rotJoystick.getRawAxis(0) * OperatorConstants.ROTATION_CONSTANT);

    // Command driveFieldOrientedDirectAngleSim = m_swerveSubsystem.simDriveCommand(
    //     () -> MathUtil.applyDeadband(driveJoystick.getRawAxis(1) , OperatorConstants.LEFT_Y_DEADBAND),
    //     () -> MathUtil.applyDeadband(driveJoystick.getRawAxis(0) , OperatorConstants.LEFT_X_DEADBAND),
    //     () -> rotJoystick.getRawAxis(0));

        Command driveFieldOrientedAnglularVelocity = m_swerveSubsystem.driveCommand(
        () -> MathUtil.applyDeadband(-driverXbox.getLeftY() * OperatorConstants.TRANSLATION_Y_CONSTANT, OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(-driverXbox.getLeftX() * OperatorConstants.TRANSLATION_X_CONSTANT, OperatorConstants.LEFT_X_DEADBAND),
        () -> -driverXbox.getRightX() * OperatorConstants.ROTATION_CONSTANT);

    Command driveFieldOrientedDirectAngleSim = m_swerveSubsystem.simDriveCommand(
        () -> MathUtil.applyDeadband(driverXbox.getRightY() , OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(driverXbox.getRightX() , OperatorConstants.LEFT_X_DEADBAND),
        () -> driverXbox.getRawAxis(0));


    m_swerveSubsystem.setDefaultCommand(!RobotBase.isSimulation() ? driveFieldOrientedAnglularVelocity : driveFieldOrientedDirectAngleSim);

    // Auto Chooser Smart Dashboard
    SmartDashboard.putData("Autos", autoChooser);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {

    // Xbox Controller Configs
      driverXbox.a().onTrue(
        Commands.runOnce(m_swerveSubsystem::zeroGyro)
      );

      driverXbox.leftTrigger().whileTrue(
        m_indexerInCmd
      );

      driverXbox.rightBumper().whileTrue(
        m_shooterShuttle
      );

      driverXbox.leftTrigger().whileTrue(
        m_intakeInCmd
      );

      driverXbox.rightTrigger().whileTrue(
        m_tagAlignmentCmd
      );

      driverXbox.b().whileTrue(
        m_indexerOverrideCmd
      );

      driverXbox.leftBumper().whileTrue(
      m_intakeOutCmd
      );

  // Joystick Button Configs

    // rotJoystick.button(11).onTrue(
    //    Commands.runOnce(m_swerveSubsystem::zeroGyro)
    //   );

    // driveJoystick.button(1).whileTrue(
    //   m_indexerInCmd
    //   );

    // driveJoystick.button(1).whileTrue(
    //   m_intakeInCmd
    //   );

    // rotJoystick.button(1).whileTrue(
    //   m_tagAlignmentCmd
    //   );


    // rotJoystick.button(3).whileTrue(
    //   m_indexerOverrideCmd
    //   );

    // driveJoystick.button(3).whileTrue(
    //   m_intakeOutCmd
    //   );

    // rotJoystick.button(4).whileTrue(
    //     m_shooterShuttle
    //   );




      // Button Box Configs
    ButtonBox.button(3).onTrue(
      Commands.runOnce(m_armSubsystem :: armUp)).onFalse(Commands.runOnce(m_armSubsystem::armoff)
    );

    ButtonBox.button(1).whileTrue(
      m_armDownCmd
    );

    ButtonBox.button(6).onTrue(
      Commands.runOnce(m_armSubsystem :: shooterSetpoint)
    );

    ButtonBox.button(4).onTrue(
      Commands.runOnce(m_armSubsystem :: intakeSetpoint)
    );

    ButtonBox.button(5).onTrue(
      Commands.runOnce(m_armSubsystem :: ampSetpoint)
    );

    ButtonBox.pov(0).whileTrue(
      m_climberUpCommand
    );

    ButtonBox.pov(180).whileTrue(
      m_climberDownCommand
    );

    ButtonBox.button(10).whileTrue(
      m_climberUpOverrideCmd
    );

    ButtonBox.button(9).whileTrue(
      m_climberDownOverrideCmd
    );

    ButtonBox.leftTrigger().whileTrue(
      Commands.runOnce(m_armSubsystem :: climberSetpoint)
    );
    
    
    // driverXbox.b().whileTrue(
    //     Commands.deferredProxy(() -> m_swerveSubsystem.driveToPose(
    //                                new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
    //                           ));

    
    // driverXbox.x().whileTrue(Commands.runOnce(m_swerveSubsystem::lock, m_swerveSubsystem).repeatedly());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }

  public void setDriveMode()
  {
    //m_swerveSubsystem.setDefaultCommand();
  }

  public void setMotorBrake(boolean brake)
  {
    m_swerveSubsystem.setMotorBrake(brake);
  }

  public void climberEncoderZero()
  {
    m_climberSubsystem.climberEncoderZero();
  }
}
