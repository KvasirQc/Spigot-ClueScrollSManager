package main.commands.bukkit;

import main.ClueScrollManager;

public class ScrollExchangeCmd extends PlayerBaseCmd {

  public ScrollExchangeCmd(ClueScrollManager plugin, String cmdName) {
    super(plugin, cmdName);
  }

  @Override
  public void execute() {
    this.logger.info("ScrollUExchangeCmd");
  }
}
