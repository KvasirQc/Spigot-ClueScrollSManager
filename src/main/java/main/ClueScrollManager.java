package main;

import java.net.http.WebSocket.Listener;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public final class ClueScrollManager extends JavaPlugin implements Listener {

    private Logger logger;

    public ClueScrollManager() {
        this.logger = Logger.getLogger("ClueScrollManager");
        saveDefaultConfig();
    }

    @Override
    public final void onEnable() {
        logger.info("Hello world !!!");
    }

    @Override
    public final void onDisable() {
        logger.info("Goodbye world !!!");
    }
}
