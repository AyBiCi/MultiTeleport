package main;

import cli.CommandManager;
import gui.GuiPages;
import gui.initializers.TeleportToSubTeleportPageInitializer;
import gui.initializers.customEvent.OnNewBlockStandEvent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class MtpMain extends JavaPlugin{
	
	static MtpMain instance;
	public final HashMap<String, PlayerSession> playerSessions = new HashMap<>();
	TeleportPlaces teleportPlaces = new TeleportPlaces();


	
	public static MtpMain getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		CommandManager.registerSubcommandExecutors();
		OnNewBlockStandEvent.registerEvents(this);
		TeleportToSubTeleportPageInitializer.registerEvents();
		GuiPages.registerAllPages();

		loadPlugin("autosave");
	}

	public void onDisable(){
		savePlugin("autosave");
	}

	private String pathForFile = "."+File.separator+"plugins"+ File.separator +"Subteleport";

	private String getPath(String fileName){
		return pathForFile + '/' + fileName + ".bin";
	}

	public void savePlugin(String fileName){
		try {
			FileOutputStream fileOut = new FileOutputStream(getPath(fileName));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(teleportPlaces);
			out.close();
			fileOut.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void loadPlugin(String fileName){
		try {
			FileInputStream fileIn = new FileInputStream(getPath(fileName));
			ObjectInputStream in = new ObjectInputStream(fileIn);
			teleportPlaces = (TeleportPlaces) in.readObject();
			in.close();
			fileIn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CommandManager.onCommand(sender, args);
		return false;
	}
	
	public boolean createNewTeleport(Location location, String name) {
		return teleportPlaces.addPlace(location, name);
	}
	
	public boolean removeTeleport(Location location) {
		return teleportPlaces.removeTeleport(location);
	}
	
	public boolean removeTeleport(String name) {
		return teleportPlaces.removeTeleport(name);
	}
	
	public TeleportPlace getTeleport(Location location) {
		return teleportPlaces.getPlace(location);
	}
	
	public TeleportPlace getTeleport(String name) {
		return teleportPlaces.getPlace(name);
	}
	
	public List<String> getListOfTeleportNames(){
		return teleportPlaces.getTeleportList();
	}
	
	public void createNewPlayerSession(Player player, TeleportPlace container, int teleportID) {
		PlayerSession session = new PlayerSession();
		session.tpContainer = container;
		session.modifyID = teleportID;
		playerSessions.put(player.getName(), session);
	}
	
	public PlayerSession getPlayerSession(Player player) {
		return playerSessions.get(player.getName());
	}
	
	public void deletePlayerSession(Player player) {
		playerSessions.remove(player.getName());
	}
	
	public boolean setLocationFromPlayerSession(Player player) {
		PlayerSession session = getPlayerSession(player);
		if(session == null) {
			return false;
		}
		
		Location newLoc = player.getLocation().clone();
		
		if(session.tpContainer.containsTeleportID(session.modifyID)) {
			session.tpContainer.getTeleportPlace(session.modifyID).setLocation(newLoc);
		}
		else {
			session.tpContainer.addPlace(newLoc);
		}
		
		MtpMain.getInstance().deletePlayerSession(player);
		return true;
	}
	
	public boolean teleportPlayerToTeleport(Player player, String tpName) {
		TeleportPlace teleportPlace = MtpMain.getInstance().getTeleport(tpName);
		if(teleportPlace == null) {
			return false;
		}
		
		player.teleport(teleportPlace.getLocation());
		return true;
	}
	
	public boolean teleportPlayerToSubteleport(Player player, int teleportNumber, TeleportPlace teleportPlace) {
		if(teleportPlace == null) {
			return false;
		}
		
		SubteleportPlace tp = teleportPlace.getTeleportPlace(teleportNumber);
		if(tp == null) {
			return false;
		}
		
		player.teleport(tp.getLocation());
		return true;
	}
	
	public boolean teleportPlayerToSubteleport(Player player, int teleportNumber) {
		return teleportPlayerToSubteleport(player, 
				teleportNumber,
				MtpMain.getInstance().getTeleport(player.getLocation()));
	}
	
	public boolean teleportPlayerToSubteleport(Player player, int teleportNumber, String teleportName) {
		return teleportPlayerToSubteleport(player, 
				teleportNumber,
				MtpMain.getInstance().getTeleport(teleportName));
	}
}
