package inputgetters.getters;

import main.MtpMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextGetter implements Listener {

    public TextGetter(){
        Bukkit.getServer().getPluginManager().registerEvents(this, MtpMain.getInstance());
    }

    private static Map<String, String> lastInputs = new HashMap<String, String>();
    private static List<String> toGetInput = new ArrayList<String>();
    private static Object mutex = new Object();

    public int playerToGetInput(Player player){
        for(int i = 0; i<toGetInput.size(); i++){
            if(toGetInput.get(i).equals(player.getName())){
                return i;
            }
        }
        return -1;
    }

    public static String get(Player player){
        String playerName = player.getName();
        boolean gotIt = false;

        synchronized(mutex) { // Add player to get input list
            toGetInput.add(playerName);
        }

        while(gotIt == false){ // We wait for player to write input

            synchronized(mutex){
                gotIt = lastInputs.containsKey(playerName);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String input = lastInputs.remove(playerName); // we remove input and return input
        return input;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        synchronized(mutex){ // We checking if player sending this message is found on our list of players we look for input
            int i = playerToGetInput(player);

            if (i != -1) {
                toGetInput.remove(i);
                lastInputs.put(player.getName(),event.getMessage());
                event.setCancelled(true);
            }
        }
    }

}
