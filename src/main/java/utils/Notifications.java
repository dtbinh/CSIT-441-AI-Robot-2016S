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
        Sound.setVolume(20);
        Sound.playTone(1760, 200);
        Sound.pause(10);
        Sound.playTone(1760,200);
        Sound.pause(20);
        Sound.playTone(1760,400);
        Sound.pause(40);
        Sound.playTone(1321,200);
        Sound.pause(10);
        Sound.playTone(1484, 200);
        Sound.pause(40);
        Sound.playTone(1174, 200);
        Sound.pause(100);
        Sound.playTone(2349,300);
        Sound.setVolume(10);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
        Button.LEDPattern(0);
    }

    /**
     * Error notification
     * Flashes the LED to red flashing
     * Plays an errorWithPause tone
     */
    public static void errorWithPause() {
        Button.LEDPattern(8);
        Sound.buzz();
    }

    public static void errorWithPause(String errorText) {
        LCD.clear();
        Button.LEDPattern(8);
        LCD.drawString(errorText, 0, 5);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
        Button.LEDPattern(0);
    }

    public static void runningOperation() {
        Button.LEDPattern(5);
    }

    public static void notifyShutdown() {
        Button.LEDPattern(4);
        Sound.buzz();
    }

    /**
     * Clears the color/pattern of the button LED
     */
    public static void clearStatusColor() {
        Button.LEDPattern(0);
    }
}
