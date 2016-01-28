package utils;

import java.util.Timer;
import java.util.TimerTask;

public class TimerThread extends Thread {
    private Timer timer;
    private int timerTime;

    /**
     * Default constructor for utils.TimerThread
     * Sets the default time for the alarm at 2 minutes
     */
    public TimerThread() {
        // Sets the default time to 2 minutes
        setTimerTime(2*60);
    }

    /**
     * Creates a new timer object
     * Sets the time alotment for the timer object to the time given in timeInSeconds
     * @param timeInSeconds The time for the timer to run
     */
    public void setTimerTime(int timeInSeconds){
        timer = new Timer();
        timerTime = timeInSeconds * 1000;
    }

    /**
     * Run method from extending thread
     */
    public void run() {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Set the task to time out after set interval
            }
        }, timerTime);
    }


}
