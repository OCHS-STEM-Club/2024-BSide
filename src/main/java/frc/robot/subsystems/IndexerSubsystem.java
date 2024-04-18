// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty.Type;
// import com.ctre.phoenix.Logger;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */

        private CANSparkMax indexerMotor;
        private DigitalInput indexerSensor;
        private double speed;

    public IndexerSubsystem() {
        indexerMotor = new CANSparkMax(Constants.IndexerConstants.kIndexerMotorID, MotorType.kBrushless);
        indexerSensor = new DigitalInput(Constants.IndexerConstants.kBeamBreakPort);
        indexerMotor.setInverted(false);
        indexerMotor.setIdleMode(IdleMode.kBrake);

        

        indexerMotor.setSmartCurrentLimit(30,30);

        indexerMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 100);
        indexerMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 100);
        indexerMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 100);
        indexerMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 100);
        
    }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean(("Beam Brake"), beamBreakSensor());
  }

  public void indexerSpeed(double speed){
    indexerMotor.set(speed);
  }

  public void indexerIn() {
    indexerMotor.set(Constants.IndexerConstants.kIndexerInSpeed);
  }

  public void indexerOff() {
    indexerMotor.set(0);
  }

  public void indexerOut() {
    indexerMotor.set(Constants.IndexerConstants.kIndexerOutSpeed);
  }

  public boolean beamBreakSensor() {
    return indexerSensor.get();
  }
}

