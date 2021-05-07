package gui.pages;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import main.SubteleportPlace;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class GuiUtils {
    public static void makeUneditable(ChestGui gui){
        gui.setOnGlobalClick(event -> event.setCancelled(true));
    }
    public static GuiItem createButtonForSubteleportPlace(SubteleportPlace place){
        return new GuiItem(GuiUtils.newItemStack(place.getIcon(), place.getName()));
    }

    public static ItemStack newItemStack(Material material, String name, String... caption){
        ItemStack itemStack = new ItemStack(material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(name);
        itemMeta.setLore(new ArrayList<>(Arrays.asList(caption)));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
