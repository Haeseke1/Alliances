package me.Haeseke1.Alliances.Outpost.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

public class Outpost implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0 && sender.hasPermission("Alliances.outpost.*")){
			sender.sendMessage(MessageManager.infoColorCode + "===== Outpost =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/outpost create #Create a new outpost");
			sender.sendMessage(MessageManager.infoColorCode + "/outpost listtype #get a list of different types!");
			return false;
		}
		
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("This command needs to be executed by a player");
			return false;
		}
		Player player = (Player) sender;
		if (player.hasPermission("Alliances.outpost.*")) {

			if (args[0].equalsIgnoreCase("listtype")) {
				sender.sendMessage(MessageManager.infoColorCode + "===== Outpost =====");
				sender.sendMessage(MessageManager.infoColorCode + "Types:");
				sender.sendMessage(MessageManager.infoColorCode + "1. Blacksmith");
				sender.sendMessage(MessageManager.infoColorCode + "2. Dock");
				sender.sendMessage(MessageManager.infoColorCode + "3. Farm");
				sender.sendMessage(MessageManager.infoColorCode + "4. God");
				sender.sendMessage(MessageManager.infoColorCode + "5. MagicTower");
				sender.sendMessage(MessageManager.infoColorCode + "6. Mine");
				sender.sendMessage(MessageManager.infoColorCode + "7. MobFarm");
				return false;
			}
			if (args[0].equalsIgnoreCase("create") && args.length > 1) {
				if (regionSelect.hasRegion(player)) {
					new me.Haeseke1.Alliances.Outpost.Outpost(args[1], regionSelect.leftClick.get(player), regionSelect.rightClick.get(player), null);
					MessageManager.sendMessage(player, ChatColor.DARK_GREEN + "Outpost created succesfully");
				} else {
					String message = "&cYou need to select a region first";
					MessageManager.sendMessage(player, message);
				}
			}
			MessageManager.sendMessage(player, "&cWrong argument do: /outpost");
			return false;
		}
		return false;
  }
}
