package pinplotter.pin;
import java.util.HashMap;
public class PinFactory {
    private static final HashMap<PinType,Pin> pins = new HashMap<PinType,Pin>();

    public static Pin getPin(PinType type) {
        Pin pinImpl = pins.get(type);
        if (pinImpl == null) {
            if (type.equals(PinType.SURF_PIN)) {
                pinImpl = new DEMPin(PinType.SURF_PIN);
            }
            else if (type.equals(Pintype.WT_PIN)) {
                pinImpl = new DEMPin(PinType.WT_PIN);
            }
            pins.put(type, pinImpl);
        }
        return pinImpl;
    }
}
