package Threads;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

/**
 * Created by michael on 4/4/2016.
 */
public class SensorThread implements Runnable {
    private EV3ColorSensor colorSensorDown;

    public static boolean threadStop = false;
    public static int colorDownID;


    public SensorThread(EV3ColorSensor colorSensorDown) {
        this.colorSensorDown = colorSensorDown;
    }

    @Override
    public void run() {
        while(threadStop)
        colorDownID = colorSensorDown.getColorID();
    }
}
