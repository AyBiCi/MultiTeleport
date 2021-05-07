package gui.initializers;

import gui.GuiPages;
import gui.initializers.customEvent.OnNewBlockStandEvent;
import gui.initializers.customEvent.OnNewBlockStandObserver;
import main.MtpMain;
import main.TeleportPlace;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class TeleportToSubTeleportPageInitializer implements OnNewBlockStandObserver {

    public static void registerEvents(){
        OnNewBlockStandEvent.addObserver(new TeleportToSubTeleportPageInitializer());
    }

    @Override
    public void onNewBlockStand(Player player, Block block) {
        Location playerLocation = block.getLocation();
        TeleportPlace container = MtpMain.getInstance().getTeleport(playerLocation);
        if(container != null){
            if(block.getBlockData().getMaterial() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
                GuiPages.openPage("tpmenu", player, block.getLocation() );
            }
        }
    }
}
