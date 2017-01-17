package me.Haeseke1.Alliances.Outpost.Commands.Create;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;


public class outpostCreate {
	
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			String message = MessageManager.getMessage("Command_Error_Not_A_User");
			MessageManager.sendAlertMessage(message);
			return;
		}
		Player player = (Player) sender;
		
		if(regionSelect.leftClick.containsKey(player) && regionSelect.rightClick.containsKey(player)){
			InventoryEvents.createInventory(player);
		}else{
			String message = MessageManager.getMessage("Command_Error_Select_Region");
			MessageManager.sendMessage(player, message);
		}
	}

}
