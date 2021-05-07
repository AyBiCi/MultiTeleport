package gui.pages;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import gui.GuiPage;
import gui.GuiPages;
import main.MtpMain;
import main.SubteleportPlace;
import main.TeleportPlace;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ChangeOrder implements GuiPage {

    @Override
    public void openPageForNormalPlayer(Player player, Object... args) {
        player.sendMessage("Nie masz permisji do uzywania tego menu!");
    }

    private StaticPane createTeleportButtons(Player player, TeleportPlace place, int last_id){
        int howManyButtonsNeeded = place.getTeleportPlacesCount();

        int rows = howManyButtonsNeeded / 9 + 1;

        StaticPane teleportButtons = new StaticPane(0, 0, 9, rows);

        int id = 0;
        for(SubteleportPlace tpPlace : place.getTeleportPlaces()){
            GuiItem button = null;
            int finalId = id;
            if(last_id != id) {
                button = GuiUtils.createButtonForSubteleportPlace(tpPlace);

                button.setAction(event -> {
                    if(last_id == -1){
                        GuiPages.openPage("changeOrder", player, place, finalId);
                    }
                    else {
                        place.changeOrder(last_id, finalId);
                        GuiPages.openPage("changeOrder", player, place, -1);
                    }
                });
            }
            else{
                button = new GuiItem(GuiUtils.newItemStack(Material.RED_WOOL, "Przenoszony"),
                            event -> {
                                GuiPages.openPage("changeOrder", player, place, -1);
                            }
                        );
            }






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
    public void openPageForOperator(Player player, Object... args) {
        TeleportPlace teleportPlace = (TeleportPlace) args[0];
        int selected = (int) args[1];

        ChestGui gui = new ChestGui(1, "Zmien kolejnosc");

        StaticPane buttons = createTeleportButtons(player,teleportPlace, selected);
        gui.addPane(buttons);

        gui.show(player);
    }

    @Override
    public String getPageName() {
        return "changeOrder";
    }
}
