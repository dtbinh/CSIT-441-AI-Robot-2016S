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

    /**
     *
     */
    public static void calibrateRightOnRed() {
        System.out.println("The left sensor reads: " + getLeftRedMode());
        leftWhiteValue = getLeftRedMode();

        System.out.println("The right sensor reads: " + getRightRedMode());
        rightRedValue = getRightRedMode();
    }

    /**
     *
     */
    public static void calibrateBothRed() {
        leftRedValue = getLeftRedMode();
        rightRedValue = getRightRedMode();
    }

    /**
     *
     */
    public static void calibrateBothWhite() {
        leftWhiteValue = getLeftRedMode();
        rightWhiteValue = getRightRedMode();
    }

    /**
     *
     * @return
     */
    public static float getLeftRedMode() {
        SampleProvider providerLeft = colorSensorDownLeft.getRedMode();
        float[] sampleLeft = new float[1];
        providerLeft.fetchSample(sampleLeft, 0);
        return sampleLeft[0] * 100;
    }

    /**
     *
     * @return
     */
    public static float getRightRedMode() {
        SampleProvider providerRight = colorSensorDownRight.getRedMode();
        float[] sampleRight = new float[1];
        providerRight.fetchSample(sampleRight, 0);
        return sampleRight[0] * 100;
    }

    /**
     *
     * @return
     */
    public static void setInitialLeftRed() {
        leftRedValue = getLeftRedMode();
    }

    /**
     *
     * @return
     */
    public static void setInitialRightRed() {
        rightRedValue = getRightRedMode();
    }

    /**
     *
     * @param input
     * @param localMin
     * @param localMax
     * @return
     */
    public static float expandRange(float input, float localMin, float localMax) {
        return (((input - localMin)/(localMax - localMin)) * (valMax - valMin) + valMin);
        //( (old_value - old_min) / (old_max - old_min) ) * (new_max - new_min) + new_min
    }

    /**
     *
     * @return
     */
    public static float expandSensorLeft() {
        return expandRange(SensorThread.getLeftRedMode(), leftRedValue, leftWhiteValue);
    }

    /**
     *
     * @return
     */
    public static float expandSensorRight() {
        return expandRange(SensorThread.getRightRedMode(), rightRedValue, rightWhiteValue);
    }

    /**
     *
     */
    @Override
    public void run() {
        while (Button.ESCAPE.isUp()) {
            if (statement) {
                float result = getLeftRedMode();
                colorDownIDLeft = result;
                if (result > leftWhiteValue) {
                    leftWhiteValue = result;
                }
                statement = false;
            } else {
                float result = getRightRedMode();
                colorDownIDRight = result;
                if (result > rightWhiteValue) {
                    rightWhiteValue = result;
                }
                statement = true;
            }
        }
    }
}
