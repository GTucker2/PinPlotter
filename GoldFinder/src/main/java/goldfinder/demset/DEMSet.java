package goldfinder.demset;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DEMSet {

    private static final int TOTAL_PARAMS = 6;
    private int N_COLS;
    private int N_ROWS; 
    private float XLL_CORNER;
    private float YLL_CORNER;
    private float CELL_SIZE;
    private float NODATA_VALUE;
    private final HashMap<Float, Float> DEMVals = new HashMap<Float, Float>();

    public DEMSet (File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line, tag;
            String [] vals;
            int paramsMet = 0;
            float yOffset = 0;
            while ((line = br.readLine()) != null) {
                vals = line.split("\\s+");
                tag = vals[0];
                if      (tag.equals("ncols")) N_COLS = Integer.parseInt(vals[1]);
                else if (tag.equals("nrows")) N_ROWS = Integer.parseInt(vals[1]);
                else if (tag.equals("xllcorner")) XLL_CORNER = Float.parseFloat(vals[1]);
                else if (tag.equals("yllcorner")) YLL_CORNER = Float.parseFloat(vals[1]);
                else if (tag.equals("cellsize")) CELL_SIZE = Float.parseFloat(vals[1]);
                else if (tag.equals("NODATA_value")) NODATA_VALUE = Float.parseFloat(vals[1]);
                else {
                    if (paramsMet < TOTAL_PARAMS) {
                        throw new IllegalArgumentException();
                    }
                    else {
                        // map a line of values here
                        System.out.println(yOffset);
                        mapVals(vals, yOffset);
                        yOffset += CELL_SIZE;
                        continue;
                    }
                }
                paramsMet++;
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Illegal argument exception!");
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }
        catch (IOException e) {
            System.out.println("IOException!");
        }
    }

    private void mapVals(String[] vals, float yOffset)
    throws IllegalArgumentException {
        float x = XLL_CORNER;
        float y = YLL_CORNER + yOffset;
        for (int i = 1; i < vals.length; i++) {
            DEMVals.put(getCantorKey(x, y), Float.parseFloat(vals[i]));
            x += CELL_SIZE;
        }
    }

    private float getCantorKey(float x, float y) {
        return ((x + y)*(x + y + 1)/2) + y;
    }
}