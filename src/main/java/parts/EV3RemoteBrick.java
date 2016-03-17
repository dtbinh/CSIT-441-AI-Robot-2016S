package parts;

import lejos.hardware.Button;
import lejos.hardware.LED;
import lejos.hardware.lcd.LCD;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.remote.ev3.RemoteTextLCD;

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

    private RemoteEV3 ev3;
    private LED led;
    private String address = "192.168.0.11";

    public EV3RemoteBrick() {
        // Setup connection
        connectToMasterBrick();
    }

    public boolean connectToMasterBrick() {
        try {
            //Remote IP address of the secondary brick
            ev3 = new RemoteEV3(address);
        } catch (RemoteException e) {

        } catch (MalformedURLException e) {

        } catch (NotBoundException e) {

        }

        led = ev3.getLED();

        // Returns true if the block completes successfully
        return true;
    }

    public void helloWorld() {
        // Quick indicator that the brains are talking
        led.setPattern(1);
    }

    public void cleanExit() {
        led.setPattern(0);
    }
}