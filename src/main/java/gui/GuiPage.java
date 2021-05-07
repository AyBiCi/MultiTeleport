package gui;

import org.bukkit.entity.Player;

public interface GuiPage {
    void openPageForNormalPlayer(Player player, Object... args);
    void openPageForOperator(Player player, Object... args);
    String getPageName();
}
