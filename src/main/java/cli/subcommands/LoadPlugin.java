package cli.subcommands;

import cli.SubcommandExecutor;
import main.MtpMain;
import org.bukkit.entity.Player;

public class LoadPlugin implements SubcommandExecutor {

    @Override
    public boolean onSubCommand(Player player, String[] args) {
        if(!player.hasPermission("mtp.saveandload")){
            player.sendMessage("Nie masz permisji aby używać tej komendy!");
            return false;
        }

        MtpMain.getInstance().loadPlugin(args[0]);
        player.sendMessage("Zaladowano!");
        return true;
    }

    @Override
    public int[] getPossibleArgsCount() {
        return new int[0];
    }

    @Override
    public String getArgumentList() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
