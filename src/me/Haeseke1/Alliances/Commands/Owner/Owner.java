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
import me.Haeseke1.Alliances.Town.Town;
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
			player.sendMessage(MessageManager.infoColorCode + "/... owner deposit <Amount> #add money to alliance balance");
			return;
		}
		
		if(!AllianceManager.playerIsInAlli(player) || !AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
			MessageManager.sendMessage(player, "&cYou aren't an owner of an alliance");
			return;
		}
		
		if(args[1].equalsIgnoreCase("disband")){
			Alliance alli = AllianceManager.getAlliance(player);
			String name = alli.getName();
			for(Town t : alli.getTowns()){
				Town.towns.remove(t);
			}
			Main.alliances.remove(alli);
			
			MessageManager.sendMessage(player, "&cYou've disbanded your alliance");
			MessageManager.sendBroadcast(name + "&c left the alliance battle");
			return;
		}
		
		if(args[1].equalsIgnoreCase("change") && args.length > 2){
			if(!PlayerManager.isPlayerOnline(args[2])){
				MessageManager.sendMessage(player, "&c" + args[2] + " isn't online");
				return;
			}
			Player nowner = PlayerManager.getPlayer(args[2]);
			if(!AllianceManager.getAlliance(player).getMembers().containsKey(nowner.getUniqueId())){
				MessageManager.sendMessage(player, "&cThis player is not in an alliance");
				return;
			}
			
			AllianceManager.getAlliance(player).setOwner(nowner.getUniqueId());
			AllianceManager.getAlliance(player).sendPlayersMessage("&2The alliance owner gave the ownership to &6" + nowner.getName());
			return;
		}
		
		if(args[1].equalsIgnoreCase("addadmin") && args.length > 2){
			if(!PlayerManager.isPlayerOnline(args[2])){
				MessageManager.sendMessage(player, "&" + args[2] + " isn't online");
				return;
			}
			Player nadmin = PlayerManager.getPlayer(args[2]);
			if(!AllianceManager.getAlliance(player).getMembers().containsKey(nadmin.getUniqueId())){
				MessageManager.sendMessage(player, "&" + args[2] + " isn't a member of your alliance");
				return;
			}
			
			AllianceManager.getAlliance(player).addAdmins(nadmin.getUniqueId());
			
			MessageManager.sendMessage(player, "&2You gave &6" + nadmin.getName() + " &2the admin rank!");
			MessageManager.sendMessage(nadmin, "&2You've become an admin of your alliance");
			AllianceManager.getAlliance(player).sendPlayersMessage("&6" + nadmin.getName() + "&2 is now an admin of your alliance");
			return;
		}
		
		if(args[1].equalsIgnoreCase("removeadmin") && args.length > 2){
			if(!PlayerManager.isPlayerOnline(args[1])){
				MessageManager.sendMessage(player, "&" + args[1] + " isn't online");
				return;
			}
			Player nadmin = PlayerManager.getPlayer(args[2]);
			if(!AllianceManager.getAlliance(player).getMembers().containsKey(nadmin.getUniqueId())){
				MessageManager.sendMessage(player, "&" + args[2] + " isn't a member of your alliance");
				return;
			}
			if(!AllianceManager.getAlliance(player).getAdmins().contains(nadmin.getUniqueId())){
				MessageManager.sendMessage(player, "&6" + args[2] + " isn't an admin");
				return;
			}
			
			List<UUID> admins = AllianceManager.getAlliance(player).getAdmins();
			admins.remove(nadmin.getUniqueId());
			AllianceManager.getAlliance(player).setAdmins(admins);
			MessageManager.sendMessage(player, "&2You've removed the admin rank from &6" + nadmin.getName());
			MessageManager.sendMessage(nadmin, "&2The owner removed the admin rank from you");
			AllianceManager.getAlliance(player).sendPlayersMessage("&2You've removed the admin rank from &6" + nadmin.getName());
			return;
		}
		
		if(args[1].equalsIgnoreCase("invite") && args.length > 2){
			if(!PlayerManager.isPlayerOnline(args[2])){
				MessageManager.sendMessage(player, "&" + args[1] + " isn't online");
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
			if(!PlayerManager.isPlayerOnline(args[2])){
				MessageManager.sendMessage(player, "&" + args[1] + " isn't online");
				return;
			}
			Player cplayer = PlayerManager.getPlayer(args[2]);
			if(!AllianceManager.getAlliance(player).getMembers().containsKey(cplayer.getUniqueId())){
				MessageManager.sendMessage(player, "&" + args[2] + " isn't a member of your alliance");
				return;
			}
			HashMap<UUID,String> members = AllianceManager.getAlliance(player).getMembers();
			members.replace(cplayer.getUniqueId(), args[3]);
			MessageManager.sendMessage(player, "&2You've set the rank of &6" + cplayer.getName() + "&2 to &6" + args[3]);
			MessageManager.sendMessage(cplayer, "&2A admin of your alliance changed your rank to &6" + args[3]);
			return;
		}
		
		if(args[1].equalsIgnoreCase("getrewards")){
			Alliance alli = AllianceManager.getAlliance(player);
			if(alli.claimReward(player)){
				MessageManager.sendMessage(player, "&2Your alliance retrieved the rewards");
			}else{
				MessageManager.sendMessage(player, "&cTheir aren't any pending rewards");
			}
			return;
		}
		
		if(args[1].equalsIgnoreCase("deposit") && args.length > 2){
			Alliance alli = AllianceManager.getAlliance(player);
			try{
				int money = Integer.parseInt(args[2]);
				if(Coins.removePlayerCoins(player, money)){
					Coins.addAllianceCoins(alli, money);
					alli.addScore(money);
					MessageManager.sendMessage(player, "&2You supported your alliance with &6" + money + "&2 coins!");
				}else{
					MessageManager.sendMessage(player, "&cYou don't have enough money");
				}
			}catch(Exception e){
				e.printStackTrace();
				MessageManager.sendInfoMessage("not a number");
				MessageManager.sendMessage(player, "&cWrong argument do: /alli");
			}
			return;
		}
		MessageManager.sendMessage(player, "&cWrong argument do: /alli");
	}
}
