package pinplotter.pin.demPin;

public class DEMPin implements Pin {
    private static HashMap<Int,Float> surfEls = new HashMap<Int, Float>();
    private static HashMap<Int,Float> wtEls = new HashMap<Int, Float>();
    private PinType type;

    public DEMPin(PinType type)
    throws IllegalArgumentException {
        if (!type.equals(PinType.SURF_PIN) && !type.equals(PinType.WT_PIN)) {
            throw new IllegalArgumentException("DEMPin may not be of the "+
            "given type: " + type);
        }
        this.type = type;
    }

    public void addPin(int x, int y, float val){
        int hashCode = getHashCode(x, y);
        if (type.equals(PinType.SURF_PIN)) surfEls.put(hashCode, val);
        else if (type.equals(PinType.WT_PIN)) wtEls.put(hashCode, val);
    }

    public float evalPin(int x, int y){
        float val;
        int hashCode = getHashCode(x, y);
        if (type.equals(PinType.SURF_PIN)) val = surfEls.get(hashCode);
        else if (type.equals(PinType.WT_PIN)) val = wtEls.get(hashCode);
        if (val == Null) return 0.0f; // log some error message;
        else return val;
    }

    public void removePin(int x, int y){
        int hashCode = getHashCode(x, y);
        if (type.equals(PinType.SURF_PIN)) {
            if (surfEls.get(hashCode)) surfEls.remove(hashCode);
            else; // log some error message;
        }
        else if (type.equals(PinType.WT_PIN)) {
            if (wtEls.get(hashCode)) wtEls.remove(hashCode);
            else; // log some error message;
        }
    }

    public boolean isSubmerged(int x, int y) {
        int hashCode = getHashCode(x, y);
        float surfEl = surfEls.get(hashCode);
        float wtEl = wtEls.get(hashCode);
        if (surfEl == Null || wtEl == Null) {
            print("Insufficient data; assuming not submerged.");
            return false;
        }
        else return (surfEl > wtEl);
    }

    public float submersionDepth(int x, int y) {
        int hashCode = getHashCode(x, y);
        float surfEl = surfEls.get(hashCode);
        float wtEl = wtEls.get(hashCode);
        if (surfEl == Null || wtEl == Null) {
            print("Insufficient data; assuming not submerged; returning 0.0f.");
            return 0.0f;
        }
        else if (surfEl > wtEl)
            return (wtEl - surfEl);
        else {
            print("Not submerged; returning 0.0f.");
            return 0.0f;
        }
    }

    /**
    * Bijective algorithm suggested by Mani @https://stackoverflow.com/questions
    * /22826326/good-hashcode-function-for-2d-coordinates for producing a
    * hashcode from two integers.
    * @param x the x-coordinate of the pin.
    * @param y the y-coordinate of the pin.
    */
    private static int getHashCode(int x, int y) {
        int tmp = (y+((x+1)/2));
        return x + (tmp * tmp);
    }
}
