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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SpaceDisplayGUI extends JPanel {

    private final GLCanvas glcanvas;
    public SpaceDisplayGUI(GUIHandler guiHandler) {
//        this.setBackground(Color.YELLOW);


        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        glcanvas = new GLCanvas( glcapabilities );

        Renderer r = new Renderer(guiHandler);
        glcanvas.addGLEventListener( r );

        glcanvas.setSize(1600, 900);

        add( glcanvas, BorderLayout.CENTER );

        final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true);
        animator.start();




        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventPostProcessor(new KeyEventPostProcessor() {
            @Override
            public boolean postProcessKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    r.camY += 0.1f;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    r.camY -= 0.1f;
                }
                if  (e.getKeyCode() == KeyEvent.VK_A) {
                    r.camYaw += 1f;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    r.camYaw -= 1f;
                }
                return false;
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        glcanvas.setSize(getWidth()-10, getHeight()-10);
        super.paint(g);
    }
}
