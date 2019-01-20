package goldfinder.demset;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException;

public class DEMParser {

    private DEMParser () {}

    public static void parseFile(String filePath) {
        try {
            File demFile = retrieveFile(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IllegalArgumentException e) {
            System.out.println("Unsupported file!");
        }
    }

    /**
     * Retrieves a file from a given filePath if valid.
     * @param filePath the path to the file which to return.
     * @return the retrieved file from the file path. 
     */
    private static File retrieveFile (String filePath) 
    throws FileNotFoundException, IllegalArgumentException {

        // Check parameter validity; fail if filePath is empty.
        if(filePath.length() == 0 || filePath == null)
            throw new IllegalArgumentException("Invalid file path; file path is empty.");

        // Create file object and check validity.
        File f = new File(filePath);
        if(!f.exists() || f.isDirectory())
            throw new FileNotFoundException("Invalid file path; file does not exist.");

        // Retrieve file extension and check validity. 
        String filePathLC = filePath.toLowerCase();
        String[] directoriesAndName = filePathLC.split("\\");
        String[] nameAndExtention = directoriesAndName[directoriesAndName.length-1].split(".");
        String extension = nameAndExtention[nameAndExtention.length-1];
        if (!SupportedExtension.supports(extension)) 
            throw new IllegalArgumentException("Invalid file extension.");

        // Return the retrieved file.
        return f;
    }

    private enum SupportedExtension {

        ASC (".asc");
    
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
                if (ext.getExtension().equals(extension)) return true;
            return false;
     
        }
    }
}