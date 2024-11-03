// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class ArmTag extends Command {
  /** Creates a new ArmTag. */

  private final ArmSubsystem m_armSubsystem;
  private final SwerveSubsystem m_swerveSubsystem;

  public ArmTag(SwerveSubsystem swerveSubsystem, ArmSubsystem armSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_swerveSubsystem = swerveSubsystem;
    m_armSubsystem = armSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_armSubsystem.setReference(m_swerveSubsystem.getAngleToSpeaker()+25);
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
