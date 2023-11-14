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
      final int scrollValue = 5;

      if (args == null || args[0] == null) {
        final String msg = "Unable to make scroll exchange: Invalid arguments tier is required.";

        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      if (scrollValue <= 0) {
        final String msg = "Unable to make scroll exchange: Invalid arguments the sroll value is negative.";
        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      final String askedTier = args[0].toString().toLowerCase();
      final int askedAmount = Integer.parseInt(args.length > 1 ? args[1] : "256");

      if (!api.getTiers().contains(askedTier) || askedAmount < 1) {
        final String msg = String.format(
            "Unable to make scroll exchange: Invalid amount or tier --> [Tier: %1$s | Amount: %2$s]",
            askedTier, askedAmount);

        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      int exchangeCount = 0;
      final ItemStack[] playerItems = player.getInventory().getContents();
      for (ItemStack itemStack : playerItems) {
        if ((exchangeCount == askedAmount)) {
          break;
        }

        if (itemStack == null || itemStack.getAmount() != 1) {
          continue;
        }

        final String itemTier = api.getScrollTier(itemStack);
        if (itemTier == null || !itemTier.equals(askedTier)) {
          continue;
        }

        itemStack.setAmount(0);
        exchangeCount++;
      }

      if (exchangeCount <= 0) {
        final String msg = String.format("No %1$s scroll was found in your inventory...");
        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      final int depositValue = scrollValue * exchangeCount;
      EconomyManager.depositPlayer(player, depositValue);

      final String msg = String.format("Player %1$s exchanged %2$s %3$s scrolls at %4$s$ each.", player.getName(),
          exchangeCount, askedTier, scrollValue);

      player.sendMessage(msg);
      logger.info(msg);

    } catch (Exception e) {
      final String msg = String.format("Unknown error ocurred during the scroll exchange with %1$s:", player.getName());

      player.sendMessage(msg);
      logger.severe(msg);
      logger.severe(e.getMessage());
      e.printStackTrace();
    }
  }

}
