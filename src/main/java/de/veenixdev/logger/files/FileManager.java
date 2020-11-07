package de.veenixdev.logger.files;

import de.veenixdev.logger.PrimeLogger;
import de.veenixdev.logger.misc.FileUtils;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class FileManager {

    @Getter
    private final Map<String, Log> logs = new HashMap<>();

    private static FileManager instance;

    private FileManager() {
    }

    public static FileManager getInstance() {
        if (instance == null)
            instance = new FileManager();
        return instance;
    }

    public void newLog(String name) {
        logs.put(name.toLowerCase(), new Log(new File(PrimeLogger.getInstance().getDataFolder(), name + ".log")));
    }

    public boolean logExists(String name) {
        return logs.containsKey(name.toLowerCase());
    }

    public Log getLog(String name) {
        return logs.get(name.toLowerCase());
    }

    public void createQueryDeleteFile() {
        try{
            File file = new File(PrimeLogger.getInstance().getDataFolder() + "/query/", "deleteQuery.sh");

            if(!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                file.setReadable(false);
                file.setWritable(false);
                file.setExecutable(true);
                FileUtils.writeInFile(file, true, "sudo rm -f *.query");
            }
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void queryLogs(CommandSender sender, String log, String search, boolean exact) {
        Map<Integer, String> result = new HashMap<>();
        log = log.toLowerCase();
        boolean removeDate = false;

        if (logs.containsKey(log)) {
            if(log.equalsIgnoreCase("joinQuit")) {
                removeDate = true;
            }
            List<String> array = removeDate ? logs.get(log).read() : logs.get(log).readOnlyMessage();
            int counter = 1;

            while(search.endsWith(" ")) {
                char[] c = search.toCharArray();

                StringBuilder sb = new StringBuilder();

                for(int i = 0; i < c.length - 1; i++) {
                    sb.append(c[i]);
                }

                search = sb.toString();
            }

            if(removeDate) {
                List<String> temp = new ArrayList<>();
                for (String s : array) {
                    String[] arr = s.split("] ", 2);
                    temp.add(arr[1]);
                }
                array = temp;
            }

            search = search.replace("{", "").replace("}", "");

            Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
            for (String s : array) {
                if (exact) {
                    if (s.equalsIgnoreCase(search)) {
                        result.put(counter, s);
                    }
                } else {
                    if (pattern.matcher(s).find()) {
                        result.put(counter, s);
                    }
                }
                counter++;
            }
        } else {
          sender.sendMessage("§9Couldn't find " + log + "!");
          return;
        }

        sender.sendMessage("§9Query ended please view '§b" + createQueryFile(UUID.randomUUID().toString(), result) + "§9' for the result!");
    }

    private String createQueryFile(String id, Map<Integer, String> content) {
        File file = new File(PrimeLogger.getInstance().getDataFolder() + "/query/", "query_" + id + ".query");
        try{
            if(!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                file.setReadOnly();
            }

            try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                StringBuilder sb = new StringBuilder();
                Integer[] intArr = content.keySet().toArray(new Integer[0]);

                Arrays.sort(intArr);

                for(Integer i : intArr) {
                    sb.append("line: ").append(i).append(", content: ").append(content.get(i)).append("\n");
                }

                writer.write(sb.toString());
                writer.flush();
            }
        }catch (IOException exception) {
            exception.printStackTrace();
        }

        return file.getName();
    }
}
