package me.Haeseke1.Alliances.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Commands.Coin.Coin;
import me.Haeseke1.Alliances.Commands.Create.mainCreate;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Alli implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MessageManager.infoColorCode + "===== Alliances =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances coin #Get list of coin commands");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances create #Create a new alliance");
			return false;
		}

		if (args[0].equalsIgnoreCase("coin")) {
			Coin.onCommand(sender, args);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("create")){
			if(args.length < 2){
				MessageManager.sendAlertMessage((Player) sender,
						"Not enough arguments! Use /alliance coin for more information!");
				return false;
			}
			mainCreate.onCommand(sender, args);
			return true;
		}

		MessageManager.sendAlertMessage((Player) sender, "Unknown command use /alliances for more help!");
		return false;
	}

}
