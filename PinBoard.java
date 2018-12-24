package goldfinder;
import java.io.*;
import java.util.regex;

/**
* The PinBoard class supports the organization of Pin data.
* A PinBoard keeps track of its dimensions, as well as its location
* in physical, longitudinal-latitudinal space at a set definition (in meters).
* @author  Griffin A. Tucker
* @version 1.0
* @since   2018-12-19
*/
public class PinBoard {
    // When conditions are not met or no information is provided, the class
    // will be defined with the following constants.
    private static final int MIN_NCOLS = 1;
    private static final int MIN_NROWS = 1;
    private static final int MIN_CELLSIZE = 1;
    private static final float DFLT_SWLONG = 0.0f;
    private static final float DFLT_SWLAT = 0.0f;
    private static final float DFLT_NODATA = 0.0f;
    // PinBoards are purely read-only; all information is private.
    private int nCols;
    private int nRows;
    private int cellSize;
    private float swLong;
    private float swLat;
    private float NODATA_VAL;
    private Pin[][] pins;

    /**
    * Constructs a default pinboard.
    */
    public PinBoard() {
        float longi = DFLT_SWLONG;
        float lati = DFLT_SWLAT;
        this.nCols  = MIN_NCOLS;
        this.nRows  = MIN_NROWS;
        this.swLong = DFLT_SWLONG;
        this.swLat  = DFLT_SWLAT;
        this.cellSize = MIN_CELLSIZE;
        this.pins   = new Pin[MIN_NCOLS][MIN_NROWS];
        for (int iCol = 0; iCol < MIN_NCOLS; iCol++) {
            for (int iRow = nRows-1; iRow >= 0; iRow--) {
                this.pins[iCol][iRow] = new Pin(longi, lati, NODATA_VAL);
                lati += MIN_CELLSIZE;
            }
            longi += MIN_CELLSIZE;
            lati = DFLT_SWLAT;
        }
    }

    /**
    * Constructs a PinBoard object from a DTM file.
    * @param dtmPath the filepath to the DTM file.
    */
    public PinBoard(String dtmPath) {
        try {
            String[] info;
            String infoTag;
            float infoVal;
            FileReader frDTMReader = new FileReader(dtmPath);
            BufferedReader dtmReader = new BufferedReader(frDTMReader);
            String line = dtmReader.readLine();
            while(line != null) {
                info = line.split(" ");
                infoTag = info[0];
                infoVal = Float.parseFloat(info[1]);
                if      (Objects.equals(infoTag, "ncols")) this.nCols = (int)(infoVal);
                else if (Objects.equals(infoTag, "nrows")) this.nRows = (int)(infoVal);
                else if (Objects.equals(infoTag, "xllcorner")) this.swLong = infoVal;
                else if (Objects.equals(infoTag, "yllcorner")) this.swLat  = infoVal;
                else if (Objects.equals(infoTag, "cellsize")) this.cellSize = infoVal;
                else {
                    if (!Objects.equals(infoTag, "NODATA_value"))
                        throw new IOExcepton("Incomplete DTM file.");
                    else NODATA_VAL = infoVal;
                    break;
                }
                line = dtmReader.readLine();
            }
            float[][] els = ElsFromDTM(nCols, nRows, dtmReader);
            this.pins = PinsFromEls();
            this.nCols = nCols;
            this.nRows = nRows;
            this.swLong = swLong;
            this.swLat = swLat;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file: " + dtmPath);
        }
        catch(IOExcepton ex){
            ex.printStackTrace();
        }
    }

    private float[][] ElsFromDTM(int nCols, int nRows, BufferedReader dtmReader) {
        try {
            String line = dtmReader.readLine();
            String[] info = line.split(" ");
            float[][] els = float[nCols][nRows];
            int iCols = 0;
            int iRows = 0;
            while(line != null) {
                for (String landEl : line) {
                    els[iCols][iRows] = Float.parseFloat(info[iCols]);
                    iCols++;
                }
                line = dtmReader.readLine();
                info = line.split(" ");
                iCols = 0;
                iRows++;
            }
            return els;
        }
        catch(IOExcepton ex) {
            ex.printStackTrace();
        }
    }

    private Pins[][] PinsFromEls(int nCols, int nRows, int cellSize, float swLong, float swLat, float[][] landEls, float[][] wtEls) {
        longi = swLong;
        lati = swLat; 
        pins = new Pin[nCols][nRows];
        for (int iCol = 0; iCol < nCols; iCol++) {
            for (int iRow = nRows-1; iRow >= 0; iRow--) {
                if(!landEls)
                    this.pins[iCol][iRow] = new Pin(longi, lati, landEls[iCol][iRow]);
                else this.pins[iCol][iRow] = new Pin(longi, lati, DFLT_NODATA);
                lati += cellSize;
            }
            longi += cellSize;
            lati = swLat;
        }
    }

    /**
    * Constructs a PinBoard object from a set of user-specified variables.
    * @param nCols the number of collumns in the PinBoard.
    * @param nRows the number of rows in the PinBoard.
    * @param cellSize the definition of the pin in meters.
    * @param swLong the longitudinal coordinate of the SW corner of the board.
    * @param swLat the latitudinal coordinate of the SW corner of the board.
    * @param pins the (long, lat) pins in the PinBoard.
    */
    public PinBoard(int nCols, int nRows, int cellSize, float swLong, float swLat, Pin[][] pins) {
        // Assert preconditions.
        constructorAssert(nCols, nRows, cellSize, pins);
        // Initialize PinBoard with the provided arguments.
        this.nCols = nCols;
        this.nRows = nRows;
        this.swLong = swLong;
        this.swLat = swLat;
        this.cellSize = cellSize;
        this.pins = new Pin[nCols][nRows];
        for (int iCol = 0; iCol < nCols; iCol++) {
            for (int iRow = 0; iRow < nRows; iRow++) {
                this.pins[iCol][iRow] = pins[iCol][iRow];
            }
        }
    }

    /**
    * Constructs a new PinBoard object from another PinBoard object.
    * @param copyBoard the PinBoard to copy to a new instance.
    */
    public PinBoard(Pinboard copyBoard) {
        this.nRows = copyBoard.getNRows();
        this.nCols = copyBoard.getNCols();
        this.swLong = copyBoard.getSWLong();
        this.swLat = copyBoard.getSWLat();
        this.cellSize = copyBoard.getCellSize();
        this.pins = new Pin[nCols][nRows];
        for (int iCols = 0; iCols < this.nCols; iCols++) {
            for (int iRows = 0; iRows < this.nRows; iRows++) {
                pins[iCols][iRows] = copyBoard.pins[iCols][iRows];
            }
        }
    }

    /**
    * Asserts a set of conditions which the class should be constructed under.
    * @param nCols the number of collumns in the PinBoard.
    * @param nRows the number of rows in the PinBoard.
    * @param cellSize the definition of a pin in meters.
    * @param pins the (long, lat) pins in the PinBoard.
    * @throws IllegalArgumentException when argument constraints are not met.
    */
    private constructorAssert(int nCols, int nRows, int cellSize, Pin[] pins)
    throws IllegalArgumentException{
        if (nCols < MIN_NCOLS) {
            throw new IllegalArgumentException("Illegal number of collumns: " + nCols);
            this.nCols = MIN_NCOLS;
        }
        if (nRows < MIN_NROWS) {
            throw new IllegalArgumentException("Illegal number of rows: " + nRows);
            this.nRows = MIN_NROWS;
        }
        if (cellSize < MIN_CELLSIZE) {
            throw new IllegalArgumentException("Illegal pinsize definition: " + cellSize);
            this.cellSize = MIN_CELLSIZE;
        }
        if ((nRows * nCols) != (pins.length * pins[0].length)) {
            throw new IllegalArgumentException("Pinboard area does not match number of pins.\n" +
            "Area: " + (nRows * nCols) + "\n" +
            "Pins: " + (pins.length * pins[0].length));
        }
    }

    /**
    * Getter method for nCols.
    * @return the number of collumns in the PinBoard.
    */
    public getNCols() {
        return this.nCols;
    }

    /**
    * Getter method for nRows.
    * @return the number of rows in the PinBoard.
    */
    public getNRows() {
        return this.nRows;
    }

    /**
    * Getter method for swLong.
    * @return the SW, longitudinal coordinate of the PinBoard.
    */
    public getSWLong() {
        return this.swLong;
    }

    /**
    * Getter method for swLat.
    * @return the SW, latitudinal coordinate of the PinBoard.
    */
    public getSWLat() {
        return this.swLat;
    }

    /**
    * Getter method for cellSize.
    * @return the cellSize of a pin in the pinboard (area in meters).
    */
    public getCellSize() {
        return this.cellSize;
    }

    /**
    * Getter method for pins.
    * @return the pins in the pinboard.
    */
    public getPins() {
        return this.pins;
    }
}
