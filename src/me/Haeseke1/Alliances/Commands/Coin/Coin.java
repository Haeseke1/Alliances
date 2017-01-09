package me.Haeseke1.Alliances.Commands.Coin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Coin {

	public static void onCommand(CommandSender sender, String[] args) {
		if (args.length == 1) {
			sender.sendMessage(MessageManager.infoColorCode + "===== Coins =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... coin balance #Watch your balance or from other Players");
			sender.sendMessage(MessageManager.infoColorCode + "/... coin pay <Player> <Amount> #Watch your balance or from other Players");
			sender.sendMessage(MessageManager.infoColorCode + "/... coin add <Player> <Amount> #give player coins");
			return;
		}

		if (args[1].equalsIgnoreCase("balance")) {
			if (args.length > 2) {
				MessageManager.sendRemarkMessage((Player) sender, "Balance: " + Coins.getPlayerCoins(args[2]));
				return;
			} else {
				if (!(sender instanceof Player)) {
					MessageManager.sendAlertMessage((Player) sender, "You need to be a player to do this command!");
				}
				Player player = (Player) sender;
				MessageManager.sendRemarkMessage(player, "Balance: " + Coins.getPlayerCoins(player));
			}
			return;
		}
		if (args[1].equalsIgnoreCase("add")) {
			if (args.length < 4) {
				MessageManager.sendAlertMessage((Player) sender,
						"Not enough arguments! Use /alliance coin for more information!");
				return;
			} else {
				try {
					Coins.addPlayerCoins(args[2], Integer.parseInt(args[3]));
				} catch (Exception e) {
					MessageManager.sendAlertMessage((Player) sender, "Given arguments are not correct!");
					return;
				}
				MessageManager.sendRemarkMessage((Player) sender,
						"Succesfully added coins! New balance: " + Coins.getPlayerCoins(args[2]));
			}
			return;
		}
		if (args[1].equalsIgnoreCase("pay")) {
			if (args.length < 4) {
				MessageManager.sendAlertMessage((Player) sender,
						"Not enough arguments! Use /alliance coin for more information!");
				return;
			} else {
				if (!(sender instanceof Player)) {
					MessageManager.sendAlertMessage((Player) sender, "You need to be a player to do this command!");
					return;
				}
				Player player = (Player) sender;
				try {
					if (Coins.removePlayerCoins(player, Integer.parseInt(args[3]))) {
						Coins.addPlayerCoins(args[2], Integer.parseInt(args[3]));
						MessageManager.sendRemarkMessage((Player) sender,
								"Succesfully payed coins! Your new balance: " + Coins.getPlayerCoins(player));
						return;
					} else {
						MessageManager.sendAlertMessage((Player) sender, "You don't have enough coins!");
						return;
					}
				} catch (Exception e) {
					MessageManager.sendAlertMessage((Player) sender, "Given arguments are not correct!");
					return;
				}
			}
		}
		MessageManager.sendAlertMessage((Player) sender, "Wrong arguments! use /alliance coin for more information!");

	}

}
