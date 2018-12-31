package pinplotter;
class Pin():
    """ The pin class is defined by the locational data of a point
    on the Earth.
    """

    def __init__(self, long = 0.0, lat = 0.0, lEl = 0.0, wtEl = 0.0):
        """ Initialize a pin with relevant positional data, including
        that of the water level respective to it.

        Keyword arguments:
        long -- the longitude of the pin
        lat -- the latitude of the pin
        lEl -- the elevation of the land
        wtEl -- the elevation of the water table
        """
        # Set (long,lat,el) coordinates.
        self.long = long
        self.lat = lat
        self.landEl = lEl
        self.waterTableEl = wtEl

        # Set submersion data.
        # Submersion Depth is 0 if not submerged.
        self.isSubmerged = (lEl < wtEl)
        self.submersionDepth = (wtEl - lEl)
        if self.submersionDepth <= 0:
            self.submersionDepth = 0.0

    def __str__(self):
        """
        """
