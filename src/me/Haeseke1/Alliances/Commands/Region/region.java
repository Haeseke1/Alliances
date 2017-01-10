package me.Haeseke1.Alliances.Commands.Region;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Commands.Outpost.Create.outpostCreate;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

public class region {
	
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			return;
		}
		if(args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Region =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... region tool #get region select tool");
			return;
		}
		
		if(args[1].equalsIgnoreCase("tool")){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return;
			}
			Player player = (Player) sender;
			player.getInventory().addItem(regionSelect.createItem());
			MessageManager.sendRemarkMessage(player, "Tool succesfully created!");
			return;
		}
	}

}
