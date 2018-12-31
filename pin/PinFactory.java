package pinplotter.pin;
import java.util.HashMap;
public class PinFactory {
    private static final HashMap<PinType,Pin> pins = new HashMap<PinType,Pin>();

    public static Pin getPin(PinType type) {
        Pin pinImpl = pins.get(type);
        if (pinImpl == null) {
            if (type.equals(SURF_PIN)) {
                pinImpl = new SurfPin ();
            }
            else if (WT_PIN) {
                pinImpl = new WtPin ();
            }
            else if (WAYPOINT) {
                //pinImpl = new Waypoint ();
            }
            pins.put(type, pinImpl);
        }
        return pinImpl;
    }

    public static enum PinType {
        SURF_PIN, WT_PIN, WAYPOINT;
    }
}
