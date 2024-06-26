// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase {
  private CANSparkMax armMotorRight;
  private CANSparkMax armMotorLeft;

  private SparkPIDController m_armPidController;
  private SparkAbsoluteEncoder m_armEncoder;

  private DigitalInput m_magLimitSwitch;

  private double armValue;
  public boolean tuningMode;
  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {
    armMotorRight = new CANSparkMax(Constants.ArmConstants.kArmMotorRightID, MotorType.kBrushless);
    armMotorLeft = new CANSparkMax(Constants.ArmConstants.kArmMotorLeftID, MotorType.kBrushless);
    armMotorRight.setIdleMode(IdleMode.kBrake);
    
    armMotorLeft.setIdleMode(IdleMode.kBrake);

    // Works
    m_magLimitSwitch = new DigitalInput(9);
    
    armMotorRight.setSmartCurrentLimit(30, 20);
    armMotorLeft.setSmartCurrentLimit(30, 20);

    m_armEncoder = armMotorRight.getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);
    // m_armEncoder.setPositionConversionFactor(360);
    // m_armEncoder.setZeroOffset(ArmConstants.kEncoderZeroOffset);
    m_armEncoder.setInverted(true);

    m_armPidController = armMotorRight.getPIDController();

    m_armPidController.setP(ArmConstants.kP);
    m_armPidController.setI(ArmConstants.kI);
    m_armPidController.setD(ArmConstants.kD);
    m_armPidController.setIZone(ArmConstants.kIz);
    m_armPidController.setFF(ArmConstants.kFF);
    m_armPidController.setOutputRange(ArmConstants.kMinOutput, ArmConstants.kMaxOutput);
    m_armPidController.setFeedbackDevice(m_armEncoder);

    armMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 100);
    armMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 100);
    armMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 100);
    armMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 100);

    armMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 100);
    armMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 100);
    armMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 100);
    armMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 100);

    armValue = 0;
    tuningMode = false;

    SmartDashboard.putNumber("Arm Referece Value", armValue);
    SmartDashboard.putBoolean("Tuning Mode", tuningMode);


  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Arm absolute encoder", m_armEncoder.getPosition());
    SmartDashboard.putNumber("Arm Motor Right Current", armMotorRight.getOutputCurrent());
    SmartDashboard.putNumber("Arm Motor Left Current", armMotorLeft.getOutputCurrent());
    SmartDashboard.putNumber("Arm Motor Right Speed", armMotorRight.get());
    SmartDashboard.putNumber("Arm Motor Left Speed", armMotorLeft.get());

   
    // System.out.println(m_magLimitSwitch.get());
    SmartDashboard.putBoolean("Mag Limit Switch", m_magLimitSwitch.get());

    double value = SmartDashboard.getNumber("Arm Referece Value", 0);
    boolean tuningMode = SmartDashboard.getBoolean("Tuning Mode", false);
    if((tuningMode == true)) { 
      m_armPidController.setReference(value,CANSparkMax.ControlType.kPosition); armValue = value; 
    }


  }

  public void armUp() {
    armMotorRight.set(0.2);  
    armMotorLeft.set(0.2); 
  }

  public void armDown() {
    if (m_magLimitSwitch.get() == false ) {//TODO:Not tested yet
      armMotorRight.set(-0.2);
      armMotorLeft.set(-0.2);
    }
    if (m_magLimitSwitch.get() == true) {
      this.armoff();
    }
  }

  public void armRightUp() {
    armMotorRight.set(0.2);  
  }

  public void armLeftUp() {
    armMotorLeft.set(-0.2);  
  }

  public double getArmEncoderPosition() {
    return m_armEncoder.getPosition();
  }

  public void armoff() {
    armMotorRight.set(0);  
    armMotorLeft.set(0);  
  }

  public void shooterSetpoint() {
    m_armPidController.setReference(ArmConstants.kShooterSetpoint, CANSparkMax.ControlType.kPosition);
  }

  public void intakeSetpoint() {
    if (m_magLimitSwitch.get() == false ) {//TODO:Not tested yet
      m_armPidController.setReference(ArmConstants.kIntakeSetpoint, CANSparkMax.ControlType.kPosition);
    }
    // m_armPidController.setReference(ArmConstants.kIntakeSetpoint, CANSparkMax.ControlType.kPosition);
  }

  public void ampSetpoint() {
    m_armPidController.setReference(ArmConstants.kAmpSetpoint, CANSparkMax.ControlType.kPosition);
  }

  public void climberSetpoint() {
    m_armPidController.setReference(ArmConstants.kClimberSetpoint, CANSparkMax.ControlType.kPosition);
  }


  public void setReference(double setpoint) {
    m_armPidController.setReference(setpoint, CANSparkMax.ControlType.kPosition);
  }

  public boolean getMagLimitSwitch() {
    return m_magLimitSwitch.get();
  }
}

