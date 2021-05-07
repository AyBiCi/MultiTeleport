package gui.pages;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import gui.GuiPage;
import inputgetters.MinecraftInput;
import main.MtpMain;
import main.SubteleportPlace;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ModifySubteleportMenu implements GuiPage {

    @Override
    public void openPageForNormalPlayer(Player player, Object... args) {
        player.sendMessage("Nie masz dostepu do tego menu!");
    }

    public StaticPane createOperatorBar(Player player, SubteleportPlace subteleportPlace){
        StaticPane pane = new StaticPane(0,0, 9,1);
        pane.addItem(
                new GuiItem(GuiUtils.newItemStack(Material.NAME_TAG,"Zmien nazwe"),
                        event -> {
                            player.getOpenInventory().close();
                            Bukkit.getScheduler().runTaskAsynchronously(MtpMain.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    player.sendMessage("Wpisz na czacie nowa nazwe subteleportu:");
                                    String newName = MinecraftInput.text.get(player);
                                    player.sendMessage("Nazwa subteleportu zostala zmieniona na " + newName + "!");
                                    subteleportPlace.setName(newName);
                                }
                            });
                        }),
                0,
                0);

        pane.addItem(
                new GuiItem(GuiUtils.newItemStack(Material.OAK_SAPLING,"Zmien lokalizacje"),
                        event -> {
                            player.performCommand("mtp modify "+(subteleportPlace.getId()));
                            player.getOpenInventory().close();
                        }),
                1,
                0);
        pane.addItem(
                new GuiItem(GuiUtils.newItemStack(Material.RED_WOOL,"Usun"),
                        event -> {
                            subteleportPlace.delete();
                            player.sendMessage("Teleport zostal usuniety!");
                            player.getOpenInventory().close();
                        }),
                2,
                0);

        return pane;
    }

    @Override
    public void openPageForOperator(Player player, Object... args) {
        SubteleportPlace subteleportPlace = (SubteleportPlace) args[0];

        ChestGui gui = new ChestGui(1,subteleportPlace.getName());
        GuiUtils.makeUneditable(gui);

        StaticPane adminButtons = createOperatorBar(player, subteleportPlace);
        gui.addPane(adminButtons);

        gui.show(player);
    }

    @Override
    public String getPageName() {
        return "modifySubteleport";
    }
}
