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
public class Driver implements Runnable {
    OmniPilot pilot;
    LocalEV3 localEV3;
    RemoteEV3 remoteEV3;

    public static boolean stopThread = false;

    public Driver(OmniPilot pilot) {
        this.pilot = pilot;
    }

    public Driver(OmniPilot pilot, LocalEV3 localEV3, RemoteEV3 remoteEV3) {
        this.pilot = pilot;
        this.localEV3 = localEV3;
        this.remoteEV3 = remoteEV3;
    }

    /**
     * Starts the solving function
     * Begin with mapping the test boards
     *
     */
//    public void start() {
//        mapSmallBoard();
//        testProxSensors();
//    }


    @Override
    public void run() {
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
        //pilot.arc(10, 100);
        pilot.arc(10, 100, 5);

//        while (SensorThread.colorDownIDLeft != Color.WHITE) {
////            pilot.moveStraight(Motor.A.getMaxSpeed() / 200, 0);
//            pilot.travel(100, 30, false);
//
//            // If the robot sees white, have it re align itself
//            while (SensorThread.colorDownIDLeft != Color.RED) {
//                pilot.rotate(-5);
//            }
//        }
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
