package de.veenixdev.logger.commands;

import de.veenixdev.logger.PrimeLogger;
import org.bukkit.command.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class QueryCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length >= 2) {
            if(PrimeLogger.getInstance().getFileManager().logExists(args[0])) {
                Thread thread = new Thread(() -> {

                    StringBuilder sb = new StringBuilder();

                    for(int i = 2; i < args.length; i++) {
                        sb.append(args[i]).append(" ");
                    }

                    PrimeLogger.getInstance().getFileManager().queryLogs(sender, args[0], sb.toString(), args[1].equals("true"));
                });

                thread.start();
            } else {
                sender.sendMessage("Log doesn't exist");
            }
        } else return false;

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> returns = new ArrayList<>();

        switch (args.length) {
            case 1:
                for(File f : Objects.requireNonNull(PrimeLogger.getInstance().getDataFolder().listFiles())) {
                    if(!f.isDirectory()) {
                        String fName = f.getName().replace(".log", "");
                        returns.add(fName);
                    }
                }

                returns.remove("config.yml");
                return returns;
            case 2:
                returns.addAll(Arrays.asList("true", "false"));
                return returns;
            default:
                return returns;
        }
    }
}
