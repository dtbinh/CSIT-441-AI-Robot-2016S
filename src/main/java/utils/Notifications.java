package utils;


import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

//    0: turn off button lights
//    1/2/3: static light green/red/yellow
//    4/5/6: normal blinking light green/red/yellow
//    7/8/9: fast blinking light green/red/yellow
//    >9: same as 9
public class Notifications {

    //The class will likely be used as a util class so it should not be instantiated
    private Notifications() {

    }

    /**
     * Method for startup
     * Clears the screen
     * Displays a "Ready" message
     * Waits for the center button to be pressed
     * On press clears screen and returns
     */
    public static void ready() {
        LCD.clear();
        Button.LEDPattern(4);
        LCD.drawString("Ready", 0, 5);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
        Button.LEDPattern(0);
    }

    /**
     * Error notification
     * Flashes the LED to red flashing
     * Plays an error tone
     */
    public static void error() {
        Button.LEDPattern(5);
        Sound.buzz();
    }
}
