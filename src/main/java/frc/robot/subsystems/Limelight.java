package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {

    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry tid;
    NetworkTable table;

    double priorityID;

    public Limelight() {

        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tid = table.getEntry("tid");

        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
            if(alliance.get() == DriverStation.Alliance.Red) {
                priorityID = Constants.PRIORITY_ID_RED;
            } else {
                priorityID = Constants.PRIORITY_ID_BLUE;
            }
        } else {
            priorityID = Constants.PRIORITY_ID_BLUE;
        }

        NetworkTableInstance.getDefault().getTable("limelight").getEntry("priorityid").setNumber(priorityID);

    }

    /** 
     * 
     * @return distance from robot to target (inches)
     * 
     */

    public double getDistanceToTarget()  {

        double targetOffsetAngle_Vertical = ty.getDouble(0.0);

        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = Constants.LIMELIGHT_MOUNTING_ANGLE; 

        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = Constants.LIMELIGHT_LENS_HEIGHT; 

        // distance from the target to the floor
        double goalHeightInches = Constants.TARGET_HEIGHT; 

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

        //calculate distance
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

        return distanceFromLimelightToGoalInches;

    }

    public double aimToTarget() {

        double kP = Constants.LIMELIGHT_P;

        double distanceToGoal = getDistanceToTarget();
        double cameraOffset = Constants.CAMERA_OFFSET;
        double errorRadians = Math.atan(distanceToGoal/cameraOffset);
        double errorDegrees = errorRadians * (180/3.14159);

        double cameraToTargetDegrees = tx.getDouble(0.0);
        double centerToTargetDegrees = cameraToTargetDegrees + errorDegrees;

        // tx ranges from (-hfov/2) to (hfov/2) in degrees. If your target is on the rightmost edge of 
        // your limelight 3 feed, tx should return roughly 31 degrees.
        double targetingAngularVelocity = centerToTargetDegrees * kP;

        // convert to radians per second for our drive method
        targetingAngularVelocity *= (Constants.MAX_ANGULAR_VELOCITY);

        //invert since tx is positive when the target is to the right of the crosshair
        targetingAngularVelocity *= -1.0;

        return targetingAngularVelocity;

    }

    public double getTX() {

        double localTX = tx.getDouble(0.0);

        return localTX;

    }

    public boolean isAtTarget() {

        double angle = tx.getDouble(0.0);

        if(Math.abs(angle) <= Constants.ANGLE_TOLERANCE && angle != 0) {

            return true;

        } else {

            return false;

        }

    }

    public boolean hasTarget() {

        double txValue = tx.getDouble(0.0);
        double tyValue = ty.getDouble(0.0);

        if(txValue == 0.0 && tyValue == 0.0) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void periodic() {

        //read values periodically
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);
        double availableID = tid.getDouble(0.0);

        double oldPriorityID = priorityID;

        if(availableID == 3 || availableID == 4) {
            priorityID = Constants.PRIORITY_ID_RED;
        } else if(availableID == 7 || availableID == 8) {
            priorityID = Constants.PRIORITY_ID_BLUE;
        }

        if(oldPriorityID != priorityID) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("priorityid").setNumber(priorityID);
        }

        //post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightArea", area);

        SmartDashboard.putNumber("Limelight distance", getDistanceToTarget());

        SmartDashboard.putBoolean("is at target", isAtTarget());

    }
    
}
