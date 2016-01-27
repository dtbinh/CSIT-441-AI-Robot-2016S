package parts;

import lejos.hardware.LED;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Brick 1 = 192.168.0.10
 * Brick 2 = 192.168.0.11
 */
public class ev3Brick2 {

    RemoteEV3 ev3;

    private LED led;

    public boolean connectToMasterBrick() {
        try {
            ev3 = new RemoteEV3("192.168.43.18");
        } catch (RemoteException e) {

        } catch (MalformedURLException e) {

        } catch (NotBoundException e) {

        }

        led = ev3.getLED();

        return true;
    }
}