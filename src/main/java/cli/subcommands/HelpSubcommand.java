package cli.subcommands;

import cli.CommandManager;
import cli.SubcommandExecutor;
import org.bukkit.entity.Player;

public class HelpSubcommand implements SubcommandExecutor{

	public boolean onSubCommand(Player player, String[] args) {
		player.sendMessage("MultiTeleport - komendy");
		
		for(SubcommandExecutor executor : CommandManager.subcommandExecutors.values()) {
			player.sendMessage(
					  executor.getName() + " " 
					+ executor.getArgumentList() + " - " 
					+ executor.getInfo()
					);
		}
		
		return false;
	}

	public int[] getPossibleArgsCount() {
		return new int[] {0};
	}

	public String getArgumentList() {
		return "";
	}

	public String getInfo() {
		return "Pokazuje wszystkie subkomendy w pluginie.";
	}

	public String getName() {
		return "help";
	}

}
