package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by michael on 4/12/16.
 */
public class PathRecorder {
    File file = new File("/home/lejos/path.txt");
    private static BufferedWriter bufferedWriter;

    public PathRecorder() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());

            bufferedWriter = new BufferedWriter(fw);

        } catch (IOException e) {
            System.out.println("NO");
        }
    }

    public static void writeDirection(String direction) {
        try {
            bufferedWriter.write(direction + "\n");
        }catch (IOException e) {
            System.out.printf("NO");
        }
    }

    public static void closeReader() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Seriously?");
        }
    }
}
