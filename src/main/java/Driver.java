import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.MovePilot;
import sun.management.Sensor;
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
    private MovePilot pilot;
    private RemoteEV3 remoteEV3;

    public static boolean stopThread = false;


    /**
     *
     * @param pilot
     */
    Driver(MovePilot pilot) {
        this.pilot = pilot;
    }

    public void calibrateSensors() {
        SensorThread.calibrateBothRed();
        pilot.rotate(170);
        SensorThread.calibrateBothWhite();
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

    public void mapSmallBoard() {
        pilot.setLinearSpeed(20);
        pilot.setAngularSpeed(20);

        System.out.println("Left exp: " + SensorThread.expandSensorLeft());
        System.out.println("Right exp: " + SensorThread.expandSensorRight());

//        while (Button.ESCAPE.isUp()) {
//            // Hard turn left check
////            if (SensorThread.expandSensor(SensorThread.getLeftRedMode(), SensorThread.leftWhiteValue, SensorThread.rightRedValue) < 25) {
////                pilot.rotate(30);
////            }  else if (SensorThread.expandSensor(SensorThread.getRightRedMode(), SensorThread.rightWhiteValue, SensorThread.getRightRedMode()) < 25) {
////                pilot.rotate(-30);
////            } else
////            if (SensorThread.expandSensor(SensorThread.getLeftRedMode(), SensorThread.leftWhiteValue, SensorThread.rightRedValue) > 25 &&
////                    SensorThread.expandSensor(SensorThread.getRightRedMode(), SensorThread.rightWhiteValue, SensorThread.getRightRedMode()) > 25) {
////                pilot.travel(20);
////            } else {
////                pilot.stop();
////            }
//        }
    }
}
