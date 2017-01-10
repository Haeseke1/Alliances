package me.Haeseke1.Alliances.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Commands.Coin.Coin;
import me.Haeseke1.Alliances.Commands.Create.mainCreate;
import me.Haeseke1.Alliances.Commands.Outpost.Outpost;
import me.Haeseke1.Alliances.Commands.Region.region;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Alli implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MessageManager.infoColorCode + "===== Alliances =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances coin #Get list of coin commands");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances create #Create a new alliance");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances outpost #Get list of outpost commands");
			return false;
		}

		if (args[0].equalsIgnoreCase("coin")) {
			Coin.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("create")){
			mainCreate.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("outpost")){
			Outpost.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("region")){
			region.onCommand(sender, args);
			return true;
		}

		MessageManager.sendAlertMessage((Player) sender, "Unknown command use /alliances for more help!");
		return false;
	}

}
