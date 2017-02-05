package me.Haeseke1.Alliances.Outpost.Commands.Create;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;


public class outpostCreate {
	
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("This command needs to be executed by a player");
			return;
		}
		Player player = (Player) sender;
		if (player.hasPermission("Alliances.outpost.*")) {
			if (regionSelect.leftClick.containsKey(player) && regionSelect.rightClick.containsKey(player)) {
				InventoryEvents.createInventory(player);
			} else {
				String message = "&cYou need to select a region first";
				MessageManager.sendMessage(player, message);
			}
		}
	}
}
