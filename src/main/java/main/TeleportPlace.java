package main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TeleportPlace implements Serializable {
	private String name;
	private Location location;
	private ArrayList<SubteleportPlace> teleportPlaces = new ArrayList<>();


	
	public TeleportPlace(Location location, String name) {
		this.location = location.clone();
		this.name = name;
	}
	
	public Location getLocation() {
		return location.clone();
	}
	
	public String getName() {
		return name;
	}
	
	public void addPlace(Location location) {
		SubteleportPlace subPlace = new SubteleportPlace(this, location,teleportPlaces.size());
		teleportPlaces.add(subPlace);
	}
	
	public SubteleportPlace getTeleportPlace(int id) {
		if(id >= teleportPlaces.size()) return null;
		return teleportPlaces.get(id);
	}
	
	public boolean teleportToPlaceNumber(int number, Player player) {
		SubteleportPlace place = getTeleportPlace(number);
		if(place == null) return false;
		return player.teleport(place.getLocation());
	}
	
	public int getTeleportPlacesCount() {
		return teleportPlaces.size();
	}
	public boolean containsTeleportID(int id) {
		return (id >= 0 && id < getTeleportPlacesCount());
	}
	public List<SubteleportPlace> getTeleportPlaces(){
		return new ArrayList<>(teleportPlaces);
	}

	public void removeSubteleport(SubteleportPlace subteleportPlace) {
		teleportPlaces.remove(subteleportPlace.getId());
		recalculateIDs();
	}

	private void recalculateIDs(){
		for( int i = 0; i < teleportPlaces.size(); i++){
			teleportPlaces.get(i).setID(i);
		}
	}

	public void changeOrder(int a, int b){
		SubteleportPlace place = teleportPlaces.get(a);
		teleportPlaces.set(a, teleportPlaces.get(b));
		teleportPlaces.set(b, place);
		recalculateIDs();
	}

	private void writeObject(ObjectOutputStream stream) throws IOException
	{
		stream.writeUTF(name);

		stream.writeUTF		(location.getWorld().getName());
		stream.writeDouble	(location.getX());
		stream.writeDouble	(location.getY());
		stream.writeDouble	(location.getZ());
		stream.writeFloat	(location.getYaw());
		stream.writeFloat	(location.getPitch());

		stream.writeObject(teleportPlaces);
	}

	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException
	{
		name = stream.readUTF();

		String locationName = stream.readUTF();
		Double locationX = stream.readDouble();
		Double locationY = stream.readDouble();
		Double locationZ = stream.readDouble();
		Float locationYaw = stream.readFloat();
		Float locationPitch = stream.readFloat();

		location = new Location( Bukkit.getWorld(locationName), locationX, locationY, locationZ, locationYaw, locationPitch);

		teleportPlaces = (ArrayList<SubteleportPlace>) stream.readObject();
	}
}
