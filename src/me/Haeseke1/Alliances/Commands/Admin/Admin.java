package me.Haeseke1.Alliances.Commands.Admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Commands.Alli;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.PlayerManager;

public class Admin {

	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			return;
		}
		if(args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Admin =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... admin invite <Player> #Invite players to your alliance");
			sender.sendMessage(MessageManager.infoColorCode + "/... admin setrank <Player> <Name> #Set rank of a player");
			return;
		}
		
		if(args[1].equalsIgnoreCase("invite") && args.length > 2){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return;
			}
			Player player = (Player) sender;
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
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return;
			}
			Player player = (Player) sender;
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
