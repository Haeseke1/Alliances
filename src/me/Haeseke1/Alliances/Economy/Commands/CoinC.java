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
				@SuppressWarnings("deprecation")
				OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
				int amount = Integer.parseInt(args[2]);
				Coins.addPlayerCoins(player.getUniqueId(), amount);
				}catch(Exception e){
				e.printStackTrace();
				}
				return true;
			}
			String message = MessageManager.getMessage("Command_Error_Not_A_User");
			MessageManager.sendAlertMessage(message);
			return false;
		}
		Player player = (Player) sender;
		
		String wrong_arg = MessageManager.getMessage("Command_Error_Wrong_Arguments");
		
		
		if (args[0].equalsIgnoreCase("balance")) {
			if (args.length > 1) {
				if(!PlayerManager.isPlayerOnline(args[1])){
					String message = MessageManager.getMessage("Command_Error_Not_A_Online_Player");
					MessageManager.sendMessage(player, message);
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
				MessageManager.sendMessage(player, wrong_arg);
				return false;
			}
			if(!PlayerManager.isPlayerOnline(args[1])){
				String message = MessageManager.getMessage("Command_Error_Not_A_Online_Player");
				MessageManager.sendMessage(player, message);
				return false;
			}
			try {
				Coins.addPlayerCoins(args[1], Integer.parseInt(args[2]));
			} catch (Exception e) {
				MessageManager.sendMessage(player, wrong_arg);
				return false;
			}
			String message = MessageManager.getMessage("Command_Coin_Add_Answer");
			message = message.replace("%sender_coin%", "" + Coins.getPlayerCoins(sender.getName()))
			.replace("%coin%", "" + Integer.parseInt(args[2]))
			.replace("%target_coin%", "" + Coins.getPlayerCoins(args[1]))
			.replace("%sender_name%", "" + sender.getName())
			.replace("%target_name%", "" + args[1]);
			MessageManager.sendMessage(player, message);
			if(PlayerManager.isPlayerOnline(args[1])){
				message = MessageManager.getMessage("Command_Coin_Get_Answer");
				message = message.replace("%sender_coin%", "" + Coins.getPlayerCoins(sender.getName()))
				.replace("%coin%", "" + Integer.parseInt(args[2]))
				.replace("%target_coin%", "" + Coins.getPlayerCoins(args[1]))
				.replace("%sender_name%", "" + sender.getName())
				.replace("%target_name%", "" + args[1]);
				MessageManager.sendMessage(PlayerManager.getPlayer(args[1]), message);
			}
			return false;
		}
		
		if (args[0].equalsIgnoreCase("set")) {
			if (args.length < 3) {
				MessageManager.sendMessage((Player) sender, wrong_arg);
				return false;
			}
			if(!PlayerManager.isPlayerOnline(args[1])){
				String message = MessageManager.getMessage("Command_Error_Not_A_Online_Player");
				MessageManager.sendMessage(player, message);
				return false;
			}
			try {
				Coins.setPlayerCoins(args[1], Integer.parseInt(args[2]));
			} catch (Exception e) {
				MessageManager.sendMessage((Player) sender, wrong_arg);
				return false;
			}
			String message = MessageManager.getMessage("Command_Coin_Set_Answer");
			message = message.replace("%sender_coin%", "" + Coins.getPlayerCoins(sender.getName()))
			.replace("%coin%", "" + Integer.parseInt(args[2]))
			.replace("%target_coin%", "" + Coins.getPlayerCoins(args[1]))
			.replace("%sender_name%", "" + sender.getName())
			.replace("%target_name%", "" + args[1]);
			MessageManager.sendMessage(player, message);
			return false;
		}
		
		if (args[0].equalsIgnoreCase("pay")) {
			if (args.length < 3) {
				MessageManager.sendMessage((Player) sender, wrong_arg);
				return false;
			}
			if(!PlayerManager.isPlayerOnline(args[1])){
				String message = MessageManager.getMessage("Command_Error_Not_A_Online_Player");
				MessageManager.sendMessage(player, message);
				return false;
			}
			try {
				if (!Coins.removePlayerCoins(player, Integer.parseInt(args[2]))) {
					String message = MessageManager.getMessage("Command_Coin_Pay_Not_Enough_Coins");
					message = message.replace("%sender_coin%", "" + Coins.getPlayerCoins(sender.getName()))
					.replace("%coin%", "" + Integer.parseInt(args[2]))
					.replace("%target_coin%", "" + Coins.getPlayerCoins(args[1]))
					.replace("%sender_name%", "" + sender.getName())
					.replace("%target_name%", "" + args[1]);
					MessageManager.sendMessage(player, message);
					return false;
				}
				Coins.addPlayerCoins(args[1], Integer.parseInt(args[2]));
				String message = MessageManager.getMessage("Command_Coin_Pay_Answer");
				message = message.replace("%sender_coin%", "" + Coins.getPlayerCoins(sender.getName()))
				.replace("%coin%", "" + Integer.parseInt(args[2]))
				.replace("%target_coin%", "" + Coins.getPlayerCoins(args[1]))
				.replace("%sender_name%", "" + sender.getName())
				.replace("%target_name%", "" + args[1]);
				MessageManager.sendMessage(player, message);
				if(PlayerManager.isPlayerOnline(args[1])){
					message = MessageManager.getMessage("Command_Coin_Get_Answer");
					message = message.replace("%sender_coin%", "" + Coins.getPlayerCoins(sender.getName()))
					.replace("%coin%", "" + Integer.parseInt(args[2]))
					.replace("%target_coin%", "" + Coins.getPlayerCoins(args[1]))
					.replace("%sender_name%", "" + sender.getName())
					.replace("%target_name%", "" + args[1]);
					MessageManager.sendMessage(PlayerManager.getPlayer(args[1]), message);
				}
				return false;
			} catch (Exception e) {
				MessageManager.sendMessage((Player) sender, wrong_arg);
				return false;
			}
		}
		MessageManager.sendMessage((Player) sender, wrong_arg);
		return false;
	 }
	return false;
	}

}
