package de.veenixdev.logger.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    private FileUtils() {}

    public static void writeInFile(File file, boolean append, String... text) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            for(String s : text) {
                writer.write(s + "\n");
            }
            writer.flush();
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
