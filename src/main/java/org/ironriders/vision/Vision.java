package org.ironriders.vision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Optional;
import org.ironriders.core.FieldConstants;
import org.ironriders.drive.DriveSubsystem;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;

public class Vision {

    List<PhotonCamera> cams = new ArrayList<>();
    List<PhotonPoseEstimator> poseEstimators = new ArrayList<>();

    public Vision() {

        for (VisionConstants.Camera cam : VisionConstants.CAMERAS) {
            cams.add(new PhotonCamera(cam.name));
            poseEstimators
                    .add(new PhotonPoseEstimator(FieldConstants.FIELD_LAYOUT,
                            PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, cam.offset));
        }
    }

    /**
     * Takes a swerve drive and adds pose estimate
     * @param swerveDrive The swerve drive.
     */
    public void addPoseEstimate(SwerveDrive swerveDrive) {

        int index = 0;
        for (PhotonPoseEstimator estimator : poseEstimators) {
            if (cams.get(index).getLatestResult().hasTargets()) {
                Optional<EstimatedRobotPose> optional = estimator.update(cams.get(index).getLatestResult());
                if (optional.isEmpty())
                    break;
                
                EstimatedRobotPose poseEstimate = optional.get();
                swerveDrive.addVisionMeasurement(poseEstimate.estimatedPose.toPose2d(), poseEstimate.timestampSeconds);
            }
            index++;
        }
    }

    /**
     * Gets the closest tag to the front camera
     * @return An OptionalInt representing the id of the closest tagg if present.
     */
    public OptionalInt getClosestTagToFront() {

        PhotonPipelineResult result = cams.get(0).getLatestResult();
        if (!result.hasTargets())
            return OptionalInt.empty();

        Iterator<PhotonTrackedTarget> iterator = result.getTargets().iterator();
        PhotonTrackedTarget closest = iterator.next();

        while (iterator.hasNext()) {
            PhotonTrackedTarget target = iterator.next();
            if (target.getBestCameraToTarget().getX() < closest.getBestCameraToTarget().getX()) {
                closest = target;
            }
        }

        return OptionalInt.of(closest.fiducialId);
    }
}
