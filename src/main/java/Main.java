import lejos.robotics.Color;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import threads.SensorThread;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.HiTechnicGyro;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.GyroscopeAdapter;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.OmniPilot;
import utils.Notifications;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Brick 1:
 *      Motor A: Forward wheel
 *      Motor B: 120 Cclockwise wheel
 *      Motor C: 120 Clockwise wheel
 *      Motor D: Putter arm
 *      Sensor 1: Forward Prox
 *      Sensor 2: Color Sensor Left
 *      Sensor 3: Color Sensor Right
 *      Sensor 4:
 *
 *  Brick 2:
 *      Motor A: Forward color
 *      Motor B: Downward color
 *      Motor C:
 *      Motor D:
 *      Sensor 1: Prox Sensor Left
 *      Sensor 2: Prox Sensor Right
 *      Sensor 3: Color Sensor Front
 *      Sensor 4:
 *
 *  Main class will simply act as a container to start operations
 *  Will pass off actual movement and solving to Driver class
 */
public class Main {
    private OmniPilot pilot;
    private MovePilot movePilot;

    private String address = "192.168.0.11";

    // wheelDistanceFromCenter - the wheel distance from center
    private float wheelDistanceFromCenter = 4.75f;
    // wheelDiameter - the wheel diameter
    private float wheelDiameter = 1.875f;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        new Main();
    }

    /**
     * Actual start of program as denoted by Kate's object oriented design
     */
    public Main() {
        // Connects the 2 brains and sets the remote control of the second brain
//        try {
//            remoteEV3 = new RemoteEV3(address);
//        } catch (RemoteException | MalformedURLException | NotBoundException e) {
//            Notifications.errorWithPause("Could not connect brains");
//            System.exit(1);
//        }

        Driver driver = new Driver(setupNewPilotClass());

        Thread sensorThread = new Thread(new SensorThread(new EV3ColorSensor(SensorPort.S2), new EV3ColorSensor(SensorPort.S3)));

//        SensorThread sensorThread = new SensorThread(new EV3ColorSensor(SensorPort.S2), new EV3ColorSensor(SensorPort.S3));
        // Start execution

        SensorThread.calibrate();

        Notifications.ready();

        sensorThread.start();

        driver.start();

        // Close components
        SensorThread.threadStop = true;
        Driver.stopThread = true;
    }

    private MovePilot setupNewPilotClass() {
        Wheel wheel1 = WheeledChassis.modelHolonomicWheel(Motor.A, wheelDiameter).polarPosition(0, wheelDistanceFromCenter).gearRatio(2);
        Wheel wheel2 = WheeledChassis.modelHolonomicWheel(Motor.B, wheelDiameter).polarPosition(120, wheelDistanceFromCenter).gearRatio(2);
        Wheel wheel3 = WheeledChassis.modelHolonomicWheel(Motor.C, wheelDiameter).polarPosition(240, wheelDistanceFromCenter).gearRatio(2);
        Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2, wheel3}, WheeledChassis.TYPE_HOLONOMIC);

        return movePilot = new MovePilot(chassis);

    }


}