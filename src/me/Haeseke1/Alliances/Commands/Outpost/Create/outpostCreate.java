package me.Haeseke1.Alliances.Commands.Outpost.Create;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;


public class outpostCreate {
	
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("You need to be a player to do this command!");
			return;
		}
		Player player = (Player) sender;
		if(regionSelect.leftClick.containsKey(player) && regionSelect.rightClick.containsKey(player)){
			InventoryEvents.createInventory(player);
		}else{
			MessageManager.sendAlertMessage(player, "Select a region first!");
		}
	}

}
