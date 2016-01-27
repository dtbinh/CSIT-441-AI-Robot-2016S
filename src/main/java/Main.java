import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import utils.Notifications;

public class Main {

    /**
     * Main
     * Start of program
     * @param args
     */
    public static void main(String[] args) {
        //Check that the bricks are working
        LCD.clear();
        LCD.drawString("First EV3 Program", 0, 5);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
    }

    private void setup() {
        //Testing the ready functionality
        Notifications.ready();
    }
}