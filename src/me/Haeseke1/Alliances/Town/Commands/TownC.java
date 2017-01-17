package me.Haeseke1.Alliances.Town.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Town.Town;
import me.Haeseke1.Alliances.Town.TownManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class TownC implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0){
			sender.sendMessage(MessageManager.infoColorCode + "===== Town =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/town create <Name> #Create a new town");
			sender.sendMessage(MessageManager.infoColorCode + "/town claim <Name> #Claim more land for your town");
			return false;
		}
		
		if(!(sender instanceof Player)){
			String message = MessageManager.getMessage("Command_Error_Not_A_User");
			MessageManager.sendAlertMessage(message);
			return false;
		}
		

		Player player = (Player) sender;
		
		String wrong_arg = MessageManager.getMessage("Command_Error_Wrong_Arguments");
		
		if(args[0].equalsIgnoreCase("create") && args.length > 1){
			if(TownManager.isTown(args[1])){
				String message = MessageManager.getMessage("Town_Already_Exist");
				message = message.replace("%town_name%", args[1]);
				MessageManager.sendMessage(player, message);
				return false;
			}
			TownManager.createTown(player, AllianceManager.getAlliance(player), player.getLocation().getChunk(), args[1]);
			return false;
		}
		
		
		if(args[0].equalsIgnoreCase("claim") && args.length > 1){
			if(!TownManager.isTown(args[1])){
				String message = MessageManager.getMessage("Command_Town_Claim_Town_Doesnt_Exist");
				message = message.replace("%town_name%", args[1]);
				MessageManager.sendMessage(player, message);
				return false;
			}
			Town town = TownManager.getTown(args[1]);
			if(TownManager.isClaimed(player.getLocation().getChunk())){
				String message = MessageManager.getMessage("Town_Already_Claimed");
				MessageManager.sendMessage(player, message);
				return false;
			}
			if(!TownManager.isNextTo(player.getLocation().getChunk(), town)){
				String message = MessageManager.getMessage("Town_Not_Connected");
				MessageManager.sendMessage(player, message);
				return false;
			}
			town.addChunck(player.getLocation().getChunk());
			return false;
		}
		
		MessageManager.sendMessage(player, wrong_arg);
		return false;
	}
	
	

}
