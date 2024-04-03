// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

// import com.ctre.phoenix.Logger;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSubsystem extends SubsystemBase {
  /** Creates a new IndexerSubsystem. */

        private CANSparkMax indexerMotor;
        private DigitalInput indexerSensor;
        private double speed;

    public IndexerSubsystem() {
        indexerMotor = new CANSparkMax(Constants.IndexerConstants.kIndexerMotorID, MotorType.kBrushless);
        indexerSensor = new DigitalInput(Constants.IndexerConstants.kBeamBreakPort);
        indexerMotor.setInverted(false);//TODO:Check Inversions

        

        indexerMotor.setSmartCurrentLimit(30,30);

        indexerMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 100);
        indexerMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 100);
        indexerMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 100);
        indexerMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 100);
        
    }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    Logger.recordOutput("Indexer Speed", indexerMotor.get());
    Logger.recordOutput("Beam Break", beamBreakSensor());
    
  }
  //TODO:Add if statement for intaking when arm is down or lower limit switch is pressed

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
