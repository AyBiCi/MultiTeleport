package cli.subcommands;

import cli.SubcommandExecutor;
import main.MtpMain;
import org.bukkit.entity.Player;

public class ShowTeleportList implements SubcommandExecutor{

	public boolean onSubCommand(Player player, String[] args) {
		
		for(String s : MtpMain.getInstance().getListOfTeleportNames()) {
			player.sendMessage(s);
		}
		
		return true;
	}

	public int[] getPossibleArgsCount() {
		return new int[] {0};
	}

	public String getArgumentList() {
		return "";
	}

	public String getInfo() {
		return "Wypisuje liste nazw teleportow";
	}

	public String getName() {
		return "tplist";
	}

}
