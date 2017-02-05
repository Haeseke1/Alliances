package me.Haeseke1.Alliances.Commands.Create;


import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Utils.MessageManager;


public class mainCreate {
	
	
	public static void onCommand(Player player, String[] args) {
		if(!AllianceManager.playerIsInAlli(player)){
			InventoryEvents.createInventory(player);
		}else{
			MessageManager.sendMessage(player, "&cYou're already in an alliance");
		}
	}

}
