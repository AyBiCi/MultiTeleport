package cli.subcommands;

import cli.SubcommandExecutor;
import gui.GuiPages;
import org.bukkit.entity.Player;

public class ShowGuiTest implements SubcommandExecutor{

	@Override
	public boolean onSubCommand(Player player, String[] args) {
		GuiPages.openPage("tpmenu", player, player.getLocation());
		return false;
	}

	@Override
	public int[] getPossibleArgsCount() {
		return new int[]{0};
	}

	@Override
	public String getArgumentList() {
		return "";
	}

	@Override
	public String getInfo() {
		return "Test command";
	}

	@Override
	public String getName() {
		return "gui";
	}

}
