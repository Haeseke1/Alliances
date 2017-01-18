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
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
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
			player.sendMessage(MessageManager.infoColorCode + "/arena add spawn <name> <team> #Adds a spawnpoint to the config");
		}
		if(args.length == 4 && args[0].equalsIgnoreCase("create")){
			try{
				String name = args[1];
				int size = Integer.parseInt(args[2]);
				if(size%2 != 0){
					MessageManager.sendMessage(player,ChatColor.DARK_RED + "The arena size needs to be dividable by 2");
					return false;
				}
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
		}else
		if(args.length == 2 && args[0].equalsIgnoreCase("remove")){
			String name = args[1];
			ArenaManager.removeArena(name, player);
		}else
		
		if(args.length == 2 && args[0].equalsIgnoreCase("join")){
			String name = args[1];
			try {
				ArenaManager.joinArena(player, name);
			} catch (EmptyStringException e) {
				e.printStackTrace();
			}
		}else
		
		if(args.length == 4 && args[0].equalsIgnoreCase("add") && args[1].equalsIgnoreCase("spawn")){
			try{
			ArenaManager.addSpawn(args[2], Integer.parseInt(args[3]), player);
			}catch(Exception e){
			MessageManager.sendMessage(player, ChatColor.RED + "Team number must be an integer");
			}
		}else if(args.length == 3 && args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("lobby")){
			ArenaManager.setLobby(args[2], player);	
			return true;
			}else if(args.length == 4 && args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("status")){
				String status = args[3].toUpperCase();
				String arenaname = args[2].toLowerCase();
				switch(status){
				case "PLAYABLE":
					try {
						ArenaManager.setStatus(arenaname, status, player);
					} catch (EmptyLocationException e) {
						e.printStackTrace();
					}
					break;
				case "UNDER_MAINTANCE":
					try {
						ArenaManager.setStatus(arenaname, status, player);
					} catch (EmptyLocationException e) {
						e.printStackTrace();
					}
					break;
				default:
					MessageManager.sendMessage(player, ChatColor.RED + "You can't set the status to " + status);
					break;
				}
				return true;
				}
		return false;
	}
}
