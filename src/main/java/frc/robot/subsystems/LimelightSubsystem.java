// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class LimelightSubsystem extends SubsystemBase {
  /** Creates a new Limelight. */
  private double limelightMoutingAngle;
  private double limelightHeightInches;
  private double aprilTagHeight;
  private double subwooferHeight;
  private double armMountHeight;
  private double angleToSubwoofer;
  public LimelightSubsystem() {
    limelightMoutingAngle = 37;
    limelightHeightInches = 19.75;
    aprilTagHeight = 57;
    subwooferHeight = 77.5;

    armMountHeight = 23.25;


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double YValue = LimelightHelpers.getTY("limelight-bside");

    double angleToGoalDegrees = limelightMoutingAngle + YValue;
    double oppsiteSize = subwooferHeight - armMountHeight;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180);

    double distanceFromTag = (aprilTagHeight - limelightHeightInches) / Math.tan(angleToGoalRadians);
    double adjacentSize = (aprilTagHeight - armMountHeight) / Math.tan(angleToGoalRadians);

    double angleToSubwoofer = Math.atan(oppsiteSize/adjacentSize) *360;

    SmartDashboard.putNumber("Subwoofer Angle", angleToSubwoofer);
    SmartDashboard.putNumber("Distance Subwoofer", distanceFromTag);

    SmartDashboard.putNumber("Yvalue", YValue);

    
  }

  public double angleToSubwoofer() {
    return angleToSubwoofer;
  }
}
;