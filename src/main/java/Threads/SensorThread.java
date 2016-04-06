package threads;

import lejos.hardware.sensor.EV3ColorSensor;

/**
 * Created by michael on 4/4/2016.
 */
public class SensorThread implements Runnable {
    private EV3ColorSensor colorSensorDownLeft;
    private EV3ColorSensor colorSensorDownRight;

    public static boolean threadStop = false;
    public static int colorDownIDLeft;
    public static int colorDownIDRight;


    public SensorThread(EV3ColorSensor colorSensorDownLeft, EV3ColorSensor colorSensorDownRight) {
        this.colorSensorDownLeft = colorSensorDownLeft;
        this.colorSensorDownRight = colorSensorDownRight;
    }

    @Override
    public void run() {
        while(!threadStop)
        colorDownIDLeft = colorSensorDownLeft.getColorID();
        colorDownIDRight = colorSensorDownRight.getColorID();
    }
}
