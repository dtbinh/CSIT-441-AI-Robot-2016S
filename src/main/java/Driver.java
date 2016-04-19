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

            // If both sensors have a sudden drop in brightness, assume that we have found the black at the end
            if (SensorThread.expandSensorLeft() < -50 ||
                    SensorThread.expandSensorRight() < -50) {
                recorder.writeDirection(stop);
                pilot.stop();
                Sound.beepSequence();

                //
            } else if (SensorThread.expandSensorLeft() < 25) {
                recorder.writeDirection(left);
                pilot.rotate(30);

            }  else if (SensorThread.expandSensorRight() < 25) {
                recorder.writeDirection(right);
                pilot.rotate(-30);

            } else if (SensorThread.expandSensorLeft() >= 25 && SensorThread.expandSensorRight() >= 25) {
                if (initialRedLeft && SensorThread.expandSensorLeft() < 80) {
                    System.out.println("Left:" + SensorThread.expandSensorLeft());
                    SensorThread.setInitialLeftRed();
                    initialRedLeft = false;
                    recorder.writeDirection(left);
                    pilot.rotate(30);
                } else if (initialRedRight && SensorThread.expandSensorRight() < 80) {
                    System.out.println("Right:" + SensorThread.expandSensorRight());
                    SensorThread.setInitialRightRed();
                    initialRedRight = false;
                    recorder.writeDirection(right);
                    pilot.rotate(-30);
                } else {
                    recorder.writeDirection(forward);
                    pilot.travel(1);
                }
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

        for (int x = 2; x < list.size(); x++) {
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

    public void moveOnLargeBoard2() {
        pilot.setLinearSpeed(10);
        pilot.setAngularSpeed(20);
        String value;
        int count = 0;
        boolean changed = false;
        int turn = 30;
        int distance = 4;

        PathRecorder recorder = new PathRecorder();
        ArrayList<String> list = recorder.readDirectionFile();

        for (int x = 2; x < list.size(); x++) {
            count = 0;
            value = list.get(x);

            while (x < list.size() && list.get(x).equals(value)) {
                count++;
                x++;
            }

            switch (value) {
                case "stop": {
                    pilot.stop();
                    break;
                }

                case "left": {
                    pilot.rotate(turn * count);
                    break;
                }

                case "right": {
                    pilot.rotate(-turn * count);
                }

                case "forward": {
                    pilot.travel(distance * count);
                }
                default: {
                    System.out.println("I didn't have a command");
                }
            }

            x--;
        }

    }
}
