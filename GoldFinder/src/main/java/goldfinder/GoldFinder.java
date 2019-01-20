package goldfinder;

import java.util.Scanner;

public class GoldFinder {
    public static void main(String[] args) {
        System.out.println(args[1]);
        DEMParser.parseFile(args[1]);
    }
}
