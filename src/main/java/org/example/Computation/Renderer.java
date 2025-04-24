package org.example.Computation;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import edu.ufl.digitalworlds.j4k.Skeleton;
import org.example.GUIHandler;

public class Renderer implements GLEventListener {
    private GUIHandler guiHandler;
    private GLUT glut;

    public Renderer(GUIHandler guiHandler) {
        this.guiHandler = guiHandler;
        this.glut = new GLUT();
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);  // Clear the screen and depth buffer
        gl.glLoadIdentity();  // Reset the view

//        drawGridAtY(gl, -2.0f);

        gl.glPointSize(4.0f);  // Increase the point size

        // Drawing the points for skeleton joints
        gl.glBegin(GL2.GL_POINTS);
//        gl.glBegin(GL2.GL_LINE_STRIP);

        Skeleton s = guiHandler.getSkeleton();
        if (s != null) {
            System.out.println("(" + s.get3DJointX(1) + "," + s.get3DJointY(1) + "," + s.get3DJointZ(1) + ")");
            for (int i = 0; i < 25; i++) {
                gl.glColor3f(1.0f, 0.0f, 0.0f);  // Set color to black for joints
                gl.glVertex3f(s.get3DJointX(i), s.get3DJointY(i), s.get3DJointZ(i));  // Draw joint
            }
        }

        gl.glEnd();

        if (s != null) {
            for (int i = 0; i < 25; i++) {
                renderTextAtPosition(gl, i, s.get3DJointX(i), s.get3DJointY(i), s.get3DJointZ(i));
            }
        }


        gl.glFlush();  // Ensure all OpenGL commands are executed
    }

    private void renderTextAtPosition(GL2 gl, int pointNumber, float x, float y, float z) {
        gl.glPushMatrix();  // Save the current matrix state
        gl.glTranslatef(x, y, z);  // Move to the joint's position

        // Set color for text (white color)
        gl.glColor3f(1.0f, 1.0f, 1.0f);

        // Set the scale for the text (so it doesn't overlap with other points)
//        gl.glScalef(0.005f, 0.005f, 0.005f);

        // Render the point number at the joint's position
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, String.valueOf(pointNumber));

        gl.glPopMatrix();  // Restore the matrix state
    }

    // Function to draw a 2D grid at a specific y value (in this case, y = -2)
    private void drawGridAtY(GL2 gl, float yPosition) {
        gl.glColor3f(0.8f, 0.8f, 0.8f);  // Set grid color (light gray)

        // Grid spacing and range
        float gridSpacing = 1.0f;  // Space between grid lines
        int gridSize = 10;  // Size of the grid (how far the lines extend)

        // Draw vertical grid lines at y = yPosition
        gl.glBegin(GL2.GL_LINES);
        for (int i = -gridSize; i <= gridSize; i++) {
            gl.glVertex3f(i * gridSpacing, yPosition, -gridSize * gridSpacing);  // Left side of the grid (negative z direction)
            gl.glVertex3f(i * gridSpacing, yPosition, gridSize * gridSpacing);   // Right side of the grid (positive z direction)
        }
        gl.glEnd();

        // Draw horizontal grid lines at y = yPosition
        gl.glBegin(GL2.GL_LINES);
        for (int i = -gridSize; i <= gridSize; i++) {
            gl.glVertex3f(-gridSize * gridSpacing, yPosition, i * gridSpacing);  // Bottom side of the grid (negative x direction)
            gl.glVertex3f(gridSize * gridSpacing, yPosition, i * gridSpacing);   // Top side of the grid (positive x direction)
        }
        gl.glEnd();
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // Cleanup code if needed
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        // Set up perspective projection
        GLU glu = new GLU();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(90.0f, 1.0f, 0.1f, 100.0f);  // 45° field of view, aspect ratio 1:1, near and far planes
//        gl.glTranslatef(0.0f, -1.0f, -10.0f);

        glu.gluLookAt(0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 2.0f,
                0.0f, 1.0f, 0.0f);

        // Set up modelview matrix (this is where you can position objects)
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Enable depth testing
        gl.glEnable(GL2.GL_DEPTH_TEST);  // Enable depth testing to ensure proper rendering of 3D objects
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Adjust the viewport when the window is resized
    }
}
