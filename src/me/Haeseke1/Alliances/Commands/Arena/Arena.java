package me.Haeseke1.Alliances.Commands.Arena;

import org.bukkit.command.CommandSender;

import me.Haeseke1.Alliances.Commands.Arena.Create.mainCreate;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Arena {
	
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Arena =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... create <Name> #Create arena with selected region!");
			return;
		}
		
		
		if(args[1].equalsIgnoreCase("create")){
			mainCreate.onCommand(sender, args);
		}
		
		
		
		
	}
}
