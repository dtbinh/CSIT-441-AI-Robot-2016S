import lejos.hardware.ev3.LocalEV3;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.navigation.OmniPilot;

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

    }

    private void mapSmallBoard() {

    }
}
