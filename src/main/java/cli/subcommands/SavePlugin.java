package cli.subcommands;

import cli.SubcommandExecutor;
import main.MtpMain;
import org.bukkit.entity.Player;

public class SavePlugin implements SubcommandExecutor {
    @Override
    public boolean onSubCommand(Player player, String[] args) {
        if(!player.hasPermission("mtp.saveandload")){
            player.sendMessage("Nie masz permisji aby używać tej komendy!");
            return false;
        }

        MtpMain.getInstance().savePlugin(args[0]);
        player.sendMessage("Zapisano!");
        return true;
    }

    @Override
    public int[] getPossibleArgsCount() {
        return new int[]{1};
    }

    @Override
    public String getArgumentList() {
        return "<nazwa>";
    }

    @Override
    public String getInfo() {
        return "Zapisuje stan teleportow do plikow z save'ami";
    }

    @Override
    public String getName() {
        return "save";
    }
}
