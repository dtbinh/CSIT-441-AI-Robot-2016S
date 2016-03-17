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
import lejos.robotics.Gyroscope;
import lejos.robotics.GyroscopeAdapter;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.OmniPilot;
import parts.EV3LocalBrick;
import parts.EV3RemoteBrick;
import utils.Notifications;

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

    private  EV3LocalBrick localEV3;
    private  EV3RemoteBrick remoteEV3;
    private OmniPilot pilot;

    // wheelDistanceFromCenter - the wheel distance from center
    private float wheelDistanceFromCenter = 12;
    // wheelDiameter - the wheel diameter
    private float wheelDiameter = 10;


    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        setup();
        setupPilotClass();

        pilot.moveStraight((int) Motor.A.getMaxSpeed(), 0);

        while(!Motor.B.isStalled()) {

        }

        pilot.rotate(90);

        pilot.forward();

        Button.waitForAnyPress();

        cleanExit();
    }

    private void setup() {
        localEV3 = new EV3LocalBrick();
        remoteEV3 = new EV3RemoteBrick();

        //Testing the ready functionality
        Notifications.ready();
    }

    /**
     * Basic hello world to test the deploy functionality is working properly
     */
    private void helloWorld() {
        localEV3.helloWorld();
        remoteEV3.helloWorld();

        LCD.clear();
        LCD.drawString("First EV3 Program", 0, 5);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
    }

    private void cleanExit() {
        localEV3.cleanExit();
        remoteEV3.cleanExit();
    }

    private void setupPilotClass() {
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
}