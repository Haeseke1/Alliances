package me.Haeseke1.Alliances.Commands.Member;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Member {
	
	
	public static void onCommand(Player player, String[] args) {
		if(args.length == 1){
			player.sendMessage(MessageManager.infoColorCode + "===== Member =====");
			player.sendMessage(MessageManager.infoColorCode + "Commands:");
			player.sendMessage(MessageManager.infoColorCode + "/... member leave #Leave the alliance");
			player.sendMessage(MessageManager.infoColorCode + "/... member deposit <Amount> #add money to alliance balance");
			return;
		}
		
		if(!AllianceManager.playerIsInAlli(player)){
			MessageManager.sendMessage(player, "&cYou aren't in an alliance");
			return;
		}
		
		
		if(args[1].equalsIgnoreCase("leave")){
			if(AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
				MessageManager.sendMessage(player, "&cYou can't leave an alliance when you're the owner");
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
			MessageManager.sendMessage(player, "&cYou've left the alliance");
			alli.sendPlayersMessage("&6" + player.getName() + "&c left the alliance");
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
				MessageManager.sendMessage(player, "&cWrong argument do: /alli");
			}
			return;
		}
		MessageManager.sendMessage(player, "&cWrong argument do: /alli");
	}

}
