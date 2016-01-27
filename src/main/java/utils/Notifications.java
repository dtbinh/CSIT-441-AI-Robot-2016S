package utils;


import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Notifications {

    //The class will likely be used as a util class so it should not be instantiated
    private Notifications() {

    }

    /**
     * Method for startup
     * Clears the screen
     */
    public static void ready() {
        LCD.clear();
        Button.LEDPattern(4);
        LCD.drawString("Ready", 0, 5);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
    }

    public static void error() {
        Button.LEDPattern(0);

    }
}
