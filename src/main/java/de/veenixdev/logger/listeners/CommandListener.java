package de.veenixdev.logger.listeners;

import de.veenixdev.logger.PrimeLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        PrimeLogger.getInstance().getFileManager().getLog("command").write(event.getPlayer().getName() + ": " + event.getMessage());
    }

}
