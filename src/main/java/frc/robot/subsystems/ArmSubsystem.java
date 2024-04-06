// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty.Type;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  private CANSparkMax armMotorRight;
  private CANSparkMax armMotorLeft;
  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {
    armMotorRight = new CANSparkMax(Constants.ArmConstants.kArmMotorRightID, MotorType.kBrushless);
    armMotorLeft = new CANSparkMax(Constants.ArmConstants.kArmMotorLeftID, MotorType.kBrushless);


    // armMotorLeft.follow(armMotorRight);

    // armMotorLeft.setInverted(false);
    // armMotorRight.setInverted(true);


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  public void armUp() {
    armMotorRight.set(0.2);  
    armMotorLeft.set(0.2);  
  }
  public void armRightUp() {
    armMotorRight.set(0.2);  
  }

  public void armLeftUp() {
    armMotorLeft.set(-0.2);  
  }

  public void armoff() {
    armMotorRight.set(0);  
    armMotorLeft.set(0);  
  }
}
