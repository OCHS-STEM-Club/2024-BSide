// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new climberSubsystem. */
  private CANSparkMax climberMotor;
  private RelativeEncoder climberEncoder;
  public boolean climberbool;
  public DutyCycleEncoder throughBoreEncoder;

  public ClimberSubsystem() {
    climberMotor = new CANSparkMax(Constants.ClimberConstants.kClimberMotorID, MotorType.kBrushless);
    climberEncoder = climberMotor.getEncoder();
    climberMotor.setIdleMode(IdleMode.kBrake);
    climberMotor.setInverted(true);
    climberMotor.setSmartCurrentLimit(10, 10);
    climberMotor.getEncoder();
    throughBoreEncoder = new DutyCycleEncoder(2);

    climberMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 100);
    climberMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 100);
    climberMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 100);
    climberMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 100);
    
  }
  @Override
  public void periodic() {

    SmartDashboard.putNumber("Climber Encoder Position", climberEncoder.getPosition());
    SmartDashboard.putNumber("climber Motor Speed", climberMotor.get());
    SmartDashboard.putNumber("Climber Motor current", climberMotor.getOutputCurrent());
  }

  public void climberDown() {
    if (climberEncoder.getPosition() <= 0 ) {
      climberMotor.set(0.98);
   } else climberMotor.set(0);
}

  public void climberUp() {
   if (climberEncoder.getPosition() >= -180) {
      climberMotor.set(-0.98);
    }  else climberMotor.set(0);
    
  }

  public void climberUpOverride() {
  climberMotor.set(-0.8);
  }

  public void climberDownOverride() {
  climberMotor.set(0.8);
  }

  public void climberOff() {
    climberMotor.set(0);
  }


  public boolean isClimberdown() {
    if(climberbool == true) {
      return true;
    } else 
      return false;
  }

  public void climberEncoderZero() {
    climberEncoder.setPosition(0);
  }
  






}