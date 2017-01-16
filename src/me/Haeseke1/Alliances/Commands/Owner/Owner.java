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

	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			return;
		}
		if(args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Owner =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... owner invite <Player> #Invite players to your alliance");
			sender.sendMessage(MessageManager.infoColorCode + "/... owner disband #Disband your alliance");
			sender.sendMessage(MessageManager.infoColorCode + "/... owner change <Player> #Change owner");
			sender.sendMessage(MessageManager.infoColorCode + "/... owner setrank <Player> <Name> #Set rank of a player");
			sender.sendMessage(MessageManager.infoColorCode + "/... owner addadmin <Player> #Set rank of a player");
			sender.sendMessage(MessageManager.infoColorCode + "/... owner removeadmin <Player> #Set rank of a player");
			return;
		}
		
		
		if(args[1].equalsIgnoreCase("disband")){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return ;
			}
			Player player = (Player) sender;
			if(AllianceManager.playerIsInAlli(player) && AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
				Alliance alli = AllianceManager.getAlliance(player);
				String name = alli.getName();
				Main.alliances.remove(alli);
				alli = null;
				MessageManager.sendRemarkMessage(player, "You disbanded your alliance!");
				MessageManager.sendInfoBroadcast("Alliance " + name + "is disbanded!");
			}else{
				MessageManager.sendAlertMessage(player, "You are not owner of a alliance!");
			}
			return;
		}
		
		if(args[1].equalsIgnoreCase("change") && args.length > 2){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return;
			}
			Player player = (Player) sender;
			if(AllianceManager.playerIsInAlli(player) && (AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId()) || AllianceManager.getAlliance(player).getAdmins().contains(player.getUniqueId()))){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendAlertMessage(player, "This player is not online!");
					return;
				}
				Player nowner = PlayerManager.getPlayer(args[2]);
				if(!AllianceManager.getAlliance(player).getMembers().containsKey(nowner.getUniqueId())){
					MessageManager.sendAlertMessage(player, "This player is not in your alliance!");
					return;
				}
				AllianceManager.getAlliance(player).setOwner(nowner.getUniqueId());
				MessageManager.sendRemarkMessage(player, "Changed ownership succesfully to " + nowner.getName() + "!");
				MessageManager.sendInfoMessage(nowner, "You are now the owner of " + AllianceManager.getAlliance(player).getName() + "!");
				AllianceManager.getAlliance(player).sendPlayersInfoMessage("The owner of this alliance is changed to " + nowner.getName() + "!");
				return;
			}else{
				MessageManager.sendAlertMessage(player, "You are not owner/admin of a alliance!");
			}
			return;
		}
		
		if(args[1].equalsIgnoreCase("addadmin") && args.length > 2){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return;
			}
			Player player = (Player) sender;
			if(AllianceManager.playerIsInAlli(player) && (AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId()) || AllianceManager.getAlliance(player).getAdmins().contains(player.getUniqueId()))){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendAlertMessage(player, "This player is not online!");
					return;
				}
				Player nadmin = PlayerManager.getPlayer(args[2]);
				if(!AllianceManager.getAlliance(player).getMembers().containsKey(nadmin.getUniqueId())){
					MessageManager.sendAlertMessage(player, "This player is not in your alliance!");
					return;
				}
				AllianceManager.getAlliance(player).addAdmins(nadmin.getUniqueId());
				MessageManager.sendRemarkMessage(player, "You promoted " + nadmin.getName() + " to admin!");
				MessageManager.sendInfoMessage(nadmin, "You are now a admin of " + AllianceManager.getAlliance(player).getName() + "!");
				AllianceManager.getAlliance(player).sendPlayersInfoMessage(nadmin.getName() + " is promoted to admin!");
				return;
			}else{
				MessageManager.sendAlertMessage(player, "You are not owner/admin of a alliance!");
			}
			return;
		}
		
		if(args[1].equalsIgnoreCase("removeadmin") && args.length > 2){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return;
			}
			Player player = (Player) sender;
			if(AllianceManager.playerIsInAlli(player) && (AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId()) || AllianceManager.getAlliance(player).getAdmins().contains(player.getUniqueId()))){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendAlertMessage(player, "This player is not online!");
					return;
				}
				Player nadmin = PlayerManager.getPlayer(args[2]);
				if(!AllianceManager.getAlliance(player).getAdmins().contains(nadmin.getUniqueId())){
					MessageManager.sendAlertMessage(player, "This player is not a admin in your alliance!");
					return;
				}
				List<UUID> admins = AllianceManager.getAlliance(player).getAdmins();
				admins.remove(nadmin.getUniqueId());
				AllianceManager.getAlliance(player).setAdmins(admins);
				MessageManager.sendRemarkMessage(player, "You removed " + nadmin.getName() + " from admin!");
				MessageManager.sendInfoMessage(nadmin, "You are no longer a admin of " + AllianceManager.getAlliance(player).getName() + "!");
				AllianceManager.getAlliance(player).sendPlayersInfoMessage(nadmin.getName() + " is no longer a admin!");
				return;
			}else{
				MessageManager.sendAlertMessage(player, "You are not owner/admin of a alliance!");
			}
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
					MessageManager.sendAlertMessage(player, "This player is not online!");
					return;
				}
				Player pinvite = PlayerManager.getPlayer(args[2]);
				if(AllianceManager.getAlliance(player).getMembers().containsKey(pinvite.getUniqueId())){
					MessageManager.sendAlertMessage(player, "This person is already in your alliance!");
					return;
				}
				if(Alli.invited.containsKey(pinvite)){
					List<Alliance> allis = Alli.invited.get(pinvite);
					if(allis.contains(AllianceManager.getAlliance(player))){
						MessageManager.sendAlertMessage(player, "You already invited this person!");
						return;
					}
					allis.add(AllianceManager.getAlliance(player));
					Alli.invited.replace(pinvite, allis);
				}else{
					List<Alliance> allis = new ArrayList<Alliance>();
					allis.add(AllianceManager.getAlliance(player));
					Alli.invited.put(pinvite, allis);
				}
				MessageManager.sendInfoMessage(pinvite, "You are invited for the alliance " + AllianceManager.getAlliance(player).getName() + "!");
				MessageManager.sendRemarkMessage(player, "You have invited " + pinvite.getName() + "!");
				return;
			}else{
				MessageManager.sendAlertMessage(player, "You are not owner/admin of a alliance!");
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
					MessageManager.sendAlertMessage(player, "This player is not online!");
					return;
				}
				Player cplayer = PlayerManager.getPlayer(args[2]);
				if(!AllianceManager.getAlliance(player).getMembers().containsKey(cplayer.getUniqueId())){
					MessageManager.sendAlertMessage(player, "This player is not in your alliance!");
					return;
				}
				HashMap<UUID,String> members = AllianceManager.getAlliance(player).getMembers();
				members.replace(cplayer.getUniqueId(), args[3]);
				AllianceManager.getAlliance(player).setMembers(members);
				MessageManager.sendRemarkMessage(player, "The rank is updated!");
				MessageManager.sendInfoMessage(cplayer, "Your rank has been updated to " + args[3] + "!");
			}
		}
	}
}
