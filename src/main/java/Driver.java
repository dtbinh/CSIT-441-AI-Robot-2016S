import lejos.hardware.Sound;
import lejos.robotics.navigation.MovePilot;
import threads.SensorThread;
import lejos.hardware.Button;
import lejos.remote.ev3.RemoteEV3;
import utils.Notifications;
import utils.PathRecorder;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by michael on 3/16/16.
 */
public class Driver {
    private MovePilot pilot;
    private RemoteEV3 remoteEV3;

    public static boolean stopThread = false;

    private String stop = "stop";
    private String forward = "forward";
    private String left = "left";
    private String right = "right";


    /**
     *
     * @param pilot
     */
    Driver(MovePilot pilot) {
        this.pilot = pilot;
    }

    public void calibrateSensors() {
        //SensorThread.calibrateBothRed();
        //pilot.rotate(160);
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
        pilot.setLinearSpeed(10);
        pilot.setAngularSpeed(20);

        boolean initialRedLeft = true;
        boolean initialRedRight = true;

        PathRecorder recorder = new PathRecorder();
        recorder.initFile();

        while (!(SensorThread.expandSensorLeft() < -50 ||
                SensorThread.expandSensorRight() < -50)) {
            System.out.println("Left Sensor" + SensorThread.expandSensorLeft());
            System.out.println("Right Sensor" + SensorThread.expandSensorRight());
//             Hard turn left check
            if (SensorThread.expandSensorLeft() < -50 ||
                    SensorThread.expandSensorRight() < -50) {
                recorder.writeDirection(stop);
                pilot.stop();
                Sound.beepSequence();
            } else if (SensorThread.expandSensorLeft() < 0) {
                if (initialRedLeft) {
                    SensorThread.setInitialLeftRed();
                    initialRedLeft = false;
                }
            } else if (SensorThread.expandSensorRight() < 0) {
                if (initialRedRight) {
                    SensorThread.setInitialRightRed();
                    initialRedRight = false;
                }
            } else if (SensorThread.expandSensorLeft() < 25) {
                if (initialRedLeft) {
                    SensorThread.setInitialLeftRed();
                    initialRedLeft = false;
                } else {
                    recorder.writeDirection(left);
                    pilot.rotate(30);
                }
            }  else if (SensorThread.expandSensorRight() < 25) {
                if (initialRedRight) {
                    SensorThread.setInitialRightRed();
                    initialRedRight = false;
                } else {
                    recorder.writeDirection(right);
                    pilot.rotate(-30);
                }
            } else if (SensorThread.expandSensorLeft() >= 25 && SensorThread.expandSensorRight() >= 25) {
                recorder.writeDirection(forward);
                pilot.travel(1);
            } else {
                recorder.writeDirection(stop);
                pilot.stop();
                Sound.beepSequence();
            }
        }

        recorder.closeWriter();
    }

    public void moveOnLargeBoard() {
        pilot.setLinearSpeed(10);
        pilot.setAngularSpeed(20);

        PathRecorder recorder = new PathRecorder();
        ArrayList<String> list = recorder.readDirectionFile();

        for (int x = 0; x < list.size(); x++) {
            switch (list.get(x)) {
                case "stop": {
                    pilot.stop();
                    break;
                }

                case "left": {
                    pilot.rotate(30);
                    break;
                }

                case "right": {
                    pilot.rotate(-30);
                }

                case "forward": {
                    pilot.travel(4);
                }
                default: {
                    System.out.println("I didn't have a command");
                }
            }

        }
    }
}
