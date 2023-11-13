package commands.bukkit;

import main.ClueScrollManager;

public class ScrollUpgradeCmd extends PlayerBaseCmd {

  private final ConfigManager configs;

  public ScrollUpgradeCmd(ClueScrollManager plugin, String cmdName) {
    super(plugin, cmdName);
  }

  @Override
  public void execute() {
    this.logger.info("ScrollUpgradeCmd");
  }
}
