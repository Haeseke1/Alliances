package me.Haeseke1.Alliances.Arena.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.MessageManager;

public class Arena implements CommandExecutor{

	public boolean onCommand(CommandSender sender,Command cmd, String label,String[] args){
		if (!(sender instanceof Player)) {
			String message = MessageManager.getMessage("Command_Error_Not_A_User");
			MessageManager.sendAlertMessage(message);
			return false;
		}
		Player player = (Player) sender;
		if(args.length == 0){
			MessageManager.sendMessage(player, MessageManager.infoColorCode + "===== Arena =====");
			MessageManager.sendMessage(player, MessageManager.infoColorCode + "Commands:");
			MessageManager.sendMessage(player, MessageManager.infoColorCode + "/arena create <name> <size> <countdown> #Creates and adds an arena to the game");
			MessageManager.sendMessage(player, MessageManager.infoColorCode + "/arena remove <name> #Removes an arena from the game");
			MessageManager.sendMessage(player, MessageManager.infoColorCode + "/arena join <name> #Join an arena by name");
		}
		return false;
	}
}
