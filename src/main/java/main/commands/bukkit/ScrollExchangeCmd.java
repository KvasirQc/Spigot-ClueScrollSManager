package main.commands.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
      final String tier = args[0];
      final int amount = Integer.parseInt(args[1]);

      var tiers = api.getTiers();
      if (!tiers.contains(tier) || amount < 1) {
        logger
            .info(String.format("Unable to make scroll exchange. [Invalid amount or tier... Tier: %1$s | Amount: %2$s]",
                tier, amount));
        return;
      }

      int findCount = 0;
      var playerItems = player.getInventory().getContents();

      for (ItemStack itemStack : playerItems) {
        if (itemStack.getAmount() == 1 && api.getScrollTier(itemStack).equals(tier)) {
          itemStack.setAmount(0);
          findCount++;
        }
      }

      EconomyManager.depositPlayer(player, 1 * findCount);

    } catch (Exception e) {
      logger.warning("Unknown error ocurred during scroll exchange.");
    }
  }

}
