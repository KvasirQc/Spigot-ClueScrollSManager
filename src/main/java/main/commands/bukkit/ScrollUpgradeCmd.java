package main.commands.bukkit;

import main.ClueScrollManager;

public class ScrollUpgradeCmd extends PlayerBaseCmd {

  public ScrollUpgradeCmd(ClueScrollManager plugin, String cmdName) {
    super(plugin, cmdName);
  }

  @Override
  public void execute() {
    this.logger.info("ScrollUpgradeCmd");
  }
}
