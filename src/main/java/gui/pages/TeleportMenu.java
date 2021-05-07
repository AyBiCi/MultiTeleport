package gui.pages;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import gui.GuiPage;
import gui.GuiPages;
import main.MtpMain;
import main.SubteleportPlace;
import main.TeleportPlace;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TeleportMenu implements GuiPage{



	private void bindTeleportFunctionToSubTeleportPlaceButton(GuiItem button, SubteleportPlace subteleportPlace){
		button.setAction(event -> {
			Player player2 = (Player) event.getWhoClicked();
			MtpMain.getInstance().teleportPlayerToSubteleport(player2, subteleportPlace.getId(), subteleportPlace.getTeleportPlace());
			player2.getOpenInventory().close();
		});
	}

	public StaticPane createModyficationBarForOperators(TeleportPlace teleportPlace,Player player){
		StaticPane adminBar = new StaticPane(0, 0, 9, 1);
		adminBar.addItem(
				new GuiItem(GuiUtils.newItemStack(Material.NETHER_STAR,"Modyfikuj SubTeleport"),event -> GuiPages.openPage("modifyTeleport", player, teleportPlace)),
				0,
				0
		);
		adminBar.addItem(
				new GuiItem(GuiUtils.newItemStack(Material.CHEST,"Zmien kolejnosc","Pozwala na zmiane", "kolejnosci subteleportow"),
						event -> {
							GuiPages.openPage("changeOrder", player, teleportPlace, -1);
						}),
				1,
				0
		);
		adminBar.addItem(
				new GuiItem(GuiUtils.newItemStack(Material.RED_WOOL,"Dodaj SubTeleport","Pozwala na dodanie", "nowego teleportu"),
						event -> {
							player.performCommand("mtp modify");
							player.getOpenInventory().close();
						}),
				2,
				0
		);

		return adminBar;
	}


	private StaticPane createTeleportButtons(TeleportPlace place){
		int howManyButtonsNeeded = place.getTeleportPlacesCount();

		int rows = howManyButtonsNeeded / 9 + 1;

		StaticPane teleportButtons = new StaticPane(0, 0, 9, rows);

		int id = 0;
		for(SubteleportPlace tpPlace : place.getTeleportPlaces()){
			GuiItem button = GuiUtils.createButtonForSubteleportPlace(tpPlace);
			bindTeleportFunctionToSubTeleportPlaceButton(button, tpPlace);

			teleportButtons.addItem(
					button,
					id%9,
					id/9
					);
			id++;
		}

		return teleportButtons;
	}

	public ChestGui createStandardSubteleportsMenu(TeleportPlace teleportPlace){
		ChestGui gui = new ChestGui(1, "Teleportuj do");
		GuiUtils.makeUneditable(gui);

		StaticPane teleportButtons = createTeleportButtons(teleportPlace);
		gui.addPane(teleportButtons);

		return gui;
	}

	@Override
	public void openPageForNormalPlayer(Player player, Object... args) {
		Location teleportLocation = (Location) args[0];
		TeleportPlace teleportPlace = MtpMain.getInstance().getTeleport(teleportLocation);

		createStandardSubteleportsMenu(teleportPlace).show(player);
	}

	@Override
	public void openPageForOperator(Player player, Object... args) {
		Location teleportLocation = (Location) args[0];
		TeleportPlace teleportPlace = MtpMain.getInstance().getTeleport(teleportLocation);

		ChestGui gui = new ChestGui(2, "Teleportuj do");
		GuiUtils.makeUneditable(gui);

		StaticPane teleportButtons = createTeleportButtons(teleportPlace);
		gui.addPane(teleportButtons);

		StaticPane adminBar = createModyficationBarForOperators(teleportPlace,player);
		adminBar.setY(gui.getRows()-1);
		gui.addPane(adminBar);

		gui.show(player);
	}

	@Override
	public String getPageName() {
		return "tpmenu";
	}
}
