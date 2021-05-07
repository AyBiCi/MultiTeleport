package gui.initializers.customEvent;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface OnNewBlockStandObserver {
	void onNewBlockStand(Player player, Block block);
}
