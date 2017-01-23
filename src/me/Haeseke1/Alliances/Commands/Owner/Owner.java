package me.Haeseke1.Alliances.Commands.Owner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Commands.Alli;
import me.Haeseke1.Alliances.Economy.Coins;
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
			player.sendMessage(MessageManager.infoColorCode + "/... owner getrewards #Take items your alliance was rewarded!");
			player.sendMessage(MessageManager.infoColorCode + "/... admin deposit <Amount> #add money to alliance balance");
			return;
		}
		
		if(!AllianceManager.playerIsInAlli(player) || !AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
			String message = MessageManager.getMessage("Command_Error_Not_A_Owner");
			MessageManager.sendMessage(player, message);
			return;
		}
		String wrong_arg = MessageManager.getMessage("Command_Error_Wrong_Arguments");
		
		if(args[1].equalsIgnoreCase("disband")){
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
			if(!PlayerManager.isPlayerOnline(args[2])){
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
			if(!PlayerManager.isPlayerOnline(args[1])){
				String message = MessageManager.getMessage("Command_Error_Not_A_Online_Player");
				MessageManager.sendMessage(player, message);
				return;
			}
			Player pinvite = PlayerManager.getPlayer(args[2]);
			if(AllianceManager.getAlliance(player).getMembers().containsKey(pinvite.getUniqueId())){
				String message = MessageManager.getMessage("Command_Alliance_Owner_Invite_Already_In_Alliance");
				message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
						.replace("%player_name%", pinvite.getName());
				MessageManager.sendMessage(player, message);
				return;
			}
			if(Alli.invited.containsKey(pinvite)){
				List<Alliance> allis = Alli.invited.get(pinvite);
				if(allis.contains(AllianceManager.getAlliance(player))){
					String message = MessageManager.getMessage("Command_Alliance_Owner_Invite_Already_Invited_To_Alliance");
					message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
							.replace("%player_name%", pinvite.getName());
					MessageManager.sendMessage(player, message);
					return;
				}
				allis.add(AllianceManager.getAlliance(player));
				Alli.invited.replace(pinvite, allis);
			}else{
				List<Alliance> allis = new ArrayList<Alliance>();
				allis.add(AllianceManager.getAlliance(player));
				Alli.invited.put(pinvite, allis);
			}
			String message = MessageManager.getMessage("Command_Alliance_Owner_Invite_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", pinvite.getName());
			MessageManager.sendMessage(player, message);
			message = MessageManager.getMessage("Command_Alliance_Owner_Invited_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", pinvite.getName());
			MessageManager.sendMessage(pinvite, message);
			return;
		}
		
		if(args[1].equalsIgnoreCase("setrank") && args.length > 3){
			if(!PlayerManager.isPlayerOnline(args[1])){
				String message = MessageManager.getMessage("Command_Error_Not_A_Online_Player");
				MessageManager.sendMessage(player, message);
				return;
			}
			Player cplayer = PlayerManager.getPlayer(args[2]);
			if(!AllianceManager.getAlliance(player).getMembers().containsKey(cplayer.getUniqueId())){
				String message = MessageManager.getMessage("Command_Error_Not_In_Your_Alliance");
				MessageManager.sendMessage(player, message);
				return;
			}
			HashMap<UUID,String> members = AllianceManager.getAlliance(player).getMembers();
			members.replace(cplayer.getUniqueId(), args[3]);
			AllianceManager.getAlliance(player).setMembers(members);
			String message = MessageManager.getMessage("Command_Alliance_Owner_SetRank_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", cplayer.getName());
			MessageManager.sendMessage(player, message);
			message = MessageManager.getMessage("Command_Alliance_Owner_SetRank_Changed_Answer");
			message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
					.replace("%player_name%", cplayer.getName());
			MessageManager.sendMessage(cplayer, message);
			return;
		}
		
		if(args[1].equalsIgnoreCase("getrewards")){
			Alliance alli = AllianceManager.getAlliance(player);
			if(alli.claimReward(player)){
				String message = MessageManager.getMessage("Command_Alliance_getrewards_Answer_All");
				message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName());
				MessageManager.sendMessage(player, message);
			}else{
				String message = MessageManager.getMessage("Command_Alliance_getrewards_Answer_Not_All");
				message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName());
				MessageManager.sendMessage(player, message);
			}
			return;
		}
		
		if(args[1].equalsIgnoreCase("deposit") && args.length > 2){
			Alliance alli = AllianceManager.getAlliance(player);
			try{
				int money = Integer.parseInt(args[2]);
				if(Coins.removePlayerCoins(player, money)){
					Coins.addAllianceCoins(alli, money);
					String message = MessageManager.getMessage("Command_Alliance_Member_Deposit_Answer");
					message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
							.replace("%amount%", money + "");
					MessageManager.sendMessage(player, message);
				}else{
					String message = MessageManager.getMessage("Command_Alliance_Member_Deposit_Not_Enough_Money");
					message = message.replace("%alli_name%", AllianceManager.getAlliance(player).getName())
							.replace("%amount%", money + "");
					MessageManager.sendMessage(player, message);
				}
			}catch(Exception e){
				e.printStackTrace();
				MessageManager.sendInfoMessage("not a number");
				MessageManager.sendMessage(player, wrong_arg);
			}
			return;
		}
		
		MessageManager.sendMessage(player, wrong_arg);
	}
}
