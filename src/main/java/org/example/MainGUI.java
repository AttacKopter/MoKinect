package org.example;

import org.example.guiComponents.*;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private GUIHandler handler;
    private JPanel pane;

    public MainGUI(GUIHandler handler) {

        this.handler = handler;
        setName("MoKinect");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        popluateGUI();


    }

    private void popluateGUI() {

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        add(new SpaceDrawingSettingsGUI(handler), c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 2.0;
        c.weighty = 2.0;
        add(new SpaceDisplayGUI(handler), c);

        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridheight = 2;
        add(new MainControlsGUI(handler), c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        add(new SensorSettingsGUI(handler), c);

        c.gridx = 1;
        c.gridy = 1;
        add(new SensorListGUI(handler), c);

        getContentPane().setBackground(Color.DARK_GRAY);
        setVisible(true);
        setSize(800, 600);

    }
}
