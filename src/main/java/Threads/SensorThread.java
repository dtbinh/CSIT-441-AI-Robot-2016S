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

    // The range that will be used when the values are converted after calibration
    private static int valMin = 0;
    private static int valMax = 100;
    // Values of left sensor over red and over white
    public static float leftRedValue;
    public static float leftWhiteValue;
    // Values of right sensor over red and over white
    public static float rightRedValue;
    public static float rightWhiteValue;

    private boolean statement = false;

    public static boolean threadStop = false;

    public static float colorDownIDLeft;
    public static float colorDownIDRight;

    public static float leftColorDiff;
    public static float rightColorDiff;


    /**
     *
     * @param colorSensorDownLeft
     * @param colorSensorDownRight
     */
    public SensorThread(EV3ColorSensor colorSensorDownLeft, EV3ColorSensor colorSensorDownRight) {
        SensorThread.colorSensorDownLeft = colorSensorDownLeft;
        SensorThread.colorSensorDownRight = colorSensorDownRight;
    }

    /**
     *
     */
    public static void calibrateLeftOnRed() {
        System.out.println("The left sensor reads: " + getLeftRedMode());
        leftRedValue = getLeftRedMode();

        System.out.println("The right sensor reads: " + getRightRedMode());
        rightWhiteValue = getRightRedMode();
    }

    public static void calibrateRightOnRed() {
        System.out.println("The left sensor reads: " + getLeftRedMode());
        leftWhiteValue = getLeftRedMode();

        System.out.println("The right sensor reads: " + getRightRedMode());
        rightRedValue = getRightRedMode();
    }

    public static void calibrateBothRed() {
//        System.out.println("The left sensor reads: " + getLeftRedMode());
        leftRedValue = getLeftRedMode();

//        System.out.println("The right sensor reads: " + getRightRedMode());
        rightRedValue = getRightRedMode();
    }

    public static void calibrateBothWhite() {
//        System.out.println("The left sensor reads: " + getLeftRedMode());
        leftWhiteValue = getLeftRedMode();

//        System.out.println("The right sensor reads: " + getRightRedMode());
        rightWhiteValue = getRightRedMode();
    }

    public static float getLeftRedMode() {
        SampleProvider providerLeft = colorSensorDownLeft.getRedMode();
        float[] sampleLeft = new float[1];
        providerLeft.fetchSample(sampleLeft, 0);
        float returnSample = sampleLeft[0] * 100;
        System.out.println("Left: " + returnSample);
        return returnSample;
    }

    public static float getRightRedMode() {
        SampleProvider providerRight = colorSensorDownRight.getRedMode();
        float[] sampleRight = new float[1];
        providerRight.fetchSample(sampleRight, 0);
        float returnSample = sampleRight[0] * 100;
        System.out.println("Right: " + returnSample);
        return returnSample;
    }

    public static float expandSensor(float input, float localMin, float localMax) {
        return (((input - localMin)/(localMax - localMin) * (valMax - valMin)) + valMin);
        //( (old_value - old_min) / (old_max - old_min) ) * (new_max - new_min) + new_min
    }

    public static float expandSensorLeft() {
        return expandSensor(SensorThread.getLeftRedMode(), leftRedValue, leftWhiteValue);
    }

    public static float expandSensorRight() {
        return expandSensor(SensorThread.getRightRedMode(), rightRedValue, rightWhiteValue);
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
