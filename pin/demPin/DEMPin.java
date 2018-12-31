package pinplotter.pin.demPin;

public abstract class DEMPin implements Pin {
    protected static HashMap<int, float> surfEls = new HashMap<int, float>();
    protected static HashMap<int, float> wtEls = new HashMap<int, float>();
    abstract protected void newPin(int x, int y, float val);
    abstract protected void removePin(int x, int y);
    abstract protected float evalPin(int x, int y);

    public boolean isSubmerged(int x, int y) {
        int hashCode = getHashCode(x, y);
        float surfEl = surfEls.get(hashCode);
        float wtEl = wtEls.get(hashCode);
        if (surfEl == null || wtEl == null) {
            print("Insufficient data; assuming not submerged.");
            return false;
        }
        else return (surfEl > wtEl);
    }

    public float submersionDepth(int x, int y) {
        int hashCode = getHashCode(x, y);
        float surfEl = surfEls.get(hashCode);
        float wtEl = wtEls.get(hashCode);
        if (surfEl == null || wtEl == null) {
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
    protected int getHashCode(int x, int y) {
        int tmp = (y+((x+1)/2));
        return x + (tmp * tmp);
    }
}
