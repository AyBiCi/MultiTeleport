package gui.initializers.customEvent;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OnNewBlockStandEvent implements Listener {
	private static final List<OnNewBlockStandObserver> observers = new ArrayList<>();
	
	private static final HashMap<String, Block> lastStandedBlock = new HashMap<>();
	
	public static void registerEvents(JavaPlugin plugin){
		plugin.getServer().getPluginManager().registerEvents(new OnNewBlockStandEvent(), plugin);
	}
	
	public static void addObserver(OnNewBlockStandObserver obs) {
		observers.add(obs);
	}
	
	public static void removeObserver(OnNewBlockStandObserver obs) {
		observers.remove(obs);
	}
	
	private static void sendNewEvent(Player player) {
		Block b = lastStandedBlock.get(player.getName());
		for(OnNewBlockStandObserver obs : observers) {
			obs.onNewBlockStand(player, b);
		}
	}
	
	@EventHandler
	@SuppressWarnings("deprecation")
	public void onPlayerMove(PlayerMoveEvent event){
		String playername = event.getPlayer().getName();
		Block newBlock = event.getTo().getBlock();
		
		if(!lastStandedBlock.containsKey(playername) ) {
			lastStandedBlock.put(playername, newBlock);
			sendNewEvent(event.getPlayer());
			return;
		}
		Block lastBlock = lastStandedBlock.get(playername);
		
		if(lastBlock.getData() != newBlock.getData() || !lastBlock.getType().equals(newBlock.getType()) ) {
			lastStandedBlock.put(playername, newBlock);
			sendNewEvent(event.getPlayer());
		}
	}
}
