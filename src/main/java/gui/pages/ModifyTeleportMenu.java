package gui.pages;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import gui.GuiPage;
import gui.GuiPages;
import main.SubteleportPlace;
import main.TeleportPlace;
import org.bukkit.entity.Player;

public class ModifyTeleportMenu implements GuiPage {
    @Override
    public void openPageForNormalPlayer(Player player, Object... args) {
        player.sendMessage("Nie masz dostepu do tego menu!");
    }

    @Override
    public void openPageForOperator(Player player, Object... args) {
        TeleportPlace teleportPlace = (TeleportPlace) args[0];

        ChestGui gui = new ChestGui(teleportPlace.getTeleportPlacesCount()/9+1, "Modyfikuj... ");
        GuiUtils.makeUneditable(gui);

        StaticPane modButtons = createModificationButtons(teleportPlace,player);
        gui.addPane(modButtons);

        gui.show(player);
    }

    private StaticPane createModificationButtons(TeleportPlace place, Player player){
        int howManyButtonsNeeded = place.getTeleportPlacesCount();

        int rows = howManyButtonsNeeded / 9 + 1;

        StaticPane teleportButtons = new StaticPane(0, 0, 9, rows);

        int id = 0;
        for(SubteleportPlace tpPlace : place.getTeleportPlaces()){
            GuiItem button = GuiUtils.createButtonForSubteleportPlace(tpPlace);
            button.setAction(event -> GuiPages.openPage("modifySubteleport", player, tpPlace));

            teleportButtons.addItem(
                    button,
                    id%9,
                    id/9
            );
            id++;
        }

        return teleportButtons;
    }

    @Override
    public String getPageName() {
        return "modifyTeleport";
    }
}
