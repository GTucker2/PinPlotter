package goldfinder;

import java.util.Scanner;
import goldfinder.demset.*;
import java.lang.IllegalArgumentException;
import java.io.FileNotFoundException;
import java.io.File;

public class GoldFinder {
    public static void main(String[] args) {
        System.out.println(args[0]);
        File file = new File(args[0]);
        DEMSet demset = new DEMSet(file);
    }
}
