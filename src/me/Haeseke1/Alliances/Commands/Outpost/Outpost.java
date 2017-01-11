package me.Haeseke1.Alliances.Commands.Outpost;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Commands.Outpost.Create.outpostCreate;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Outpost {
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("You need to be a player to do this command!");
			return ;
		}
		Player player = (Player) sender;
		if(args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Outpost =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... outpost create #Create a new outpost");
			return;
		}
		
		if(args[1].equalsIgnoreCase("create")){
			outpostCreate.onCommand(sender, args);
		}
	}

}
