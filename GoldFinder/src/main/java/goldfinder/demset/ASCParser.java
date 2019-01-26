package goldfinder.demset;

public class ASCParser extends DEMParser {

    public DEMMap parseFile(String filePath) {
        
        // Attempt to retrieve the file specified by filePath.
        try { 
            File file = retrieveFile(filePath); 
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found; enter a valid filepath."); 
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid filepath; please enter "); 
        } 

        // Read the file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != Null) {
                switch () {
                    case "ncols":
                    case "nrows":
                    case "xllcorner":
                    case "yllcorner":
                    case "cellsize":
                    case "NODATA_value":
                    default:
                }
            }
        }
        catch (IllegalArgumentException) 
    }
}