package threads;

import lejos.hardware.Button;


public class ButtonShutdownThread implements Runnable {

    public ButtonShutdownThread() {

    }

    @Override
    public void run() {
        Button.waitForAnyPress();



    }
}
