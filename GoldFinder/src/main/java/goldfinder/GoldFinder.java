package goldfinder;

import java.util.Scanner;
import goldfinder.demset.*;
import java.lang.IllegalArgumentException;

public class GoldFinder {
    public static void main(String[] args) {
        try {
            ASCParser.parseFile(args[0]);
        } catch (IllegalArgumentException e) {
            System.out.println("No filepath provided. Execution terminated.");
        }
    }
}
