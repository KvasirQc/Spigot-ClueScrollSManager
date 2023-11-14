package main.commands.bukkit;

import org.bukkit.Material;

import com.electro2560.dev.cluescrolls.api.ClueScrollsAPI;

import main.ClueScrollManager;
import main.EconomyManager;

public class ScrollExchangeCmd extends PlayerBaseCmd {

  public ScrollExchangeCmd(ClueScrollManager plugin, String cmdName) {
    super(plugin, cmdName);
  }

  @Override
  public void execute() {
    try {
      final ClueScrollsAPI api = plugin.getScrollAPI();
      final int tier = Integer.parseInt(args[0]);
      final int count = Integer.parseInt(args[1]);

      if(true) {
        logger.info(String.format("Unable to make scroll exchange. [Not enough papers... Tier: %1$s | Amount: %2$s]", tier, count));
        return;
      }
      
    } catch (Exception e) {
      logger.warning("Unable to make scroll exchange.");
    }

    EconomyManager.depositPlayer(player, 15);
  }

}
