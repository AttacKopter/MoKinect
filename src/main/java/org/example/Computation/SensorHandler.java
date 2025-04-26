package org.example.Computation;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.DepthMap;
import edu.ufl.digitalworlds.j4k.Skeleton;
import edu.ufl.digitalworlds.j4k.VideoFrame;
import org.example.GUIHandler;

public class SensorHandler extends J4KSDK {

    VideoFrame videoTexture;
    Sensor sensor;
    Skeleton skeleton;

    public SensorHandler(Sensor sensor) {
        super();
        this.sensor = sensor;
        videoTexture=new VideoFrame();
    }

    public void onDepthFrameEvent(short[] depth_frame, byte[] player_index, float[] XYZ, float[] UV) {

        DepthMap map=new DepthMap(getDepthWidth(),getDepthHeight(),XYZ);
        if(UV!=null) map.setUV(UV);

//        guiHandler.setMap(map);


    }

    /*The following method will run every time a new skeleton frame
      is received from the Kinect sensor. The skeletons are converted
      into Skeleton objects.*/
    @Override
    public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] joint_position, float[] joint_orientation, byte[] joint_status) {

        Skeleton[] skeletons = new Skeleton[getMaxNumberOfSkeletons()];
        for(int i=0;i<getMaxNumberOfSkeletons();i++) {
            skeletons[i] = Skeleton.getSkeleton(i, skeleton_tracked, joint_position, joint_orientation, joint_status, this);

            float x = Math.abs(skeletons[i].get3DJointX(1));
            float y = Math.abs(skeletons[i].get3DJointY(1));
            float z = Math.abs(skeletons[i].get3DJointZ(1));


            if( (100> x && x > 0.0000001) && (100> y && y > 0.0000001) && (100> z && z > 0.0000001)) {
                skeleton = skeletons[i];
            }
        }
    }

    /*The following method will run every time a new color frame
      is received from the Kinect video camera. The incoming frame
      is passed to the videoTexture object to update its content.*/
    @Override
    public void onColorFrameEvent(byte[] data) {

        videoTexture.update(getColorWidth(), getColorHeight(), data);
    }

}
