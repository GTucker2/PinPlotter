public enum SupportedExtension {

    ASCII_GRID ("asc");

    private final String extension;
    
    /**
     * Constructs an extension enum value.
     * @param extension the string value of the extension to enumerate.
     */
    SupportedExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Retrieves the String value of the Enum.
     * @return the String extension.
     */
    String getExtension() {
        return extension;
    }

    /**
     * Checks if given file extension is supported by the Enum.
     * @param extension the file extension to search for support.
     * @return true if file extension supported by Enum, otherwise false.
     */
    static Boolean supports(String extension) {
        for (SupportedExtension ext : SupportedExtension.values())
        {
            if (ext.getExtension().equals(extension)) return true;
        }
        return false;
    }
}