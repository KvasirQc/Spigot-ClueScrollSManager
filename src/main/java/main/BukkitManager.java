package main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import main.commands.bukkit.ScrollExchangeCmd;
import main.commands.bukkit.ScrollUpgradeCmd;

public class BukkitManager {
    private static Logger getLogger() {
        return Logger.getLogger("ClueScrollManager:" + BukkitManager.class.getSimpleName() + " </>:");
    }

    public static Server getServer() {
        return Bukkit.getServer();
    }

    public static void registerEvents(ClueScrollManager plugin) {
        try {
            // Add you events here..
            // Bukkit.getPluginManager().registerEvents(new OnServerLoad(plugin), plugin);
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
        }
    }

    public static void registerCommands(ClueScrollManager plugin) {
        try {
            plugin.getCommand("scroll-upgrade").setExecutor(new ScrollUpgradeCmd(plugin, "scroll-upgrade"));
            plugin.getCommand("scroll-exchange").setExecutor(new ScrollExchangeCmd(plugin, "scroll-exchange"));
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
        }
    }
}