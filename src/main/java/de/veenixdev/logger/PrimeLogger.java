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

    /* Config logs path
     *
     * 0: Command
     * 1: Chat
     * 2: Block Place
     * 3: Block Break
     * 4: JoinQuit
     */
    @Getter
    private final String[] logsPath = new String[] {"logs.command", "logs.chat", "logs.blockPlace", "logs.blockBreak", "logs.joinQuit"};

    /* Should files get deleted if they aren't used */
    @Getter
    private boolean deleteUnusedFiles;

    /* Does things when plugin gets loaded by the Server */
    @Override
    public void onLoad() {
        synchronized (this) {
            instance = this;
        }
        if(getConfig().getBoolean(logsPath[0]))
            fileManager.newLog("command");

        if(getConfig().getBoolean(logsPath[1]))
            fileManager.newLog("chat");

        if(getConfig().getBoolean(logsPath[2]))
            fileManager.newLog("blockPlace");

        if(getConfig().getBoolean(logsPath[3]))
            fileManager.newLog("blockBreak");

        if(getConfig().getBoolean(logsPath[4]))
            fileManager.newLog("joinQuit");

        loadConfig();
        fileManager.createQueryDeleteFile();
        if(deleteUnusedFiles)
            fileManager.deleteUnusedFiles();
    }

    /* Does things when plugin gets enabled by the Server */
    @Override
    public void onEnable() {
        //Registers all EventListeners if the log is enabled!
        if(getConfig().getBoolean(logsPath[0]))
            Bukkit.getPluginManager().registerEvents(new CommandListener(), this);

        if(getConfig().getBoolean(logsPath[1]))
            Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

        if(getConfig().getBoolean(logsPath[2]) || getConfig().getBoolean(logsPath[3]))
            Bukkit.getPluginManager().registerEvents(new BlockListener(), this);

        if(getConfig().getBoolean(logsPath[4]))
            Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);

        getCommand("query").setExecutor(new QueryCommand());

        Bukkit.getConsoleSender().sendMessage("§9Plugin started!");
    }

    /* Does things when plugin gets disabled by the Server */
    @Override
    public void onDisable() {
        reloadConfig();
        saveConfig();
    }

    private void loadConfig() {
        /* Load Config.yml */
        getConfig().addDefault(logsPath[0], true);
        getConfig().addDefault(logsPath[1], true);
        getConfig().addDefault(logsPath[2], true);
        getConfig().addDefault(logsPath[3], true);
        getConfig().addDefault(logsPath[4], true);

        getConfig().addDefault("deleteUnusedFiles", false);

        getConfig().options().copyDefaults(true);
        saveConfig();

        /* Save values */
        this.deleteUnusedFiles = getConfig().getBoolean("deleteUnusedFiles");
    }
}
