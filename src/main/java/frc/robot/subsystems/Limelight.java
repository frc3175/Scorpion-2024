package frc.robot.subsystems;

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
    NetworkTable table;

    public Limelight() {

        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");

        NetworkTableInstance.getDefault().getTable("limelight").getEntry("priorityid").setNumber(Constants.PRIORITY_ID);

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

        // tx ranges from (-hfov/2) to (hfov/2) in degrees. If your target is on the rightmost edge of 
        // your limelight 3 feed, tx should return roughly 31 degrees.
        double targetingAngularVelocity = tx.getDouble(0.0)* kP;

        // convert to radians per second for our drive method
        targetingAngularVelocity *= (Constants.MAX_ANGULAR_VELOCITY);

        //invert since tx is positive when the target is to the right of the crosshair
        targetingAngularVelocity *= -1.0;

        return targetingAngularVelocity;

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

        //post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightArea", area);

        SmartDashboard.putNumber("Limelight distance", getDistanceToTarget());

        SmartDashboard.putBoolean("is at target", isAtTarget());

    }
    
}
