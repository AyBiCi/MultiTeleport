package cli.subcommands;

import cli.SubcommandExecutor;
import main.MtpMain;
import org.bukkit.entity.Player;

public class CreateTeleport implements SubcommandExecutor{

	public boolean onSubCommand(Player player, String[] args) {
		
		if(MtpMain.getInstance().createNewTeleport(player.getLocation().clone(), args[0])) {
			player.sendMessage("Stworzono teleport "+args[0]+"!");
		}
		else {
			player.sendMessage("Teleport o nazwie "+args[0]+" już istnieje!");
		}
		
		return false;
	}

	public int[] getPossibleArgsCount() {
		return new int[] {1};
	}

	public String getArgumentList() {
		return "<nazwa>";
	}

	public String getInfo() {
		return "Dodaje nowy teleport na lokalizacji, na ktorej stoisz";
	}

	public String getName() {
		return "create";
	}

}
