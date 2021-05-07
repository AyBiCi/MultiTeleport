package cli.subcommands;

import cli.SubcommandExecutor;
import main.MtpMain;
import org.bukkit.entity.Player;

public class DeleteTeleport implements SubcommandExecutor{

	public boolean onSubCommand(Player player, String[] args) {
		//scp remove
		if(args.length == 0) {
			if(MtpMain.getInstance().removeTeleport(player.getLocation().clone())) {
				player.sendMessage("Teleport zostal usuniety!");
				return true;
			}
			else {
				player.sendMessage("Nie stoisz na żadnym teleporcie!");
				return true;
			}
		}
		//scp remove name
		else {
			String teleportToRemoveName = args[0];
			if(MtpMain.getInstance().removeTeleport(teleportToRemoveName)) {
				player.sendMessage("Teleport o nazwie "+teleportToRemoveName+" zostal usuniety!");
				return true;
			}
			else {
				player.sendMessage("Teleport o nazwie "+teleportToRemoveName+" nie istnieje!");
			}
		}
		
		return false;
	}

	public int[] getPossibleArgsCount() {
		return new int[]{0,1};
	}

	public String getArgumentList() {
		return "[teleportName]";
	}

	public String getInfo() {
		return "Usuwa teleport na ktorym stoisz lub jesli podasz nazwe, usuwa teleport o danej nazwie";
	}

	public String getName() {
		return "delete";
	}

}
