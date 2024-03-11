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

    }
    
}
