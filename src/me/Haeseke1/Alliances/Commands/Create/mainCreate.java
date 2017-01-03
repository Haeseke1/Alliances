package me.Haeseke1.Alliances.Commands.Create;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Utils.MessageManager;


public class mainCreate {
	
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			return;
		}
		Player player = (Player) sender;
		if(!AllianceManager.playerIsInAlli(player)){
			InventoryEvents.createInventory(player);
		}else{
			MessageManager.sendAlertMessage(player, "You are already part of a alliance!");
		}
	}

}
