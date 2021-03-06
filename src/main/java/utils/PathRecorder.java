package utils;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by michael on 4/12/16.
 */
public class PathRecorder {
    private File file = new File("/home/lejos/path.txt");
    private BufferedWriter bufferedWriter;

    public PathRecorder() {

    }

    public void initFile() {
        // if file doesnt exists, then create it
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bufferedWriter = new BufferedWriter(fw);
        } catch (IOException e) {
            System.out.printf("Meh");
        }
    }

    public void writeDirection(String direction) {
        try {
            String content = direction + "\n";
            bufferedWriter.flush();
            bufferedWriter.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readDirectionFile() {
        ArrayList<String> list = new ArrayList<>();
        String line;
        System.out.println("I am here");
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
            line = reader.readLine();
            System.out.println(line);
            list.add(line);
        } catch (IOException e) {
            System.out.printf("No");
        }
        return list;
    }

    public void closeWriter() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Seriously?");
        }
    }
}
