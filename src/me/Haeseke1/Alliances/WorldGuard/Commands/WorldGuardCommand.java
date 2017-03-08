package me.Haeseke1.Alliances.WorldGuard.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.WorldGuard.Regions.Region;

public class WorldGuardCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("This command needs to be executed by a player");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(args.length == 2 && args[0].equalsIgnoreCase("create")){
			String name = args[1];
			new Region(player,name,null,null);
			return true;
		}
		
		if(args.length == 4 && args[0].equalsIgnoreCase("set")){
			String setting = args[1];
			String name = args[2].toLowerCase();
			Boolean value = Boolean.valueOf(args[3]);
			if(Region.getRegionByName(name) == null){
				MessageManager.sendMessage(player, "&cThis region doesn't exists");
				return true;
			}
			Region region = Region.getRegionByName(name.toLowerCase());
			region.setSetting(player,setting, value);
			return true;
		}
		
		return false;
	}
	
}
