package org.example.server.guiComponents;

import org.example.server.GUIHandler;

import javax.swing.*;
import java.awt.*;

public class SpaceDrawingSettingsGUI extends JPanel {

    public SpaceDrawingSettingsGUI(GUIHandler handler) {
        setBackground(Color.GREEN);
        add(new JLabel("Sensor List"));
    }
}
