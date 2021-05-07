package cli.subcommands;

import cli.SubcommandExecutor;
import main.MtpMain;
import org.bukkit.entity.Player;

public class SetLocation implements SubcommandExecutor{

	public boolean onSubCommand(Player player, String[] args) {
			if (args.length == 0) {
				if (MtpMain.getInstance().setLocationFromPlayerSession(player)) {
					player.sendMessage("Modyfikacje zostaly zapisane!");
					return true;
				} else {
					player.sendMessage("Nie zaczales zadnej modyfikacji!");
					return false;
				}
			}
			else{ //TODO: Make for args length 1
				return false;
			}
	}

	public int[] getPossibleArgsCount() {
		return new int[] {0,1};
	}

	public String getArgumentList() {
		return "";
	}

	public String getInfo() {
		return "Ustawia lokalizacje po komendzie modify";
	}

	public String getName() {
		return "set";
	}

}
