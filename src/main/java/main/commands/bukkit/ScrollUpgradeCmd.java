package main.commands.bukkit;

import org.bukkit.inventory.ItemStack;

import com.electro2560.dev.cluescrolls.api.ClueScrollsAPI;

import main.ClueScrollManager;

public class ScrollUpgradeCmd extends PlayerBaseCmd {

  public ScrollUpgradeCmd(ClueScrollManager plugin, String cmdName) {
    super(plugin, cmdName);
  }

  private String getScrollTierNextScroll(String tierName) {
    if (tierName == null) {
      return null;
    }

    switch (tierName.toLowerCase()) {
      case "common":
        return "special";

      case "special":
        return "legendary";

      default:
        return null;
    }
  }

  private int getScrollTierMinimumRequired(String tierName) {
    if (tierName == null) {
      return 0;
    }

    switch (tierName.toLowerCase()) {
      case "common":
        return 9;

      case "special":
        return 9;

      default:
        return 0;
    }
  }

  @Override
  public void execute() {
    try {
      final ClueScrollsAPI api = plugin.getScrollAPI();

      if (args == null || args[0] == null) {
        final String msg = "Unable to establish a scroll upgrade: Invalid arguments tier is required.";

        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      final String sourceTier = args[0].toString().toLowerCase();
      int sourceAmountMax = Integer.parseInt(args.length > 1 ? args[1] : "64");

      if (!api.getTiers().contains(sourceTier)) {
        final String msg = String.format(
            "Unable to establish a scroll upgrade: Tier doesn't exists --> [Tier: %1$s]", sourceTier);

        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      final String nextTier = getScrollTierNextScroll(sourceTier);
      final int batchDivider = getScrollTierMinimumRequired(sourceTier);

      if (batchDivider == 0) {
        final String msg = String.format("Unable to establish a scroll upgrade: There's no betters scroll than %1$s.",
            sourceTier);
        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      if (sourceAmountMax < batchDivider) {
        final String msg = String.format(
            "Unable to establish a scroll upgrade: Trade asked amount is invalid:\n --- [asked:%1$s, required:%2$s]",
            sourceAmountMax, batchDivider);
        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      ItemStack[] playerItems = player.getInventory().getContents();

      int findCount = 0;
      for (ItemStack itemStack : playerItems) {
        if (itemStack == null || itemStack.getAmount() != 1) {
          continue;
        }

        final String itemTier = api.getScrollTier(itemStack);
        if (itemTier == null || !itemTier.equals(sourceTier)) {
          continue;
        }

        findCount++;
      }

      sourceAmountMax = Integer.min(findCount, sourceAmountMax);

      if (findCount < batchDivider) {
        final String msg = String.format(
            "Not enough %1$s scrolls where found in your inventory, minimum needed is: %2$s, you have %3$s.",
            sourceTier,
            batchDivider, findCount);
        player.sendMessage(msg);
        logger.info(msg);
        return;
      }

      int givenCount = 0;
      int removedCount = 0;
      final int scrollToGive = sourceAmountMax / batchDivider;

      playerItems = player.getInventory().getContents();
      for (int i = 0; i < playerItems.length; i++) {
        ItemStack itemStack = playerItems[i];

        if (givenCount == scrollToGive) {
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
        removedCount++;

        if (removedCount > 0 && removedCount % batchDivider == 0) {
          itemStack = api.getScroll(nextTier, player);
          player.getInventory().addItem(itemStack);
          givenCount++;
        }
      }

      final String msg = String.format("Player %1$s upgraded:\n  --- [ %2$sx %3$s scrolls <-> %4$sx %5$s scrolls ]",
          player.getName(), givenCount * batchDivider, sourceTier, givenCount, nextTier);

      player.sendMessage(msg);
      logger.info(msg);

    } catch (Exception e) {
      final String msg = String.format("Unknown error ocurred during the scroll upgrade with %1$s:", player.getName());

      player.sendMessage(msg);
      logger.severe(msg);
      logger.severe(e.getMessage());
      e.printStackTrace();
    }
  }

}
