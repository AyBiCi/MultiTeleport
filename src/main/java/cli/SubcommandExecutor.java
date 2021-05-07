package cli;

import org.bukkit.entity.Player;

public interface SubcommandExecutor {
	boolean onSubCommand(Player player, String[] args);
	int[] getPossibleArgsCount();
	String getArgumentList();
	String getInfo();
	String getName();
}
