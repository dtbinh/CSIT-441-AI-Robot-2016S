import lejos.hardware.Button;
import lejos.hardware.LED;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.OmniPilot;
import parts.EV3LocalBrick;
import parts.EV3RemoteBrick;

import java.rmi.RemoteException;

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
        // Move bottom color sensor to gyro input, it's not currently in use

        EV3ColorSensor colorSensor = new EV3ColorSensor(remoteEV3.getPort("S2"));

        colorSensor.getColorID();

        while (colorSensor.getColorID() != Color.BLACK) {
            pilot.moveStraight(Motor.A.getMaxSpeed() / 16, 0);


            // If the robot sees white, have it re align itself
            while (colorSensor.getColorID() != Color.RED) {
                pilot.rotate(-20);
            }


        }

        Button.waitForAnyPress();

        colorSensor.close();
    }
}
