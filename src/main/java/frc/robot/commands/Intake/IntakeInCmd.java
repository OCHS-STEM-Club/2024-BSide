// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeInCmd extends Command {
  /** Creates a new IntakeInCmd. */
  private IntakeSubsystem m_intakeSubsystem;
  private ArmSubsystem m_armSubsystem;
  private IndexerSubsystem m_indexerSubsystem;

  public IntakeInCmd(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem, IndexerSubsystem indexerSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intakeSubsystem = intakeSubsystem;
    m_armSubsystem = armSubsystem;
    m_indexerSubsystem = indexerSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_armSubsystem.getArmEncoderPosition() <= 5 && m_indexerSubsystem.beamBreakSensor() == true) {
     m_intakeSubsystem.intakeIn();

   } else 
        m_intakeSubsystem.intakeOff();

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intakeSubsystem.intakeOff();
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
