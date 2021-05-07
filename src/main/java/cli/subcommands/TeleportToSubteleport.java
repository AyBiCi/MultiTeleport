package cli.subcommands;

import cli.SubcommandExecutor;
import main.MtpMain;
import org.bukkit.entity.Player;

public class TeleportToSubteleport implements SubcommandExecutor{

	public boolean onSubCommand(Player player, String[] args) {
		int id = Integer.parseInt(args[0]);

		if(args.length == 1) {
			if(MtpMain.getInstance().teleportPlayerToSubteleport(player, id)) {
				player.sendMessage("Przeteleportowano!");
				return true;
			}
			else {
				player.sendMessage("Nie stoisz na miejscu teleportu lub "
								  +"dany teleport nie posiada teleportu o tym id!");
				return false;
			}
		}
		else{
			if(MtpMain.getInstance().teleportPlayerToSubteleport(player, id, args[1])) {
				player.sendMessage("Przeteleportowano!");
				return true;
			}
			else {
				player.sendMessage("Teleport o nazwie "+args[1]+"nie istnieje lub "
								  +"dany teleport nie posiada teleportu o tym id!");
				return false;
			}
		}
	}

	public int[] getPossibleArgsCount() {
		return new int[] {1,2};
	}

	public String getArgumentList() {
		return "<idSubteleportu> [nazwa]";
	}

	public String getInfo() {
		return "Teleportuje na subteleport o danym id teleportu na ktorym stoisz"
				+ " lub jesli zostanie podana nazwa, teleportuje na subteleport "
				+ "o danym id i danej nazwie teleportu";
	}

	public String getName() {
		return "subtp";
	}
	
}
