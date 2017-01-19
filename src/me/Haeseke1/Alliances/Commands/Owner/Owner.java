package me.Haeseke1.Alliances.Commands.Owner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Commands.Alli;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.PlayerManager;

public class Owner {

	public static void onCommand(Player player, String[] args) {
		if(args.length == 1){
			player.sendMessage(MessageManager.infoColorCode + "===== Owner =====");
			player.sendMessage(MessageManager.infoColorCode + "Commands:");
			player.sendMessage(MessageManager.infoColorCode + "/... owner invite <Player> #Invite players to your alliance");
			player.sendMessage(MessageManager.infoColorCode + "/... owner disband #Disband your alliance");
			player.sendMessage(MessageManager.infoColorCode + "/... owner change <Player> #Change owner");
			player.sendMessage(MessageManager.infoColorCode + "/... owner setrank <Player> <Name> #Set rank of a player");
			player.sendMessage(MessageManager.infoColorCode + "/... owner addadmin <Player> #Set rank of a player");
			player.sendMessage(MessageManager.infoColorCode + "/... owner removeadmin <Player> #Set rank of a player");
			return;
		}
		
		
		if(args[1].equalsIgnoreCase("disband")){
			if(!AllianceManager.playerIsInAlli(player) || !AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
				String message = MessageManager.getMessage("Command_Error_Not_A_Owner");
				MessageManager.sendMessage(player, message);
				return;
			}
			
			Alliance alli = AllianceManager.getAlliance(player);
			String name = alli.getName();
			Main.alliances.remove(alli);
			
			String message = MessageManager.getMessage("Command_Alliance_Owner_Disband_Answer");
			message = message.replace("%alli_name%", name);
			MessageManager.sendMessage(player, message);
			message = MessageManager.getMessage("Command_Alliance_Owner_Disband_Broadcast");
			message = message.replace("%alli_name%", name);
			MessageManager.sendBroadcast(message);
			return;
		}
		
		if(args[1].equalsIgnoreCase("change") && args.length > 2){
			if(!AllianceManager.playerIsInAlli(player) || !AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
				String message = MessageManager.getMessage("Command_Error_Not_A_Owner");
				MessageManager.sendMessage(player, message);
				return;
			}
			if(!PlayerManager.isPlayerOnline(args[1])){
				String message = MessageManager.getMessage("Command_Error_Not_A_Online_Player");
				MessageManager.sendMessage(player, message);
				return;
			}
			Player nowner = PlayerManager.getPlayer(args[2]);
			if(!AllianceManager.getAlliance(player).getMembers().containsKey(nowner.getUniqueId())){
				String message = MessageManager.getMessage("Command_Error_Not_In_Your_Alliance");
				MessageManager.sendMessage(player, message);
				return;
			}
			
			AllianceManager.getAlliance(player).setOwner(nowner.getUniqueId());
			
			String message = MessageManager.getMessage("Command_Alliance_Owner_Change_Owner_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", nowner.getName());
			MessageManager.sendMessage(player, message);
			message = MessageManager.getMessage("Command_Alliance_Owner_Change_Owner_Changed_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", nowner.getName());
			MessageManager.sendMessage(nowner, message);
			message = MessageManager.getMessage("Command_Alliance_Owner_Change_Owner_Alli_Broadcast");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", nowner.getName());
			AllianceManager.getAlliance(player).sendPlayersMessage(message);
			return;
		}
		
		if(args[1].equalsIgnoreCase("addadmin") && args.length > 2){
			if(!AllianceManager.playerIsInAlli(player) || !AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
				String message = MessageManager.getMessage("Command_Error_Not_A_Owner");
				MessageManager.sendMessage(player, message);
				return;
			}
			if(!PlayerManager.isPlayerOnline(args[1])){
				String message = MessageManager.getMessage("Command_Error_Not_A_Online_Player");
				MessageManager.sendMessage(player, message);
				return;
			}
			Player nadmin = PlayerManager.getPlayer(args[2]);
			if(!AllianceManager.getAlliance(player).getMembers().containsKey(nadmin.getUniqueId())){
				String message = MessageManager.getMessage("Command_Error_Not_In_Your_Alliance");
				MessageManager.sendMessage(player, message);
				return;
			}
			
			AllianceManager.getAlliance(player).addAdmins(nadmin.getUniqueId());
			
			String message = MessageManager.getMessage("Command_Alliance_Owner_Add_Admin_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", nadmin.getName());
			MessageManager.sendMessage(player, message);
			message = MessageManager.getMessage("Command_Alliance_Owner_Add_Admin_Changed_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", nadmin.getName());
			MessageManager.sendMessage(nadmin, message);
			message = MessageManager.getMessage("Command_Alliance_Owner_Add_Admin_Alli_Broadcast");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", nadmin.getName());
			AllianceManager.getAlliance(player).sendPlayersMessage(message);
			return;
		}
		
		if(args[1].equalsIgnoreCase("removeadmin") && args.length > 2){
			if(!AllianceManager.playerIsInAlli(player) || !AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
				String message = MessageManager.getMessage("Command_Error_Not_A_Owner");
				MessageManager.sendMessage(player, message);
				return;
			}
			if(!PlayerManager.isPlayerOnline(args[1])){
				String message = MessageManager.getMessage("Command_Error_Not_A_Online_Player");
				MessageManager.sendMessage(player, message);
				return;
			}
			Player nadmin = PlayerManager.getPlayer(args[2]);
			if(!AllianceManager.getAlliance(player).getMembers().containsKey(nadmin.getUniqueId())){
				String message = MessageManager.getMessage("Command_Error_Not_In_Your_Alliance");
				MessageManager.sendMessage(player, message);
				return;
			}
			if(!AllianceManager.getAlliance(player).getAdmins().contains(nadmin.getUniqueId())){
				String message = MessageManager.getMessage("Command_Alliance_Owner_Remove_Admin_Not_Admin");
				message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
						.replace("%player_name%", nadmin.getName());
				MessageManager.sendMessage(player, message);
				return;
			}
			
			List<UUID> admins = AllianceManager.getAlliance(player).getAdmins();
			admins.remove(nadmin.getUniqueId());
			AllianceManager.getAlliance(player).setAdmins(admins);
			
			String message = MessageManager.getMessage("Command_Alliance_Owner_Remove_Admin_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", nadmin.getName());
			MessageManager.sendMessage(player, message);
			message = MessageManager.getMessage("Command_Alliance_Owner_Remove_Admin_Changed_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", nadmin.getName());
			MessageManager.sendMessage(nadmin, message);
			message = MessageManager.getMessage("Command_Alliance_Owner_Remove_Admin_Alli_Broadcast");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", nadmin.getName());
			AllianceManager.getAlliance(player).sendPlayersMessage(message);
			return;
		}
		
		if(args[1].equalsIgnoreCase("invite") && args.length > 2){
			if(AllianceManager.playerIsInAlli(player) && (AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId()) || AllianceManager.getAlliance(player).getAdmins().contains(player.getUniqueId()))){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendMessage(player, "This player is not online!");
					return;
				}
				Player pinvite = PlayerManager.getPlayer(args[2]);
				if(AllianceManager.getAlliance(player).getMembers().containsKey(pinvite.getUniqueId())){
					MessageManager.sendMessage(player, "This person is already in your alliance!");
					return;
				}
				if(Alli.invited.containsKey(pinvite)){
					List<Alliance> allis = Alli.invited.get(pinvite);
					if(allis.contains(AllianceManager.getAlliance(player))){
						MessageManager.sendMessage(player, "You already invited this person!");
						return;
					}
					allis.add(AllianceManager.getAlliance(player));
					Alli.invited.replace(pinvite, allis);
				}else{
					List<Alliance> allis = new ArrayList<Alliance>();
					allis.add(AllianceManager.getAlliance(player));
					Alli.invited.put(pinvite, allis);
				}
				MessageManager.sendMessage(pinvite, "You are invited for the alliance " + AllianceManager.getAlliance(player).getName() + "!");
				MessageManager.sendMessage(player, "You have invited " + pinvite.getName() + "!");
				return;
			}else{
				MessageManager.sendMessage(player, "You are not owner/admin of a alliance!");
			}
			return;
		}
		
		if(args[1].equalsIgnoreCase("setrank") && args.length > 3){
			if(AllianceManager.playerIsInAlli(player) && (AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId()) || AllianceManager.getAlliance(player).getAdmins().contains(player.getUniqueId()))){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendMessage(player, "This player is not online!");
					return;
				}
				Player cplayer = PlayerManager.getPlayer(args[2]);
				if(!AllianceManager.getAlliance(player).getMembers().containsKey(cplayer.getUniqueId())){
					MessageManager.sendMessage(player, "This player is not in your alliance!");
					return;
				}
				HashMap<UUID,String> members = AllianceManager.getAlliance(player).getMembers();
				members.replace(cplayer.getUniqueId(), args[3]);
				AllianceManager.getAlliance(player).setMembers(members);
				MessageManager.sendMessage(player, "The rank is updated!");
				MessageManager.sendMessage(cplayer, "Your rank has been updated to " + args[3] + "!");
			}
		}
	}
}
