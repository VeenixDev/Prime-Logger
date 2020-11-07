package de.veenixdev.logger.listeners;

import de.veenixdev.logger.PrimeLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        PrimeLogger.getInstance().getFileManager().getLog("chat").write(event.getPlayer().getName() + ": " + event.getMessage());
    }
}
