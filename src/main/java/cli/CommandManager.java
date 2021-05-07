package cli;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandManager{
	public static final HashMap<String, SubcommandExecutor> subcommandExecutors = new HashMap<>();
	
	public static void registerSubcommandExecutors() {
		registerSubcommandExecutor(new cli.subcommands.CreateTeleport(), "create");
		registerSubcommandExecutor(new cli.subcommands.DeleteTeleport(), "delete");
		registerSubcommandExecutor(new cli.subcommands.ModifyTeleport(), "modify");
		registerSubcommandExecutor(new cli.subcommands.TeleportToTeleport(), "tp");
		registerSubcommandExecutor(new cli.subcommands.TeleportToSubteleport(), "subtp");
		registerSubcommandExecutor(new cli.subcommands.ShowTeleportList(), "tplist");
		registerSubcommandExecutor(new cli.subcommands.SetLocation(), "set");
		registerSubcommandExecutor(new cli.subcommands.HelpSubcommand(), "help");
		registerSubcommandExecutor(new cli.subcommands.ShowGuiTest(), "gui");
		registerSubcommandExecutor(new cli.subcommands.SavePlugin(), "save");
		registerSubcommandExecutor(new cli.subcommands.LoadPlugin(), "load");
	}
	
	public static void registerSubcommandExecutor(SubcommandExecutor executor, String subcommandName) {
		subcommandExecutors.put(subcommandName, executor);
	}
	
	public static SubcommandExecutor getSubcommandExecutor(String subcomname) {
		return subcommandExecutors.get(subcomname);
	}
	
	private static boolean subcommandExecutorExists(String subcommand) {
		return subcommandExecutors.containsKey(subcommand);
	}

	public static void onCommand(CommandSender sender, String[] args) {
		
		Player player = (Player) sender;
		
		if(args.length == 0) {
			player.sendMessage("Wpisz /spc help aby poznac liste komend.");
			return;
		}
		
		String subcommandName = args[0];
		
		if(subcommandExecutorExists(subcommandName)) {
			String[] newArgs = (String[]) ArrayUtils.subarray(args,1,args.length);
			SubcommandExecutor executor = getSubcommandExecutor(subcommandName);
			executor.onSubCommand(player, newArgs);
		}
		else {
			player.sendMessage("Nieznana komenda! Wpisz /spc help aby poznac liste komend.");
		}

	}
}
