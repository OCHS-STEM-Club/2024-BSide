// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Indexer;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IndexerInCmd extends Command {
  /** Creates a new IntakeCommand. */
  IndexerSubsystem m_indexerSubsystem;
  ArmSubsystem m_armSubsystem;
  // LimelightSubsystem m_limelightSubsystem;
  /** Creates a new IntakeCommand. */
  public IndexerInCmd(IndexerSubsystem indexerSubsystem, ArmSubsystem armSubsystem) {
  m_indexerSubsystem = indexerSubsystem;
  m_armSubsystem = armSubsystem;
  
  // m_limelightSubsystem = limelight;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   if (m_indexerSubsystem.beamBreakSensor() == true && m_armSubsystem.getArmEncoderPosition() <= 5) {
     m_indexerSubsystem.indexerIn();
     
   } else if (m_indexerSubsystem.beamBreakSensor() == false)
          m_indexerSubsystem.indexerOff();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_indexerSubsystem.indexerOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
   if(m_indexerSubsystem.beamBreakSensor() == true) {
    return false;
   }
   else return true;
  }
}
