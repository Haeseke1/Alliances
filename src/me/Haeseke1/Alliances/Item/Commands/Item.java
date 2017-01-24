package me.Haeseke1.Alliances.Item.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Haeseke1.Alliances.Utils.MessageManager;

public class Item implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length < 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Items =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/outpost create #Create a new outpost");
		}
		return false;
	}

}
