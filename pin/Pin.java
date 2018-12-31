package pinplotter.pin;

public interface Pin {
    //put functions here which all pins will need
    void addPin(int x, int y, float val);
    void removePin(int x, int y);
    float evalPin(int x, int y);
    //void drawPin();
}
