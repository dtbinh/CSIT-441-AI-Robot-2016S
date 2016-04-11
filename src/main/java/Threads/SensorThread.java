package threads;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

/**
 * (max'-min')/(max-min)(v-max)+max'
 * (max'-min')/(max-min)(v-min)+min'
 */
public class SensorThread implements Runnable {
    private static EV3ColorSensor colorSensorDownLeft;
    private static EV3ColorSensor colorSensorDownRight;

    private boolean statement = false;

    public static boolean threadStop = false;

    public static float colorDownIDLeft;
    public static float colorDownIDRight;
    public static float leftRedValue;
    public static float leftWhiteValue;
    public static float rightRedValue;
    public static float rightWhiteValue;
    public static float leftColorDiff;
    public static float rightColorDiff;



    public SensorThread(EV3ColorSensor colorSensorDownLeft, EV3ColorSensor colorSensorDownRight) {
        SensorThread.colorSensorDownLeft = colorSensorDownLeft;
        SensorThread.colorSensorDownRight = colorSensorDownRight;
    }

    public static void calibrate() {
        // Calibrate 1
        System.out.println("Place left color sensor on tape");
        Button.waitForAnyPress();

        SampleProvider providerLeft = colorSensorDownLeft.getRedMode();
        float[] sampleLeft = new float[1];
        providerLeft.fetchSample(sampleLeft, 0);
        System.out.println("The left sensor reads: " + sampleLeft[0]);
        leftRedValue = sampleLeft[0];

        SampleProvider provider = colorSensorDownRight.getRedMode();
        float[] sample = new float[1];
        provider.fetchSample(sample, 0);
        System.out.println("The right sensor reads: " + sample[0]);
        rightWhiteValue = sample[0];

        // Calibrate 2
        System.out.println("Place right color sensor on tape");
        Button.waitForAnyPress();

        providerLeft = colorSensorDownLeft.getRedMode();
        sampleLeft = new float[1];
        providerLeft.fetchSample(sampleLeft, 0);
        System.out.println("The left sensor reads: " + sampleLeft[0]);
        leftWhiteValue = sampleLeft[0];

        provider = colorSensorDownRight.getRedMode();
        sample = new float[1];
        provider.fetchSample(sample, 0);
        System.out.println("The right sensor reads: " + sample[0]);
        rightRedValue = sample[0];

        leftColorDiff = leftWhiteValue - leftRedValue;
        rightColorDiff = rightWhiteValue - rightRedValue;

    }

    @Override
    public void run() {
        while (!Button.ESCAPE.isDown())
            if (statement) {
                SampleProvider provider = colorSensorDownLeft.getRedMode();
                float[] sample = new float[1];
                provider.fetchSample(sample, 0);
                colorDownIDLeft = sample[0];
                statement = false;
            } else {
                SampleProvider provider = colorSensorDownRight.getRedMode();
                float[] sample = new float[1];
                provider.fetchSample(sample, 0);
                colorDownIDRight = sample[0];
                statement = true;
            }
    }
}
