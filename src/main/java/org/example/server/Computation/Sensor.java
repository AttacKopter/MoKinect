package org.example.server.Computation;

import com.jogamp.opengl.GL2;
import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;
import org.example.server.GUIHandler;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Sensor {

    private float x,y,z;
    private float yaw,pitch,roll;

    private GUIHandler guiHandler;

    private volatile Skeleton skeleton;

    public Sensor(GUIHandler guiHandler) {
        this.guiHandler = guiHandler;

        x =0;
        y = -1;
        z = 1;
        yaw = 0;
        pitch = 0;
        roll = 0;

        SensorHandler sensorHandler = new SensorHandler();
        sensorHandler.start(J4KSDK.SKELETON | J4KSDK.PLAYER_INDEX | J4KSDK.DEPTH | J4KSDK.UV | J4KSDK.XYZ);

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    skeleton = sensorHandler.skeleton;
                }
            }
        }).start();

        guiHandler.sensors.add(this);

    }

    public Sensor(GUIHandler guiHandler, Socket s) {
        this.guiHandler = guiHandler;

        x = 0;
        y = -1;
        z = 1;
        yaw = 0;
        pitch = 0;
        roll = 0;
        new Thread(new Runnable() {
            public void run() {
                try {
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String message = dis.readUTF();
                        skeleton = SensorHandler.getSkeletonFromString(message);
                    }

                } catch (Exception e) {}
            }
        }).start();

        guiHandler.sensors.add(this);
    }


    public void draw(GL2 gl, boolean drawSkeletons, boolean drawDepthMap) {


        gl.glPushMatrix();

        gl.glRotatef(yaw, 0, 1, 0);
        gl.glRotatef(pitch, 0, 0, 1);
        gl.glRotatef(roll, 0, 0, 1);

        gl.glTranslatef(x, y, z);

        drawSensor(gl);
        if(drawSkeletons) {drawSkeleton(gl);}
//        if(drawDepthMap) {}

        gl.glPopMatrix();
    }

    private void drawSensor(GL2 gl) {
        float width =0.249f;
        float height = 0.066f;
        float depth = 0.067f;

        gl.glPushMatrix();
        gl.glTranslatef(-0.04f, 0f,0f);

        gl.glColor3f(0.9f,0.9f,0.9f);
        gl.glLineWidth(1f);
        gl.glBegin(GL2.GL_LINES);

        gl.glVertex3f(0,0,0);
        gl.glVertex3f(width,0,0);

        gl.glVertex3f(0,0,0);
        gl.glVertex3f(0,0,depth);

        gl.glVertex3f(0,0,0);
        gl.glVertex3f(0,height,0);

        gl.glVertex3f(width,height,depth);
        gl.glVertex3f(width,height,0);

        gl.glVertex3f(width,height,depth);
        gl.glVertex3f(width,0,depth);

        gl.glVertex3f(width,height,depth);
        gl.glVertex3f(0,height,depth);

        gl.glVertex3f(0,height,0);
        gl.glVertex3f(width,height,0);

        gl.glVertex3f(0,height,0);
        gl.glVertex3f(0,height,depth);

        gl.glVertex3f(width,0,depth);
        gl.glVertex3f(width,0,0);

        gl.glVertex3f(width,0,depth);
        gl.glVertex3f(0,0,depth);

        gl.glVertex3f(width,0,0);
        gl.glVertex3f(width,height,0);

        gl.glVertex3f(0,0,depth);
        gl.glVertex3f(0,height,depth);




        gl.glEnd();


        gl.glPopMatrix();

    }

    private void drawSkeleton(GL2 gl) {

        Skeleton s = skeleton;


        if (s==null) {return;}

        gl.glPointSize(2.0f);
        gl.glBegin(GL2.GL_POINTS);

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        for (int i = 0; i < 25; i++) {
            gl.glVertex3f(-s.get3DJointX(i), s.get3DJointY(i), s.get3DJointZ(i));  // Draw joint
        }
        gl.glEnd();
//        for (int i = 0; i < 25; i++) {
//            renderTextAtPosition(gl, i, s.get3DJointX(i), s.get3DJointY(i), s.get3DJointZ(i));
//        }
        for (int[] pair : SkeletonConstants.skeletonLinks) {
            drawBone(gl, s,  pair[0], pair[1]);
        }

    }

    private void drawBone(GL2 gl, Skeleton s, int id1, int id2) {
        float x1 = -s.get3DJointX(id1);
        float y1 = s.get3DJointY(id1);
        float z1 = s.get3DJointZ(id1);
        float x2 = -s.get3DJointX(id2);
        float y2 = s.get3DJointY(id2);
        float z2 = s.get3DJointZ(id2);
        float size = (float) Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2)+Math.pow(z1-z2,2));
        gl.glLineWidth(2f);
        gl.glBegin(GL2.GL_LINES);

        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(x1, y1, z1);
        gl.glVertex3f(x2, y2, z2);

        gl.glEnd();


    }


}
