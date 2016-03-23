import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.Power;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.HiTechnicGyro;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Gyroscope;
import lejos.robotics.GyroscopeAdapter;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.OmniPilot;
import parts.EV3LocalBrick;
import parts.EV3RemoteBrick;
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
 *      Sensor 2: 120 Clockwise Prox
 *      Sensor 3: 120 CClockwise Prox
 *      Sensor 4: Gyroscope
 *
 *  Brick 2:
 *      Motor A: Forward color
 *      Motor B: Downward color
 *      Motor C:
 *      Motor D:
 *      Sensor 1:
 *      Sensor 2:
 *      Sensor 3:
 *      Sensor 4:
 *
 *  Main class will simply act as a container to start operations
 *  Will pass off actual movement and solving to Driver class
 */
public class Main {

    private LocalEV3 localEV3;
    private RemoteEV3 remoteEV3;
    private OmniPilot pilot;

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
        setup();

        Driver driver = new Driver(pilot, localEV3, remoteEV3);
        driver.start();
    }

    /**
     * Contained methods to initiate all of the objects and classes required for the robot to move
     */
    private void setup() {
        // Dictates the 2 brains and sets the remote control of the second brain
        try {
            //Remote IP address of the secondary brick
            remoteEV3 = new RemoteEV3(address);
        } catch (RemoteException e) {

        } catch (MalformedURLException e) {

        } catch (NotBoundException e) {

        }

        // Sets up the pilot class
        setupPilotClassWithoutGyro();

        //Testing the ready functionality
        Notifications.ready();
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

        pilot.setSpeed((int) Motor.A.getMaxSpeed());
    }
}