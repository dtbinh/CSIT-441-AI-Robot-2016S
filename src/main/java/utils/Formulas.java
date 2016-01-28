package utils;

public class Formulas {

    public static double distance(int x1, int x2, int y1, int y2) {
        return Math.sqrt(((x2 - x1) ^ 2) + ((y2 - y1) ^ 2));
    }
}
