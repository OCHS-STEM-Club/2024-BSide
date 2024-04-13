// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AprilTag;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class TagAlignmentTeleopCmd extends Command {
  private final SwerveSubsystem m_swerveSubsystem;
  private final ShooterSubsystem m_shooterSubsystem;
  private final ArmSubsystem m_armSubsystem;
  private final PIDController m_yawPidController = new PIDController(0.05, 0, 0);


  final CommandXboxController driverXbox = new CommandXboxController(Constants.OperatorConstants.kDriveJoystickPort);

  final CommandJoystick driveJoystick = new CommandJoystick(Constants.OperatorConstants.kDriveJoystickPort);
  final CommandJoystick rotJoystick = new CommandJoystick(Constants.OperatorConstants.kRotJoystickPort);
  /** Creates a new TestAprilTag. */
  public TagAlignmentTeleopCmd(SwerveSubsystem swerveSubsystem, ShooterSubsystem shooterSubsystem, ArmSubsystem armSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    // m_limelightTable = m_inst.getTable("limelight-boombox");
    m_swerveSubsystem = swerveSubsystem;
    m_shooterSubsystem = shooterSubsystem;
    m_armSubsystem = armSubsystem;

    addRequirements(m_swerveSubsystem);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double targetAngularVelocity = LimelightHelpers.getTX("limelight-bside");
    double YValue = LimelightHelpers.getTY("limelight-bside");


    // m_swerveSubsystem.drive(new Translation2d(MathUtil.applyDeadband(-driveJoystick.getRawAxis(1)*2,
    //  OperatorConstants.LEFT_Y_DEADBAND), MathUtil.applyDeadband(-driveJoystick.getRawAxis(0)*2,
    //   OperatorConstants.LEFT_X_DEADBAND)), m_yawPidController.calculate(targetAngularVelocity,0)*2,true);


  m_swerveSubsystem.drive(new Translation2d(MathUtil.applyDeadband(-driverXbox.getLeftX()*2,
    OperatorConstants.LEFT_Y_DEADBAND), MathUtil.applyDeadband(-driverXbox.getLeftX()*2,
     OperatorConstants.LEFT_X_DEADBAND)), m_yawPidController.calculate(targetAngularVelocity,0)*2,true);
    if(YValue <= 7.5 && YValue >= 5.5) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(28);
    } 
    if(YValue < 5.5 && YValue > 3.5) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(30);
    } 
    if(YValue < 3.5 && YValue > 1.5) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(32);
    } 
    if(YValue < 1.5 && YValue > 0) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(34);
    }
    if(YValue < 0 && YValue > -1) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(34);
    }
    if(YValue < -1 && YValue > -2) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(36);
    }
    if(YValue < -2 && YValue > -3) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(38);
    }
    if(YValue < -3 && YValue > -4) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(40);
    } 
    if(YValue < -4 && YValue > -5) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(41);
    } 
    if(YValue < -5 && YValue > -6) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(43);
    }
    if(YValue < -6 && YValue > -7) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(40);
    }
    if(YValue < -7 && YValue > -8) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(42);
    }
    if(YValue < -8 && YValue > -9) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(43);
    }
    if(YValue < -9 && YValue > -10) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(44);
    }
    if(YValue < -10 && YValue > -11) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(45);
    }
    if(YValue < -11 && YValue > -12) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(48);
    }

    if(YValue < -12 && YValue > -13) {
      m_shooterSubsystem.shooterSpeed(0.4);
      m_armSubsystem.setReference(45);
    }
    if(YValue < -13 && YValue > -14) {
      m_shooterSubsystem.shooterSpeed(0.475);
      m_armSubsystem.setReference(47);
    }
    if(YValue < -14 && YValue > -15) {
      m_shooterSubsystem.shooterSpeed(0.425);
      m_armSubsystem.setReference(47);
    }
    if(YValue < -15 && YValue > -16) {
      m_shooterSubsystem.shooterSpeed(0.6);
      m_armSubsystem.setReference(47.5);
    }
    if(YValue < -16 && YValue > -17) {
      m_shooterSubsystem.shooterSpeed(0.5);
      m_armSubsystem.setReference(51);
    }
    if(YValue < -17 && YValue > -18) {
      m_shooterSubsystem.shooterSpeed(0.5);
      m_armSubsystem.setReference(50);
    }
    if(YValue < -18 && YValue > -19) {
      m_shooterSubsystem.shooterSpeed(0.5);
      m_armSubsystem.setReference(51);
    }
    if(YValue == 0) {
      m_shooterSubsystem.shooterOn();
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooterSubsystem.shooterOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
