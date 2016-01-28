package parts;

import lejos.hardware.Button;
import lejos.hardware.LED;
import lejos.hardware.lcd.LCD;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Assuming the second brick is the club brick
 *
 * Brick 1 = 192.168.0.10
 * Brick 2 = 192.168.0.11
 */
public class EV3RemoteBrick {

    RemoteEV3 ev3;

    private RMIRegulatedMotor motorA;
    private RMIRegulatedMotor motorB;
    private RMIRegulatedMotor motorC;

    private LED led;

    public EV3RemoteBrick() {
        //Setup connection
        connectToMasterBrick();
    }


    /**
     *
     * @return
     */
    public boolean connectToMasterBrick() {
        try {
            //Remote IP address of the secondary brick
            ev3 = new RemoteEV3("192.168.0.11");
        } catch (RemoteException e) {

        } catch (MalformedURLException e) {

        } catch (NotBoundException e) {

        }

        led = ev3.getLED();

        motorA = ev3.createRegulatedMotor("A", 'L');
        motorB = ev3.createRegulatedMotor("B", 'L');
        motorC = ev3.createRegulatedMotor("C", 'L');

        return true;
    }

    private void helloWorld() {
        led.setPattern(1);
    }
}