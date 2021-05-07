package cli.subcommands;

import cli.SubcommandExecutor;
import main.MtpMain;
import org.bukkit.entity.Player;

public class TeleportToTeleport implements SubcommandExecutor{

	public boolean onSubCommand(Player player, String[] args) {
		if(MtpMain.getInstance().teleportPlayerToTeleport(player, args[0])) {
			player.sendMessage("Teleportowano do miejsca o nazwie "+args[0]+".");
		}
		else {
			player.sendMessage("Miejsce o nazwie " + args[0] + " nie istnieje!");
		}
		
		return true;
	}

	public int[] getPossibleArgsCount() {
		return new int[] {1};
	}

	public String getArgumentList() {
		return "<nazwa>";
	}

	public String getInfo() {
		return "Teleportuje do teleportu o danej nazwie";
	}

	public String getName() {
		return "tp";
	}
}
