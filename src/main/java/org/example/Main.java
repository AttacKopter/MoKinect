package org.example;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import org.example.Computation.Sensor;
import org.example.Computation.SensorHandler;

public class Main {
    public static void main(String[] args) {
        GUIHandler guiHandler = new GUIHandler();
        new MainGUI(guiHandler);

        Sensor s = new Sensor(guiHandler);

    }
}