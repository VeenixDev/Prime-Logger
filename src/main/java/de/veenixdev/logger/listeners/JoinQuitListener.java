package de.veenixdev.logger.listeners;

import de.veenixdev.logger.PrimeLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        PrimeLogger.getInstance().getFileManager().getLog("joinQuit").write(event.getPlayer().getName() + ":[Join]:" + (event.getPlayer().getAddress() == null ? "n/a" : event.getPlayer().getAddress().toString()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        PrimeLogger.getInstance().getFileManager().getLog("joinQuit").write(event.getPlayer().getName() + ":[Quit]:" + (event.getPlayer().getAddress() == null ? "n/a" : event.getPlayer().getAddress().toString()));
    }
}
