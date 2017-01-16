package me.Haeseke1.Alliances.Economy.Commands;

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
			sender.sendMessage(MessageManager.infoColorCode + "/coin balance #Watch your balance or from other Players");
			sender.sendMessage(MessageManager.infoColorCode + "/coin pay <Player> <Amount> #Give another player coins");
			sender.sendMessage(MessageManager.infoColorCode + "/coin add <Player> <Amount> #give player coins");
			sender.sendMessage(MessageManager.infoColorCode + "/coin set <Player> <Amount> #Set a player's coins");
			return false;
		}
		
		if (!(sender instanceof Player)) {
			String message = MessageManager.getMessage("Command_Error_Not_A_User");
			MessageManager.sendAlertMessage(message);
		}
		Player player = (Player) sender;
		
		String wrong_arg = MessageManager.getMessage("Command_Error_Wrong_Arguments");
		
		
		if (args[0].equalsIgnoreCase("balance")) {
			if (args.length > 1) {
				String message = MessageManager.getMessage("Command_Coin_Balance_Other_Answer");
				message = message.replace("%sender_coin%", "" + Coins.getPlayerCoins(sender.getName()))
				.replace("%coin%", "")
				.replace("%target_coin%", "" + Coins.getPlayerCoins(args[1]))
				.replace("%sender_name%", "" + sender.getName())
				.replace("%target_name%", "" + args[1]);
				MessageManager.sendMessage(player, message);
				return false;
			} else {
				String message = MessageManager.getMessage("command_coin_balance_answer");
				message = message.replace("%sender_coin%", "" + Coins.getPlayerCoins(sender.getName()))
				.replace("%coin%", "")
				.replace("%target_coin%", "")
				.replace("%sender_name%", "" + sender.getName())
				.replace("%target_name%", "");
				MessageManager.sendMessage(player, message);
			}
			return false;
		}
		
		
		if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 3) {
				MessageManager.sendMessage(player, wrong_arg);
				return false;
			} else {
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
			}
			return false;
		}
		
		if (args[0].equalsIgnoreCase("set")) {
			if (args.length < 3) {
				MessageManager.sendMessage((Player) sender, wrong_arg);
				return false;
			} else {
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
			}
			return false;
		}
		
		if (args[0].equalsIgnoreCase("pay")) {
			if (args.length < 3) {
				MessageManager.sendMessage((Player) sender, wrong_arg);
				return false;
			} else {
				try {
					if (Coins.removePlayerCoins(player, Integer.parseInt(args[2]))) {
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
					} else {
						String message = MessageManager.getMessage("Command_Coin_Pay_Not_Enough_Coins");
						message = message.replace("%sender_coin%", "" + Coins.getPlayerCoins(sender.getName()))
						.replace("%coin%", "" + Integer.parseInt(args[2]))
						.replace("%target_coin%", "" + Coins.getPlayerCoins(args[1]))
						.replace("%sender_name%", "" + sender.getName())
						.replace("%target_name%", "" + args[1]);
						MessageManager.sendMessage(player, message);
						return false;
					}
				} catch (Exception e) {
					MessageManager.sendMessage((Player) sender, wrong_arg);
					return false;
				}
			}
		}
		MessageManager.sendMessage((Player) sender, wrong_arg);
		return false;
	}

}
