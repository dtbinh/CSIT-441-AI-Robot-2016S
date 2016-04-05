package Threads;

import lejos.hardware.Button;


public class ButtonShutdownThread implements Runnable {

    public ButtonShutdownThread() {

    }

    @Override
    public void run() {
        Button.waitForAnyPress();

        // TODO Build shutdown code, then call code here

    }
}
