package org.ironriders.vision;

import edu.wpi.first.math.geometry.Transform3d;

public class VisionConstants {
    public static final String CAM_NAME="apriltagcam";
    public static final String[] CAM_NAMES={"frontLeft","frontRight","backLeft","backRight"};//eventually we will have more cameras
    public static final Transform3d[] CAM_OFFSETS={new Transform3d(),new Transform3d(),new Transform3d(),new Transform3d()};//put the offsets in here
    public static final int[] REEF_TAG_IDS_RED={17,18,19,20,21,22};
    public static final int[] REEF_TAG_IDS_BLUE={6,7,8,9,10,11};
}
