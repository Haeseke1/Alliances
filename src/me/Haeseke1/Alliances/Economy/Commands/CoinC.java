package me.Haeseke1.Alliances.Economy.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.PlayerManager;

public class CoinC implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MessageManager.infoColorCode + "===== Coins =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/coin balance [Player] #Watch your balance or from other Players");
			if(sender.hasPermission("Alliances.coins.*")){
			sender.sendMessage(MessageManager.infoColorCode + "/coin pay <Player> <Amount> #Give another player coins");
			sender.sendMessage(MessageManager.infoColorCode + "/coin add <Player> <Amount> #give player coins");
			sender.sendMessage(MessageManager.infoColorCode + "/coin set <Player> <Amount> #Set a player's coins");
			}
			return false;
		}
		
		if (!(sender instanceof Player)) {
			if(args[0].equalsIgnoreCase("add") && args.length >= 3){
				try{
				OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
				int amount = Integer.parseInt(args[2]);
				Coins.addPlayerCoins(player.getUniqueId(), amount);
				}catch(Exception e){
				e.printStackTrace();
				}
				return true;
			}
			MessageManager.sendAlertMessage("&6" + args[1] + "&c isn't online");
			return false;
		}
		Player player = (Player) sender;
		
		MessageManager.sendMessage(player, "&cWrong argument do: /coins");
		
		
		if (args[0].equalsIgnoreCase("balance")) {
			if (args.length > 1) {
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendAlertMessage("&6" + args[1] + "&c isn't online");
					return false;
				}
				MessageManager.sendMessage(player, "&6" + sender.getName() + "&8 has " + Coins.getPlayerCoins(sender.getName()) + " &8coins");
			} else {
				MessageManager.sendMessage(player, "&8You've &6" + Coins.getPlayerCoins(player) + "&8 coins");
			}
			return false;
		}
		
	if(player.hasPermission("Alliances.coins.*")){
		if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 3) {
				MessageManager.sendMessage(player, "&cWrong argument do: /coins");
				return false;
			}
			if(!PlayerManager.isPlayerOnline(args[1])){
				MessageManager.sendAlertMessage("&6" + args[1] + "&c isn't online");
				return false;
			}
			try {
				Coins.addPlayerCoins(args[1], Integer.parseInt(args[2]));
			} catch (Exception e) {
				MessageManager.sendMessage(player, "&cWrong argument do: /coins");
				return false;
			}
			MessageManager.sendMessage(player, "&2Successfully added &6" + args[2] + " &2coins to &6" + PlayerManager.getPlayer(args[1]).getName() + "&2's bank account");
			if(PlayerManager.isPlayerOnline(args[1])){
				MessageManager.sendMessage(Bukkit.getPlayer(args[1]), "&2You've received &6" + args[2] + " &2coins");
			}
			return false;
		}
		
		if (args[0].equalsIgnoreCase("set")) {
			if (args.length < 3) {
				MessageManager.sendMessage(player, "&cWrong argument do: /coins");
				return false;
			}
			if(!PlayerManager.isPlayerOnline(args[1])){
				MessageManager.sendAlertMessage("&6" + args[1] + "&c isn't online");
				return false;
			}
			try {
				Coins.setPlayerCoins(args[1], Integer.parseInt(args[2]));
			} catch (Exception e) {
				MessageManager.sendMessage(player, "&cWrong argument do: /coins");
				return false;
			}
			MessageManager.sendAlertMessage("&2Successfully setted &6" + Bukkit.getPlayer(args[1]).getName() + "&2's bank account to &6" + args[2] + "&2 coins");
			return false;
		}
		
		if (args[0].equalsIgnoreCase("pay")) {
			if (args.length < 3) {
				MessageManager.sendMessage(player, "&cWrong argument do: /coins");
				return false;
			}
			if(!PlayerManager.isPlayerOnline(args[1])){
				MessageManager.sendMessage(player,"&6" + args[1] + "&c isn't online");
				return false;
			}
			try {
				if (!Coins.removePlayerCoins(player, Integer.parseInt(args[2]))) {
					MessageManager.sendMessage(player,"&cThis player doesn't have enough coins!");
					return false;
				}
				Coins.addPlayerCoins(args[1], Integer.parseInt(args[2]));
				MessageManager.sendAlertMessage("&2You've paid &6" + args[1]);
				if(PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(Bukkit.getPlayer(args[1]),"&cThis player doesn't have enough coins!");
				}
				return false;
			} catch (Exception e) {
				MessageManager.sendMessage(player, "&cWrong argument do: /coins");
				return false;
			}
		}
		MessageManager.sendMessage(player, "&cWrong argument do: /coins");
		return false;
	 }
	return false;
	}

}
