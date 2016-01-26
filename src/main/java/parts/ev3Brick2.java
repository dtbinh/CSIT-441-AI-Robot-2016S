package parts;


import lejos.remote.ev3.RemoteEV3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ev3Brick2 {


    public boolean connectToMasterBrick() {
        try {
            ev3 = new RemoteEV3("192.168.43.18");
        } catch (RemoteException e) {

        } catch (MalformedURLException e) {

        } catch (NotBoundException e) {

        }

        return false;
    }
}