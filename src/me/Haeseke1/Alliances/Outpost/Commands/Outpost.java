package me.Haeseke1.Alliances.Outpost.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Outpost.OutpostManager;
import me.Haeseke1.Alliances.Outpost.OutpostType;
import me.Haeseke1.Alliances.Outpost.Commands.Create.outpostCreate;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Outpost implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0 && sender.hasPermission("Alliances.outpost.*")){
			sender.sendMessage(MessageManager.infoColorCode + "===== Outpost =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/outpost create #Create a new outpost");
			sender.sendMessage(MessageManager.infoColorCode + "/outpost addreward <Luck> <OutpostType> #Add item in hand to outpost reward");
			sender.sendMessage(MessageManager.infoColorCode + "/outpost listtype #get a list of different types!");
			return false;
		}
		
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("This command needs to be executed by a player");
			return false;
		}
		Player player = (Player) sender;
		if (player.hasPermission("Alliances.outpost.*")) {
			if (args[0].equalsIgnoreCase("addreward") && args.length > 1) {
				try {
					if (OutpostType.getOutpostType(args[2]) == null) {
						MessageManager.sendMessage(player, "&cThis outposttype doesn't exists");
						return false;
					}
					OutpostManager.addReward(player.getItemInHand(), Integer.parseInt(args[1]),
							OutpostType.getOutpostType(args[2]));
					MessageManager.sendMessage(player, "&2You've added a reward to the outpost");
					return false;
				} catch (Exception e) {
					MessageManager.sendMessage(player, "&cWrong argument do: /outpost");
				}
				return false;
			}

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

			if (args[0].equalsIgnoreCase("create")) {
				outpostCreate.onCommand(sender, args);
				return false;
			}
			MessageManager.sendMessage(player, "&cWrong argument do: /outpost");
			return false;
		}
		return false;
  }
}
