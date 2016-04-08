import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.MovePilot;
import threads.SensorThread;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;
import lejos.robotics.navigation.OmniPilot;
import utils.Notifications;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by michael on 3/16/16.
 */
public class Driver {
    MovePilot pilot;
    RemoteEV3 remoteEV3;
    private long reverseTime = 100;
    private int speed = 1;
    public static boolean stopThread = false;


    /**
     *
     * @param pilot
     */
    Driver(MovePilot pilot) {
        this.pilot = pilot;
    }

    /**
     * Starts the solving function
     * Begin with mapping the test boards
     *
     */
    public void start() {
        mapSmallBoard();
    }

    public void setThreadStop() {
        stopThread = true;
    }

    /**
     * Execute task with a timer
     * On time out will exit task and close down robot
     */
    public void startWithTimer() {
        Notifications.runningOperation();

        // Setup a timer thread.  Will stop execution after 3 minutes
        long in3Minutes = 3 * 60 * 1000;
        Timer timer = new Timer();
        timer.schedule( new TimerTask(){
            public void run() {
                // TODO Running code here
            }
        },  in3Minutes );


        Notifications.notifyShutdown();
    }

    private void mapSmallBoard() {
        pilot.setLinearSpeed(20);
        pilot.setAngularSpeed(20);

//        while (SensorThread.colorDownIDLeft >= .60 && SensorThread.colorDownIDRight >= .60) {
        while (Button.ESCAPE.isUp()) {
            if (SensorThread.colorDownIDLeft < (SensorThread.leftWhiteValue - .05) && SensorThread.colorDownIDRight < (SensorThread.rightWhiteValue - .05)) {
                pilot.travel(-3, true);
            } else if (SensorThread.colorDownIDLeft < (SensorThread.leftWhiteValue - .05)) {
                pilot.rotate(10, true);
                // Turn robot right
            }  else if (SensorThread.colorDownIDRight < (SensorThread.rightWhiteValue - .05)) {
                pilot.rotate(-10, true);
            } else {
                pilot.travel(2, true);
            }
        }
    }

    private void testProxSensors() {
        EV3UltrasonicSensor sensor = new EV3UltrasonicSensor(SensorPort.S1);
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);

        Button.LEDPattern(4);

        while (colorSensor.getColorID() == Color.RED) {
            Button.waitForAnyPress();
            LCD.drawString(sensor.getDistanceMode().sampleSize() + "", 0, 5);
            LCD.refresh();
        }
    }
}
