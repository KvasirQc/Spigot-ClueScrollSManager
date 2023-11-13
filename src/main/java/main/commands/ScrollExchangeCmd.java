package commands.bukkit;

import main.ClueScrollManager;

public class ScrollExchangeCmd extends PlayerBaseCmd {

  private final ConfigManager configs;

  public ScrollExchangeCmd(ClueScrollManager plugin, String cmdName) {
    super(plugin, cmdName);
  }

  @Override
  public void execute() {
    this.logger.info("ScrollUExchangeCmd");
  }
}
