import Pin
class PinBoard():
    """
    """
    def __init__(self, dtmPath=None, nCols=0, nRows=0, swLong = 0.0,
    swLat = 0.0, pins = []):
        """
        """
        # Enforce class assertions.
        assert(nCols, nRows, pins)

        # If we are not converting from a DTM, init with arguments.
        if (dtmPath == None):
            self.nCols = nCols
            self.nRows = nRows
            self.swLong = swLong
            self.swLat = swLat
            self.pins = pins

        #
        else:
            self = dtmToPinboard()

    def assert(nCols, nRows, pins):
        """
        """
        assert nCols >= 0, "Negative collumsn; No negative PinBoard dimensions."
        assert nRows >= 0, "Negative rows; No negative PinBoard dimensions."
        assert isinstance(pins, list), "Must provide constructor with list of" +
        " pins."
        if (nCols == 0 || nRows == 0):
            assert (nCols == 0 && nRows == 0), "One pinboard dimension cannot" +
            " be zero while the other is not."
            assert (len(pins) == 0), "Pin array dimensions must correspond to" +
            " provided dimensions."
        else:
            assert isinstance(pins, list), "Non-empty pin array must be a list."
            assert isinstance(pins[0], list), "Non-empty pin array must be a" +
            " list of lists."
            assert isinstance(pins[0][0], int), "Non-empty pin array must not" +
            " have a third dimension."
            assert ((len(pins)*len(pins[0])) == (nCols*nRows)), "Non-empty" +
            " pin array must have dimenstion corresponding to those provided."

    def dtmToPinboard(dtmPath):
        """
        """
        # Open the DTM file, or ask the user to clarify the file path.
        dtm = None
        while True:
            try:
                dtm = open(dtmPath)
                break
            except:
                pass
            dtmPath = raw_input("DTM not found. Enter a valid path: ")

        #
        PinBoard

        # Close the DTM and return the new PinBoard
        dtm.close()
        return PinBoard()
