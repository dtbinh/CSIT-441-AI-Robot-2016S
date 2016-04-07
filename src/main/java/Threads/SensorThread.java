package threads;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;

/**
 * Created by michael on 4/4/2016.
 */
public class SensorThread implements Runnable {
    private static EV3ColorSensor colorSensorDownLeft;
    private static EV3ColorSensor colorSensorDownRight;

    private boolean statement = false;

    public static boolean threadStop = false;

    public static int colorDownIDLeft;
    public static int colorDownIDRight;


    public SensorThread(EV3ColorSensor colorSensorDownLeft, EV3ColorSensor colorSensorDownRight) {
        SensorThread.colorSensorDownLeft = colorSensorDownLeft;
        SensorThread.colorSensorDownRight = colorSensorDownRight;
    }

    @Override
    public void run() {
        while (!Button.ESCAPE.isDown())
            if (statement) {
                colorDownIDLeft = colorSensorDownLeft.getColorID();
                statement = false;
            } else {
                colorDownIDRight = colorSensorDownRight.getColorID();
                statement = true;
            }

    }
}
