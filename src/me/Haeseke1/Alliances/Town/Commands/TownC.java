package me.Haeseke1.Alliances.Town.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Town.Town;
import me.Haeseke1.Alliances.Town.TownManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;
import net.md_5.bungee.api.ChatColor;

public class TownC implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0){
			sender.sendMessage(MessageManager.infoColorCode + "===== Town =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/town create <Name> #Create a new town");
			sender.sendMessage(MessageManager.infoColorCode + "/town claim <Name> #Claim more land for your town");
			sender.sendMessage(MessageManager.infoColorCode + "/town show #Show your land");
			if (sender.hasPermission("Alliances.town.*")) {
				sender.sendMessage(MessageManager.infoColorCode + "/town unclaimable #Make a chunk unclaimable");
			}
			return false;
		}
		
		if(!(sender instanceof Player)){
			String message = "This command needs to be executed by a player";
			MessageManager.sendAlertMessage(message);
			return false;
		}
		

		Player player = (Player) sender;
		
		String wrong_arg = "Wrong argument do: /town";
		
		if(args[0].equalsIgnoreCase("create") && args.length > 1){
			if(TownManager.isTown(args[1])){
				String message = "&6%town_name%&c does already exists";
				message = message.replace("%town_name%", args[1]);
				MessageManager.sendMessage(player, message);
				return false;
			}
			TownManager.createTown(player, AllianceManager.getAlliance(player), player.getLocation().getChunk(), args[1]);
			return false;
		}
		
		
		if(args[0].equalsIgnoreCase("claim") && args.length > 1){
			if(!TownManager.isTown(args[1])){
				String message = "&c";
				message = message.replace("%town_name%", args[1]);
				MessageManager.sendMessage(player, message);
				return false;
			}
			Town town = TownManager.getTown(args[1]);
			TownManager.claimLand(player, town);
			return false;
		}
		if (player.hasPermission("Alliances.town.*")) {
			if(args[0].equalsIgnoreCase("unclaimable")){
				if(regionSelect.hasRegion(player)){
					int i = TownManager.addUnclaimable(regionSelect.leftClick.get(player), regionSelect.rightClick.get(player));
					MessageManager.sendMessage(player, ChatColor.GREEN + "Added " + i + " chunks to unclaimable!");
					return false;
				}
				if(Town.unclaimable.contains(player.getLocation().getChunk())){
					MessageManager.sendMessage(player, ChatColor.RED + "This is already unclaimable!");
					return false;
				}
				Town.unclaimable.add(player.getLocation().getChunk());
				MessageManager.sendMessage(player, ChatColor.GREEN + "This land is now unclaimable!");
				return false;
			}
		}
		
		
		if(args[0].equalsIgnoreCase("show")){
			if(!AllianceManager.playerIsInAlli(player)){
				String message = "&cYou aren't in an alliance";
				MessageManager.sendMessage(player, message);
				return false;
			}

			if(Particle_Timer.showRegion.contains(player)){
				String message = "&cTurned off town visuals";
				MessageManager.sendMessage(player, message);
				Particle_Timer.showRegion.remove(player);
			}else{
				String message = "&cTurned on town visuals";
				MessageManager.sendMessage(player, message);
				Particle_Timer.showRegion.add(player);
			}

			return false;
		}
		
		MessageManager.sendMessage(player, wrong_arg);
		return false;
	}
	
	

}
