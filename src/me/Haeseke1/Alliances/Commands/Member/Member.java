package me.Haeseke1.Alliances.Commands.Member;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Member {
	
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			return;
		}
		if(args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Member =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... member leave #Leave the alliance");
			return;
		}
		
		if(args[1].equalsIgnoreCase("leave")){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return ;
			}
			Player player = (Player) sender;
			if(AllianceManager.playerIsInAlli(player)){
				if(AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
					MessageManager.sendMessage(player,"You cannot leave the faction, when you are the owner!");
					return;
				}
				Alliance alli = AllianceManager.getAlliance(player);
				HashMap<UUID,String> members = alli.getMembers();
				members.remove(player.getUniqueId());
				if(alli.getAdmins().contains(player.getUniqueId())){
					List<UUID> admins = alli.getAdmins();
					admins.remove(player.getUniqueId());
					alli.setAdmins(admins);
				}
				MessageManager.sendMessage(player, "You left your alliance!");
				alli.sendPlayersMessage(player.getName() + " left the alliance!");
			}else{
				MessageManager.sendMessage(player, "You are not in a alliance!");
			}
			return;
		}
	}

}
