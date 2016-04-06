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

    private LocalEV3 localEV3;
    private RemoteEV3 remoteEV3;
    private OmniPilot pilot;

    // Local parts
    EV3ColorSensor colorSensorDownLeft;
    EV3ColorSensor colorSensorDownRgiht;

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
        try {
            remoteEV3 = new RemoteEV3(address);
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            Notifications.errorWithPause("Could not connect brains");
            System.exit(1);
        }

        // TODO Setup local sensors
        colorSensorDownLeft = new EV3ColorSensor(SensorPort.S2);
        colorSensorDownRgiht = new EV3ColorSensor(SensorPort.S3);

        // Sets up the pilot class
        setupPilotClassWithoutGyro();

        // Setup threads
        Thread driver = new Thread(new Driver(pilot, localEV3, remoteEV3));

//        SensorThread sensorThread = new SensorThread(colorSensorDownLeft);
        Thread sensorThread = new Thread(new SensorThread(colorSensorDownLeft, colorSensorDownRgiht));

        Notifications.ready();


        sensorThread.start();

        driver.start();

        Button.waitForAnyPress();

        SensorThread.threadStop = true;
        Driver.stopThread = true;

        closeParts();
    }

    /**
     * Sets up the Omnipilot class
     */
    private void setupPilotClass() {
        // Builds the gyro object
        HiTechnicGyro gyro = new HiTechnicGyro(SensorPort.S2);
        SampleProvider spin = gyro.getMode(0);
        GyroscopeAdapter myGyro = new GyroscopeAdapter(spin,200f);

        // Parameters:
        // wheelDistanceFromCenter - the wheel distance from center
        // wheelDiameter - the wheel diameter
        // centralMotor - the central motor
        // CW120degMotor - the motor at 120 degrees clockwise from front
        // CCW120degMotor - the motor at 120 degrees counter-clockwise from front
        // centralWheelFrontal - if true, the central wheel frontal else it is facing back
        // motorReverse - if motors are mounted reversed
        // gyro - the gyroscope
        pilot = new OmniPilot(wheelDistanceFromCenter, wheelDiameter, Motor.A, Motor.C, Motor.B, true, true, LocalEV3.get().getPower(), myGyro);

        pilot.setSpeed((int) Motor.A.getMaxSpeed());
    }

    /**
     * Sets up the Omnipilot class
     * Does not initiate gyroscope as part of the class
     */
    private void setupPilotClassWithoutGyro() {
        // Parameters:
        // wheelDistanceFromCenter - the wheel distance from center
        // wheelDiameter - the wheel diameter
        // centralMotor - the central motor
        // CW120degMotor - the motor at 120 degrees clockwise from front
        // CCW120degMotor - the motor at 120 degrees counter-clockwise from front
        // centralWheelFrontal - if true, the central wheel frontal else it is facing back
        // motorReverse - if motors are mounted reversed
        // gyro - the gyroscope
        pilot = new OmniPilot(wheelDistanceFromCenter, wheelDiameter, Motor.A, Motor.C, Motor.B, true, true, LocalEV3.get().getPower());

        pilot.setSpeed((int) Motor.A.getMaxSpeed() / 32);
    }

    private void closeParts() {
        // Close local parts
        colorSensorDownLeft.close();
    }
}