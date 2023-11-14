package main;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class EconomyManager {
    private static Server server;
    private static Economy vaultEconomy;
    private static Logger logger;

    public EconomyManager(ClueScrollManager plugin) {
        logger = Logger.getLogger("ClueScrollManager:" + this.getClass().getSimpleName());

        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }

        EconomyManager.server = plugin.getServer();
        RegisteredServiceProvider<Economy> vault = server.getServicesManager().getRegistration(Economy.class);
        vaultEconomy = vault.getProvider();
    }

    public static EconomyResponse depositPlayer(Player player, double amount) {
        logger.info(
                String.format("Vault Player Currency --> Balance: %1$s$ | Depositing: %2$s$ | Player: %3$s",
                        getPlayerBalance(player), amount, player.getName()));
        return vaultEconomy.depositPlayer(player, amount);
    }

    public static EconomyResponse withdrawPlayer(Player player, double amount) {
        logger.info(
                String.format("Vault Player Currency --> Balance: %1$s$ | Withdrawing: %2$s$ | Player: %3$s",
                        amount, player.getName()));
        return vaultEconomy.withdrawPlayer(player, amount);
    }

    public static double getPlayerBalance(Player player) {
        final double balance = vaultEconomy.getBalance(player);
        logger.info(String.format("Vault Player Currency --> Balance: %1$s$ | Player: %2$s", balance, player.getName()));
        return balance;
    }

}
