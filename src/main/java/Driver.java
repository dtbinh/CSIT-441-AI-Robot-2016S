import lejos.hardware.Sound;
import lejos.robotics.navigation.MovePilot;
import threads.SensorThread;
import lejos.hardware.Button;
import lejos.remote.ev3.RemoteEV3;
import utils.Notifications;
import utils.PathRecorder;

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
        pilot.rotate(160);
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

        PathRecorder recorder = new PathRecorder();
        recorder.initFile();

        while (!(SensorThread.expandSensorLeft() < -50 ||
                SensorThread.expandSensorRight() < -50)) {
//             Hard turn left check
            if (SensorThread.expandSensorLeft() < -50 ||
                    SensorThread.expandSensorRight() < -50) {
                recorder.writeDirection("stop");
                pilot.stop();
                Sound.beepSequence();
            } else if (SensorThread.expandSensorLeft() < 25) {
                recorder.writeDirection("left");
                pilot.rotate(30);
            }  else if (SensorThread.expandSensorRight() < 25) {
                recorder.writeDirection("right");
                pilot.rotate(-30);
            } else if (SensorThread.expandSensorLeft() >= 25 &&
                    SensorThread.expandSensorRight() >= 25) {
                recorder.writeDirection("forward");
                pilot.travel(1);
            } else {
                recorder.writeDirection("stop");
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
        for (String direction : recorder.readDirectionFile())
        switch (direction) {
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
        }

    }
}
