package me.Haeseke1.Alliances.Item.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Haeseke1.Alliances.Utils.MessageManager;

public class Item implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0 && sender.hasPermission("Alliances.items.*")){
			sender.sendMessage(MessageManager.infoColorCode + "===== Items =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/item compass #Create a new compass outpost");
			return false;
		}
		return false;	
	}
}
