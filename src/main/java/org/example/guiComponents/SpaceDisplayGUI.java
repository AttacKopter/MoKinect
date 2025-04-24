package org.example.guiComponents;

import com.jogamp.opengl.util.FPSAnimator;
import org.example.GUIHandler;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;
import org.example.Computation.Renderer;

import javax.swing.*;
import java.awt.*;

public class SpaceDisplayGUI extends JPanel {

    private final GLCanvas glcanvas;
    public SpaceDisplayGUI(GUIHandler guiHandler) {
//        this.setBackground(Color.YELLOW);


        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        glcanvas = new GLCanvas( glcapabilities );

        Renderer triangle = new Renderer(guiHandler);
        glcanvas.addGLEventListener( triangle );

        glcanvas.setSize(400, 400);

        add( glcanvas, BorderLayout.CENTER );

        final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true);
        animator.start();
    }

    @Override
    public void paint(Graphics g) {
        glcanvas.setSize(getWidth()-10, getHeight()-10);
        super.paint(g);
    }
}
