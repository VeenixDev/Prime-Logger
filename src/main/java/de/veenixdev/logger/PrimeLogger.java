package de.veenixdev.logger;

import de.veenixdev.logger.commands.QueryCommand;
import de.veenixdev.logger.files.FileManager;
import de.veenixdev.logger.listeners.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PrimeLogger extends JavaPlugin {

    /* Log files */
    @Getter
    private final FileManager fileManager = FileManager.getInstance();

    /* Static Instance from the Plugin Main */
    @Getter
    private static PrimeLogger instance;

    /* Does things when plugin gets loaded by the Server */
    @Override
    public void onLoad() {
        synchronized (this) {
            instance = this;
        }
        fileManager.newLog("chat");
        fileManager.newLog("command");
        fileManager.newLog("blockPlace");
        fileManager.newLog("blockBreak");
        fileManager.newLog("joinQuit");

        fileManager.createQueryDeleteFile();
    }

    /* Does things when plugin gets enabled by the Server */
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);

        getCommand("query").setExecutor(new QueryCommand());

        Bukkit.getConsoleSender().sendMessage("§9Plugin started!");
    }
}
