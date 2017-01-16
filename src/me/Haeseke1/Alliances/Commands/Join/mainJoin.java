package me.Haeseke1.Alliances.Commands.Join;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Commands.Alli;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class mainJoin {
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("You need to be a player to do this command!");
			return;
		}
		Player player = (Player) sender;
		if(AllianceManager.playerIsInAlli(player)){
			MessageManager.sendAlertMessage(player, "You are already in a alliance!");
			return;
		}
		if(!Alli.invited.containsKey(player)){
			InventoryEvents.createInventory(new ArrayList<Alliance>(), player);
		}else{
			InventoryEvents.createInventory(Alli.invited.get(player), player);
		}
	}

}
