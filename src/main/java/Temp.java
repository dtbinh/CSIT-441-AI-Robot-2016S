import lejos.hardware.Power;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.HiTechnicGyro;
import lejos.robotics.GyroscopeAdapter;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.OmniPilot;

/**
 * Robot travels to obstacle and back again
 */
public class Temp {
    OmniPilot pilot;
    EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
    static HiTechnicGyro gyro = new HiTechnicGyro(SensorPort.S2);
    static SampleProvider spin = gyro.getMode(0);
    static GyroscopeAdapter myGyro = new GyroscopeAdapter(spin,200f);
    SampleProvider bump = ir.getDistanceMode();
    float[] sample = new float[1];
    static Power battery = LocalEV3.get().getPower();

    public void go() {
        float dist=0;
        pilot.forward();
        while (pilot.isMoving()) {
            bump.fetchSample(sample, 0);
            dist = pilot.getMovement().getDistanceTraveled();
            if (sample[0] < 30) pilot.stop();
        }
        System.out.println("Distance = " + dist);
        pilot.travel(-dist);
    }

    public static void main(String[] args) {
        Temp traveler = new Temp();
        traveler.pilot = new OmniPilot(16f, 4.5f, Motor.C,Motor.A, Motor.B, false, false,battery,myGyro);
        traveler.go();
    }
}