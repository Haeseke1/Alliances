package me.Haeseke1.Alliances.Commands.Create;


import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Utils.MessageManager;


public class mainCreate {
	
	
	public static void onCommand(Player player, String[] args) {
		if(!AllianceManager.playerIsInAlli(player)){
			InventoryEvents.createInventory(player);
		}else{
			String message = MessageManager.getMessage("Command_Alliance_Join_And_Create_Already_In_A_Alliance");
			MessageManager.sendMessage(player, message);
		}
	}

}
