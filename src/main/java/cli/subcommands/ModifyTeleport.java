package cli.subcommands;

import cli.SubcommandExecutor;
import main.MtpMain;
import main.TeleportPlace;
import org.bukkit.entity.Player;

public class ModifyTeleport implements SubcommandExecutor{

	public boolean onSubCommand(Player player, String[] args) {
		//scp modify
		if(args.length == 0) {
			TeleportPlace tpCont = MtpMain.getInstance().getTeleport(player.getLocation());
			if(tpCont == null) {
				player.sendMessage("Nie stoisz na miejscu teleportu!");
				return false;
			}
			
			MtpMain.getInstance().createNewPlayerSession(player, tpCont, tpCont.getTeleportPlacesCount());
			player.sendMessage("Aby ustawic teleport, przejdz na jego miejsce i wywolaj komende /mtp set");
		}
		//scp modify number
		else if(args.length == 1) {
			TeleportPlace tpCont = MtpMain.getInstance().getTeleport(player.getLocation());
			if(tpCont == null) {
				player.sendMessage("Nie stoisz na miejscu teleportu!");
				return false;
			}
			
			int id = Integer.parseInt(args[0]);
			if(tpCont.containsTeleportID(id)) {
				MtpMain.getInstance().createNewPlayerSession(player, tpCont, id);
				player.sendMessage("Aby ustawic teleport, przejdz na jego miejsce i wywolaj komende /mtp set");
				return true;
			}
			else {
				player.sendMessage("Teleport na ktorym stoisz nie posiada takiego id teleportu!");
				return false;
			}
		}
		return false;
	}

	public int[] getPossibleArgsCount() {
		return new int[] {0,1};
	}

	public String getArgumentList() {
		return "[id]";
	}

	public String getInfo() {
		return "Dodaje nowy subteleport lub jesli zostanie podane id, modyfikuje lokalizacje juz istniejacego";
	}

	public String getName() {
		return "modify";
	}
	
}
