package main;

import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.electro2560.dev.cluescrolls.api.ClueScrollsAPI;

public final class ClueScrollManager extends JavaPlugin implements Listener {

    private Logger logger;
    private ClueScrollsAPI scrollAPI;

    public ClueScrollManager() {
        this.logger = Logger.getLogger("ClueScrollManager");
        saveDefaultConfig();
    }

    public final String getPluginInfos(boolean toConsole) {
        final StringBuilder sb = new StringBuilder();
        final String devName = toConsole
                ? "@Izo"
                : "<@272924120142970892>";

        sb.append(String.format(this.getName() + " - v1.1.0\n"));
        sb.append(String.format(devName + "\n"));

        return sb.toString();
    }

    public String getFiglet() {
        return """
            \n
 ______  _                _____                _ _           ___  ___
/  __ \\| |              /  ___|              | | |          |  \\/  |
| /  \\/| |    _   _  ___\\ `--.  ___ _ __ ___ | | |  ______  | .  . | __ _ _ __   __ _  __ _  ___ _ __
| |    | |   | | | |/ _ \\`--. \\/ __| '__/ _ \\| | | |______| | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|
| \\__/\\| |___| |_| |  __/\\__/ / (__| | | (_) | | |          | |  | | (_| | | | | (_| | (_| |  __/ |
\\_____/\\_____/\\__,_|\\___\\____/ \\___|_|  \\___/|_|_|          \\_|  |_/\\__,_|_| |_|\\__,_|\\__, |\\___|_|
                                                                                        __/ |
                                                                                        |___/
                """
                + "\n" + this.getPluginInfos(true);
    }

    @Override
    public final void onEnable() {
        try {
            new EconomyManager(this);
            logger.info("LOADED: EconomyManager *vaultApi");

            this.scrollAPI = ClueScrollsAPI.getInstance();
            logger.info("LOADED: ClueScrollsAPI instance");

            BukkitManager.registerEvents(this);
            BukkitManager.registerCommands(this);

            logger.info(this.getFiglet());
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    public ClueScrollsAPI getScrollAPI() {
        return scrollAPI;
    }

    @Override
    public final void onDisable() {
        logger.info("Goodbye world !!!");
    }
}
