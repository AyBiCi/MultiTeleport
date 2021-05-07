package main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SubteleportPlace implements Serializable {
	private int id;
	private String name;
	private String teleportPlaceName;
	private Location location;
	private Material icon;


	public TeleportPlace getTeleportPlace(){
		return MtpMain.getInstance().getTeleport(teleportPlaceName);
	}
	public Material getIcon(){
		return icon;
	}
	public String getName(){
		return name;
	}

	public int getId() {
		return id;
	}

	public SubteleportPlace(TeleportPlace teleportPlace, Location location, int id){
		this(teleportPlace,"Teleport "+(id+1),location, id);
	}

	public SubteleportPlace(TeleportPlace teleportPlace, String name, Location location, int id){
		this(teleportPlace,name,location, id, Material.GREEN_WOOL);
	}
	public SubteleportPlace(TeleportPlace teleportPlace, String name, Location location,int id, Material icon) {
		this.id = id;
		this.icon = icon;
		this.name = name;
		this.location = location;
		this.teleportPlaceName = teleportPlace.getName();
	}
	
	public void setLocation(Location location) {
		this.location = location.clone();
	}
	public Location getLocation() {
		return location.clone();
	}

	public void delete() {
		MtpMain.getInstance().getTeleport(teleportPlaceName).removeSubteleport(this);
	}

	public void setID(int i) {
		id = i;
	}

    public void setName(String newName) {
		name = newName;
    }

	private void writeObject(ObjectOutputStream stream) throws IOException
	{
		stream.writeInt(id);
		stream.writeUTF(name);
		stream.writeUTF(teleportPlaceName);

		stream.writeUTF		(location.getWorld().getName());
		stream.writeDouble	(location.getX());
		stream.writeDouble	(location.getY());
		stream.writeDouble	(location.getZ());
		stream.writeFloat	(location.getYaw());
		stream.writeFloat	(location.getPitch());

		stream.writeUTF(icon.name());
	}

	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException
	{
		id = stream.readInt();
		name = stream.readUTF();
		teleportPlaceName = stream.readUTF();

		String locationName = stream.readUTF();
		Double locationX = stream.readDouble();
		Double locationY = stream.readDouble();
		Double locationZ = stream.readDouble();
		Float locationYaw = stream.readFloat();
		Float locationPitch = stream.readFloat();

		location = new Location( Bukkit.getWorld(locationName), locationX, locationY, locationZ, locationYaw, locationPitch);

		icon = Material.getMaterial(stream.readUTF());
	}
}
