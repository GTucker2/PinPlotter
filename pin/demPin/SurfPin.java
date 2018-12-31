package pinplotter.pin.demPin;
import java.util.HashMap;
public final class SurfPin extends DEMPin {
    public static void addPin(int x, int y, float val){
        int hashCode = getHashCode(x, y);
        surfEls.put(hashCode, val);
    }
    public static float evalPin(int x, int y){
        int hashCode = getHashCode(x, y);
        float surfEl = surfEls.get(hashCode);
        if (surfEl == null); // log some error message;
        else return surfEl;
    }
    public static SurfPin removePin(int x, int y){
        int hashCode = getHashCode(x, y);
        if (surfEls.get(hashCode)) surfEls.remove(hashCode);
        else; // log some error message;
    }
}
