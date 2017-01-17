package me.Haeseke1.Alliances.Challenge.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Challenge.Commands.Player.InventoryEvents;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class mainChallenges implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0){
			sender.sendMessage(MessageManager.infoColorCode + "===== Challenges =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/challenges player #See your challenges");
			return false;
		}
		
		if(!(sender instanceof Player)){
			String message = MessageManager.getMessage("Command_Error_Not_A_User");
			MessageManager.sendAlertMessage(message);
			return false;
		}
		Player player = (Player) sender;
		String wrong_arg = MessageManager.getMessage("Command_Error_Wrong_Arguments");
		
		if(args[0].equalsIgnoreCase("player")){
			InventoryEvents.createInventory(player);
			return false;
		}
		MessageManager.sendMessage(player, wrong_arg);
		return false;
	}
	
}
