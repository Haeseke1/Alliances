package me.Haeseke1.Alliances.regionSelect.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

public class region implements CommandExecutor {
	
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			return false;
		}
		if(args.length == 0){
			sender.sendMessage(MessageManager.infoColorCode + "===== Region =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/region tool #get region select tool");
			sender.sendMessage(MessageManager.infoColorCode + "/region pos1 #Set first loc");
			sender.sendMessage(MessageManager.infoColorCode + "/region pos2 #Set second loc");
			sender.sendMessage(MessageManager.infoColorCode + "/region show #show the region");
			return false;
		}
		
		if(!(sender instanceof Player)){
			String message = MessageManager.getMessage("Command_Error_Not_A_User");
			MessageManager.sendAlertMessage(message);
			return false;
		}
		Player player = (Player) sender;
		String wrong_arg = MessageManager.getMessage("Command_Error_Wrong_Arguments");
		
		if(args[0].equalsIgnoreCase("tool")){
			player.getInventory().addItem(regionSelect.createItem());
			String message = MessageManager.getMessage("Command_Region_Tool_Answer");
			MessageManager.sendMessage(player, message);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("pos1")){
			regionSelect.leftClick.put(player, player.getLocation());
			String message = MessageManager.getMessage("Command_Region_Pos1_Answer");
			message = message.replace("%x%", "" + player.getLocation().getBlockX())
					.replace("%y%", "" + player.getLocation().getBlockY())
					.replace("%z%", "" + player.getLocation().getBlockZ())
					.replace("%world_name%", player.getLocation().getWorld().getName());
			MessageManager.sendMessage(player, message);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("pos2")){
			regionSelect.rightClick.put(player, player.getLocation());
			String message = MessageManager.getMessage("Command_Region_Pos2_Answer");
			message = message.replace("%x%", "" + player.getLocation().getBlockX())
					.replace("%y%", "" + player.getLocation().getBlockY())
					.replace("%z%", "" + player.getLocation().getBlockZ())
					.replace("%world_name%", player.getLocation().getWorld().getName());
			MessageManager.sendMessage(player, message);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("show")){
			if(regionSelect.hasRegion(player)){
				if(Particle_Timer.showRegion.contains(player)){
					String message = MessageManager.getMessage("Command_Region_Show_Answer_Out");
					MessageManager.sendMessage(player, message);
					Particle_Timer.showRegion.remove(player);
				}else{
					String message = MessageManager.getMessage("Command_Region_Show_Answer_In");
					MessageManager.sendMessage(player, message);
					Particle_Timer.showRegion.add(player);
				}
				return false;
			}else{
				String message = MessageManager.getMessage("Command_Error_Select_Region");
				MessageManager.sendMessage(player, message);
				return false;
			}
		}
		
		MessageManager.sendMessage(player, wrong_arg);
		return false;
	}
}
