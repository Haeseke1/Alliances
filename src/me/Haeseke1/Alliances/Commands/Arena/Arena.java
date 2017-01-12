package me.Haeseke1.Alliances.Commands.Arena;

import org.bukkit.command.CommandSender;

import me.Haeseke1.Alliances.Commands.Arena.CreateArena.mainCreateArena;
import me.Haeseke1.Alliances.Commands.Arena.CreateSign.mainCreateArenaSign;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Arena {
	
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Arena =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... createarena <name> #Create arena with selected region!");
			sender.sendMessage(MessageManager.infoColorCode + "/... createsign <coins> #Create arena with selected region!");
			return;
		}
		
		
		if(args[1].equalsIgnoreCase("createarena") && args.length >= 3){
			mainCreateArena.onCommand(sender, args);
		}
		if(args[1].equalsIgnoreCase("createsign") && args.length >= 3){
			mainCreateArenaSign.onCommand(sender, args);
		}
	}
}
