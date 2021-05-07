package gui;

import gui.pages.ChangeOrder;
import gui.pages.ModifySubteleportMenu;
import gui.pages.ModifyTeleportMenu;
import gui.pages.TeleportMenu;
import main.MtpMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GuiPages {

    private static final HashMap<String, GuiPage> pages = new HashMap<>();

    public static void registerPage(GuiPage page){
        pages.put(page.getPageName(), page);
    }

    public static void registerAllPages(){
        registerPage(new TeleportMenu());
        registerPage(new ModifyTeleportMenu());
        registerPage(new ModifySubteleportMenu());
        registerPage(new ChangeOrder());
    }

    public static void openPage(String pageName, Player player, Object... args) {
        GuiPage page = pages.get(pageName);

        player.getOpenInventory().close();

        Bukkit.getScheduler().runTaskLater(MtpMain.getInstance(), () -> {
            if( player.hasPermission("mtp.canModify") || player.isOp() ){
                page.openPageForOperator(player, args);
            }
            else{
                page.openPageForNormalPlayer(player, args);
            }
        },1);
    }
}
