package main.commands.bukkit;

import org.bukkit.inventory.ItemStack;

import com.electro2560.dev.cluescrolls.api.ClueScrollsAPI;

import main.ClueScrollManager;
import main.EconomyManager;

public class ScrollExchangeCmd extends PlayerBaseCmd {
  public ScrollExchangeCmd(ClueScrollManager plugin, String cmdName) {
    super(plugin, cmdName);
  }

  private int getScrollTierValue(String tierName) {
    switch (tierName.toLowerCase()) {
      case "common":
        return 25;

      case "special":
        return 200;

      case "legendary":
        return 1000;

      default:
        return 1;
    }
  }

  @Override
  public void execute() {
    try {
      final ClueScrollsAPI api = plugin.getScrollAPI();

      if (args == null || args[0] == null) {
        final String msg = "Unable to establish a scroll exchange: Invalid arguments tier is required.";

        player.sendMessage(msg);
        logger.info(msg);
        return;
      }
      
      final String sourceTier = args[0].toString().toLowerCase();
      final int sourceMaxAmount = Integer.parseInt(args.length > 1 ? args[1] : "64");
      final int scrollValue = getScrollTierValue(sourceTier);

      if (scrollValue <= 0) {
        final String msg = "Unable to establish a scroll exchange: Invalid arguments the scroll value is negative.";
        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      if (!api.getTiers().contains(sourceTier) || sourceMaxAmount < 1) {
        final String msg = String.format(
            "Unable to establish a scroll exchange: Invalid amount or tier --> [Tier: %1$s | Amount: %2$s]",
            sourceTier, sourceMaxAmount);

        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      int exchangeCount = 0;
      final ItemStack[] playerItems = player.getInventory().getContents();
      for (ItemStack itemStack : playerItems) {
        if ((exchangeCount == sourceMaxAmount)) {
          break;
        }

        if (itemStack == null || itemStack.getAmount() != 1) {
          continue;
        }

        final String itemTier = api.getScrollTier(itemStack);
        if (itemTier == null || !itemTier.equals(sourceTier)) {
          continue;
        }

        itemStack.setAmount(0);
        exchangeCount++;
      }

      if (exchangeCount <= 0) {
        final String msg = String.format("No %1$s scrolls where found in your inventory...", sourceTier);
        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      final int depositValue = scrollValue * exchangeCount;
      EconomyManager.depositPlayer(player, depositValue);

      final String msg = String.format("Player %1$s exchanged %2$s %3$s scrolls at %4$s$ each, for a total of: %5$s$", player.getName(),
          exchangeCount, sourceTier, scrollValue, depositValue);

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
