package parts;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

/**
 * Brick 1 = 192.168.0.10
 * Brick 2 = 192.168.0.11
 */
public class EV3Brick1 {

    public EV3Brick1() {
        helloWorld();
    }

    private void helloWorld() {
        LCD.clear();
        Button.LEDPattern(4);
        LCD.drawString("Ready", 0, 5);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
    }

}

