package pinplotter.pin.demPin;
import java.util.HashMap;
public final class WtPin extends DEMPin {
    public static void addPin(int x, int y, float val){
        int hashCode = getHashCode(x, y);
        wtEls.put(hashCode, val);
    }
    public static float evalPin(int x, int y){
        int hashCode = getHashCode(x, y);
        float wtEl = wtEls.get(hashCode);
        if (wtEl == null); // log some error message;
        else return wtEl;
    }
    public static SurfPin removePin(int x, int y){
        int hashCode = getHashCode(x, y);
        if (wtEls.get(hashCode)) wtEls.remove(hashCode);
        else; // log some error message;
    }
}
