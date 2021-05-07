package main;

import org.bukkit.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeleportPlaces implements Serializable {
	private HashMap<String, TeleportPlace> places = new HashMap<>();
	
	public boolean addPlace(Location location, String name) {
		if(places.containsKey(name)) {
			return false;
		}
		
		TeleportPlace place = new TeleportPlace(location, name);
		places.put(name, place);		
		return true;
	}
	
	public TeleportPlace getPlace(Location location) {
		
		for(TeleportPlace t : places.values()) {
			Location l = t.getLocation();
			
			if(l.getBlockX() == location.getBlockX() 
					&& l.getBlockY() == location.getBlockY() 
					&& l.getBlockZ() == location.getBlockZ()) {
				return t;
			}
		}
		
		return null;
	}
	
	public TeleportPlace getPlace(String placeName) {
		return places.get(placeName);
	}
	
	public boolean removeTeleport(Location location) {
		TeleportPlace place = getPlace(location);
		if(place == null) return false;
		return removeTeleport(place.getName());
	}
	public boolean removeTeleport(String name) {
		if(!places.containsKey(name)) return false;
		places.remove(name);
		return true;
	}
	
	public List<String> getTeleportList(){
		return new ArrayList<>(places.keySet());
	}

	
}
