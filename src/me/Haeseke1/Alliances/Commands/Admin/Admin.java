package me.Haeseke1.Alliances.Commands.Admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Commands.Alli;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.PlayerManager;

public class Admin {

	public static void onCommand(Player player, String[] args) {
		if(args.length == 1){
			player.sendMessage(MessageManager.infoColorCode + "===== Admin =====");
			player.sendMessage(MessageManager.infoColorCode + "Commands:");
			player.sendMessage(MessageManager.infoColorCode + "/... admin invite <Player> #Invite players to your alliance");
			player.sendMessage(MessageManager.infoColorCode + "/... admin setrank <Player> <Name> #Set rank of a player");
			player.sendMessage(MessageManager.infoColorCode + "/... admin getrewards #Take items your alliance was rewarded!");
			player.sendMessage(MessageManager.infoColorCode + "/... admin leave #Leave the alliance");
			player.sendMessage(MessageManager.infoColorCode + "/... admin deposit <Amount> #add money to alliance balance");
			return;
		}
		
		if(!AllianceManager.playerIsInAlli(player) || !AllianceManager.getAlliance(player).getAdmins().contains(player.getUniqueId())){
			MessageManager.sendMessage(player, "&cYou're not the admin in your alliance");
			return;
		}
		
		if(args[1].equalsIgnoreCase("invite") && args.length > 2){
			if(!PlayerManager.isPlayerOnline(args[2])){
				MessageManager.sendMessage(player, "&6" + args[2] +" &6isn't online");
				return;
			}
			Player pinvite = PlayerManager.getPlayer(args[2]);
			if(AllianceManager.getAlliance(player).getMembers().containsKey(pinvite.getUniqueId())){
				MessageManager.sendMessage(player, "&6" + args[2] + "&c is already a member of your alliance");
				return;
			}
			if(Alli.invited.containsKey(pinvite)){
				List<Alliance> allis = Alli.invited.get(pinvite);
				if(allis.contains(AllianceManager.getAlliance(player))){
					MessageManager.sendMessage(player, "&6" + args[2] + "&c is already invited to your alliance");
					return;
				}
				allis.add(AllianceManager.getAlliance(player));
				Alli.invited.replace(pinvite, allis);
			}else{
				List<Alliance> allis = new ArrayList<Alliance>();
				allis.add(AllianceManager.getAlliance(player));
				Alli.invited.put(pinvite, allis);
			}
			MessageManager.sendMessage(pinvite, "&2You received an invite from an alliance");
			MessageManager.sendMessage(player, "&2You've sent a invite to &6" + args[2]);
			return;
		}
		
		if(args[1].equalsIgnoreCase("setrank") && args.length > 3){
			if(!PlayerManager.isPlayerOnline(args[1])){
				MessageManager.sendMessage(player, "&6" + args[2] +" &6isn't online");
				return;
			}
			Player cplayer = PlayerManager.getPlayer(args[2]);
			if(!AllianceManager.getAlliance(player).getMembers().containsKey(cplayer.getUniqueId())){
				MessageManager.sendMessage(player, "&6" + args[2] +" &6isn't in your alliance");
				return;
			}
			HashMap<UUID,String> members = AllianceManager.getAlliance(player).getMembers();
			members.replace(cplayer.getUniqueId(), args[3]);
			AllianceManager.getAlliance(player).setMembers(members);
			MessageManager.sendMessage(player, "&2You've set the rank of &6" + cplayer.getName() + "&2 to &6" + args[3]);
			MessageManager.sendMessage(cplayer, "&2A admin of your alliance changed your rank to &6" + args[3]);
			return;
		}
		
		if(args[1].equalsIgnoreCase("leave")){
			if(AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
				MessageManager.sendMessage(player, "&cYou can't leave your man alone");
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
			MessageManager.sendMessage(player, "&2You left your alliance behind you!");
			alli.sendPlayersMessage("&6" + player.getName() + "&c left your alliance!");
			return;
		}
		
		if(args[1].equalsIgnoreCase("deposit") && args.length > 2){
			Alliance alli = AllianceManager.getAlliance(player);
			try{
				int money = Integer.parseInt(args[2]);
				if(Coins.removePlayerCoins(player, money)){
					Coins.addAllianceCoins(alli, money);
					MessageManager.sendMessage(player, "&2You supported your alliance with &6" + money + "&2 coins!");
				}else{
					MessageManager.sendMessage(player, "&cYou don't have enough money");
				}
			}catch(Exception e){
				MessageManager.sendMessage(player, "&cWrong argument do: /alli to see all the commands");
			}
			return;
		}
		
		MessageManager.sendMessage(player, "&cWrong argument do: /alli to see all the commands");
	}
}
