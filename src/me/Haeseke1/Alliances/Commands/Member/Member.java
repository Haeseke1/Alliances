package me.Haeseke1.Alliances.Commands.Member;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Member {
	
	
	public static void onCommand(Player player, String[] args) {
		if(args.length == 1){
			player.sendMessage(MessageManager.infoColorCode + "===== Member =====");
			player.sendMessage(MessageManager.infoColorCode + "Commands:");
			player.sendMessage(MessageManager.infoColorCode + "/... member leave #Leave the alliance");
			return;
		}
		
		if(!AllianceManager.playerIsInAlli(player)){
			String message = MessageManager.getMessage("Command_Error_Not_In_A_Alliance");
			MessageManager.sendMessage(player, message);
			return;
		}
		
		String wrong_arg = MessageManager.getMessage("Command_Error_Wrong_Arguments");
		
		if(args[1].equalsIgnoreCase("leave")){
			if(AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
				String message = MessageManager.getMessage("Command_Alliance_Member_Leave_Is_Owner");
				MessageManager.sendMessage(player, message);
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
			String message = MessageManager.getMessage("Command_Alliance_Member_Leave_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", player.getName());
			MessageManager.sendMessage(player, message);
			message = MessageManager.getMessage("Command_Alliance_Member_Leave_Alli_Broadcast");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", player.getName());
			alli.sendPlayersMessage(message);
			return;
		}
		
		
		MessageManager.sendMessage(player, wrong_arg);
	}

}
