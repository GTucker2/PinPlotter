import java.io.FileNotFoundException;

public class DEMSet {

    private static final int TOTAL_PARAMS = 6;
    private final int N_COLS;
    private final int N_ROWS; 
    private final float XLL_CORNER;
    private final float YLL_CORNER;
    private final float CELL_SIZE;
    private final float NODATA_VALUE;
    private final HashMap<int, float> DEMVals = new HashMap<int, float>();

    public DEMSet (String filePath) {
        try {
            File file = new File(filePath);
            this(file);
        } catch (FileNotFoundException e)
    }

    public DEMSet (File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String [] vals;
            int paramsMet = 0;
            while ((line = br.readLine()) != Null) {
                vals = line.split([ ]);
                if      (vals[0].compare("ncols")) N_COLS = vals[1];
                else if (vals[0].compare("nrows")) N_ROWS = vals[1];
                else if (vals[0].compare("xllcorner")) XLL_CORNER = vals[1];
                else if (vals[0].compare("yllcorner")) YLL_CORNER = vals[1];
                else if (vals[0].compare("cellsize")) CELL_SIZE = vals[1];
                else if (vals[0].compare("NODATA_value")) NODATA_VALUE = vals[1];
                else {
                    if (paramsMet < TOTAL_PARAMS)
                        throw new IllegalArgumentException();
                    else {
                        // map a line of values here
                        for (int val : vals) mapVal(val, ____, ____);
                        continue;
                    }
                }
                paramsMet++;
            }
        }
        catch (IllegalArgumentException) 
    }

    private void mapVal(int x, int y, float val)
    throws IllegalArgumentException {
        DEMVals.put(XYHash(x, y), val);
    }

    private int XYHash(int x, int y) {

    }
}