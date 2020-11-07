package de.veenixdev.logger.files;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Log {

    @Getter @Setter
    private File logFile;

    @Getter
    private boolean readOnly;

    @Getter
    private boolean fileCreated;

    public Log(File file) {
        this.logFile = file;
        try{
            if(!this.logFile.exists()){
                fileCreated = this.logFile.getParentFile().mkdirs();
                fileCreated = this.logFile.createNewFile();
                readOnly= this.logFile.setReadOnly();
            }
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void write(String txt) {
        try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(this.logFile, true))) {
            Date date = new Date(System.currentTimeMillis());
            DateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
            fileWriter.write("[" + format.format(date) + "] " + txt + System.lineSeparator());
            fileWriter.flush();
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public List<String> read() {
        List<String> result = new ArrayList<>();
        try(Scanner fileReader = new Scanner(this.logFile)) {

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> readOnlyMessage() {
        List<String> read = read();
        List<String> result = new ArrayList<>();

        for(String s : read) {
            String[] arr = s.split("]", 2);
            arr = arr[1].split(":", 2);

            result.add(arr[1]);
        }

        return result;
    }
}
