// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.IndexerSubsystem;

public class IndexerInCommand extends Command {
  /** Creates a new IntakeCommand. */
  IndexerSubsystem m_indexerSubsystem;
  // LimelightSubsystem m_limelightSubsystem;
  /** Creates a new IntakeCommand. */
  public IndexerInCommand(IndexerSubsystem indexerSubsystem) {
  m_indexerSubsystem = indexerSubsystem;
  // m_limelightSubsystem = limelight;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  //  if (m_indexerSubsystem.beamBreakSensor() == true) {
  //    m_indexerSubsystem.indexerSpeed(Constants.IndexerConstants.kIndexerInSpeed);
  //    LimelightHelpers.setLEDMode_ForceOff("limelight-boombox");
  //  } else  m_indexerSubsystem.indexerOff();
  //       LimelightHelpers.setLEDMode_ForceBlink("limelight-boombox");
         
  m_indexerSubsystem.indexerSpeed(Constants.IndexerConstants.kIndexerInSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_indexerSubsystem.indexerOff();
    LimelightHelpers.setLEDMode_ForceOff("limelight-boombox");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
  //  if(m_indexerSubsystem.beamBreakSensor() == true) {
  //   return false;
  //  }
  //  else return true;
  return false;
  }
}

