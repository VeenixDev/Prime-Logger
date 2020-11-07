package de.veenixdev.logger.listeners;

import de.veenixdev.logger.PrimeLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        event.getBlockPlaced();
        String txt = "{type:\"" + event.getBlock().getType().toString() + "\", x:" + event.getBlock().getX() +
                        ", y:" + event.getBlock().getY() + ", z:" + event.getBlock().getZ() + ", cancelled:" + event.isCancelled() + "}";

        PrimeLogger.getInstance().getFileManager().getLog("blockPlace").write(event.getPlayer().getName() +
                ": " + txt);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        String txt = "{type:\"" + event.getBlock().getType().toString() + "\", x:" + event.getBlock().getX() +
                ", y:" + event.getBlock().getY() + ", z:" + event.getBlock().getZ() + ", cancelled:" + event.isCancelled() + "}";

        PrimeLogger.getInstance().getFileManager().getLog("blockBreak").write(event.getPlayer().getName() +
                ": " + txt);
    }
}
