package me.Haeseke1.Alliances.Commands.Join;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Commands.Alli;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class mainJoin {
	
	public static void onCommand(Player player, String[] args) {
		if(AllianceManager.playerIsInAlli(player)){
			MessageManager.sendMessage(player, "&cYou're already a member of an alliance");
			return;
		}
		if(!Alli.invited.containsKey(player)){
			InventoryEvents.createInventory(new ArrayList<Alliance>(), player);
		}else{
			InventoryEvents.createInventory(Alli.invited.get(player), player);
		}
	}

}
