package me.Haeseke1.Alliances.Buildings.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Buildings.Builder.Builder;
import me.Haeseke1.Alliances.Buildings.Builder.BuilderManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class BuildingC implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 0) {
			sender.sendMessage(MessageManager.infoColorCode + "===== PVE =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/building createbuilder <name> <type> #Create new building");
			sender.sendMessage(MessageManager.infoColorCode + "/building getbuilder <name> #Get a block to build the building");
			return false;
		}
		
		if (!(sender instanceof Player)) {
			MessageManager.sendAlertMessage("This command needs to be executed by a player");
			return false;
		}
		Player player = (Player) sender;
		String wrong_arg = "&cWrong argument do: /building";
		
		if(args[0].equalsIgnoreCase("createbuilder") && args.length > 2){
			if(BuilderManager.createBuilder(player, args[1], args[2])){
				MessageManager.sendMessage(player, ChatColor.GREEN + args[1] + " can now be constructed!");
			}
			return false;
		}
		
		if(args[0].equalsIgnoreCase("getbuilder") && args.length > 1){
			if(!BuilderManager.nameExist(args[1])){
				MessageManager.sendMessage(player, "This builder does not exist!");
				return false;
			}
			Builder b = BuilderManager.getBuilder(args[1]);
			player.getInventory().addItem(b.createItemStack());
			MessageManager.sendMessage(player, ChatColor.GREEN + "Have fun building!");
			return false;
		}
		
		MessageManager.sendMessage(player, wrong_arg);
		
		return false;
	}

}
