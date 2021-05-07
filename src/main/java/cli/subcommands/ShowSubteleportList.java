package cli.subcommands;

import cli.SubcommandExecutor;
import main.MtpMain;
import main.SubteleportPlace;
import main.TeleportPlace;
import org.bukkit.entity.Player;

public class ShowSubteleportList implements SubcommandExecutor{

	public boolean onSubCommand(Player player, String[] args) {
		TeleportPlace tpcontainer;
		if(args.length == 0) {
			tpcontainer = MtpMain.getInstance().getTeleport(player.getLocation());
		}
		else {
			tpcontainer = MtpMain.getInstance().getTeleport(args[0]);
		}
		
		if(tpcontainer == null) {
			if(args.length == 0)
				player.sendMessage("Nie stoisz na miejscu teleportu!");
			else
				player.sendMessage("Teleport o takim id nie istnieje!");
			return false;
		}
		
		for(SubteleportPlace place : tpcontainer.getTeleportPlaces())
		{
			player.sendMessage(place.getLocation().toString());
		}
		
		return true;
	}

	public int[] getPossibleArgsCount() {
		return new int[] {0,1};
	}

	public String getArgumentList() {
		return "[nazwa]";
	}

	public String getInfo() {
		return "Pokazuje liste lokalizacji subteleportow na ktorym stoisz lub"
				+ " jesli podasz nazwe, lokalizacje subteleportow o danej nazwie";
	}

	public String getName() {
		return "subtplist";
	}

}
