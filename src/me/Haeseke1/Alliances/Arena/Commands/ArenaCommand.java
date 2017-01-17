package me.Haeseke1.Alliances.Arena.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Arena.Arena;
import me.Haeseke1.Alliances.Arena.ArenaManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

public class ArenaCommand implements CommandExecutor{

	public boolean onCommand(CommandSender sender,Command cmd, String label,String[] args){
		if (!(sender instanceof Player)) {
			String message = MessageManager.getMessage("Command_Error_Not_A_User");
			MessageManager.sendAlertMessage(message);
			return false;
		}
		  Player player = (Player) sender;
		if(args.length == 0){
			player.sendMessage(MessageManager.infoColorCode + "===== Arena =====");
			player.sendMessage(MessageManager.infoColorCode + "Commands:");
			player.sendMessage(MessageManager.infoColorCode + "/arena create <name> <size> <countdown> #Creates and adds an arena to the game");
			player.sendMessage(MessageManager.infoColorCode + "/arena remove <name> #Removes an arena from the game");
			player.sendMessage(MessageManager.infoColorCode + "/arena join <name> #Join an arena by name");
		}
		if(args.length == 4 && args[0].equalsIgnoreCase("create")){
			try{
				String name = args[1];
				int size = Integer.parseInt(args[2]);
				int countdown = Integer.parseInt(args[3]);
				if(!regionSelect.hasRegion(player)){
					MessageManager.sendMessage(player,ChatColor.DARK_RED + "You need to select a region first");
					return false;
				}
				Location corner1 = regionSelect.getRegion(player, "left");
				Location corner2 =  regionSelect.getRegion(player, "right");
				Arena arena = new Arena(name,size,countdown,corner1,corner2);
                ArenaManager.createArena(arena, player);
			}catch(Exception e){
				MessageManager.sendMessage(player,ChatColor.DARK_RED + "Something went wrong: syntax error");
				return false;
			}
		}
		return false;
 }
}
