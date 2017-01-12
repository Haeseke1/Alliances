package me.Haeseke1.Alliances.Commands.Challenges;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Commands.Challenges.Player.InventoryEvents;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class mainChallenges {

	
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("You need to be a player to do this command!");
			return;
		}
		Player player = (Player) sender;
		if(args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Challenges =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... challenges player #See your challenges");
			return;
		}
		
		if(args[1].equalsIgnoreCase("player")){
			InventoryEvents.createInventory(player);
		}
	}
	
}
