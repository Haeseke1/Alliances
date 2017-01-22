package me.Haeseke1.Alliances.Outpost.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Outpost.Commands.Create.outpostCreate;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Outpost implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Outpost =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/outpost create #Create a new outpost");
			return false;
		}
		
		if(!(sender instanceof Player)){
			String message = MessageManager.getMessage("Command_Error_Not_A_User");
			MessageManager.sendAlertMessage(message);
			return false;
		}
		Player player = (Player) sender;
		String wrong_arg = MessageManager.getMessage("Command_Error_Wrong_Arguments");
		
		if(args[0].equalsIgnoreCase("create")){
			outpostCreate.onCommand(sender, args);
			return false;
		}	
		MessageManager.sendMessage(player, wrong_arg);
		return false;
	}
}
