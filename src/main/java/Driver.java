import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;
import lejos.robotics.navigation.OmniPilot;
import parts.EV3LocalBrick;
import parts.EV3RemoteBrick;

/**
 * Created by michael on 3/16/16.
 */
public class Driver {
    OmniPilot pilot;
    LocalEV3 localEV3;
    RemoteEV3 remoteEV3;

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
    public void start() {
        mapSmallBoard();
    }

    private void mapSmallBoard() {
        pilot.moveStraight(Motor.A.getMaxSpeed(), 0);

        Button.waitForAnyPress();
    }
}
