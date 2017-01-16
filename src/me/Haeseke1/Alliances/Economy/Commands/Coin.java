package me.Haeseke1.Alliances.Economy.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.PlayerManager;

public class Coin implements CommandExecutor{

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

		if (args[0].equalsIgnoreCase("balance")) {
			if (args.length > 1) {
				MessageManager.sendMessage((Player) sender, "Balance: " + Coins.getPlayerCoins(args[1]));
				return false;
			} else {
				if (!(sender instanceof Player)) {
					MessageManager.sendMessage((Player) sender, "You need to be a player to do this command!");
				}
				Player player = (Player) sender;
				MessageManager.sendMessage(player, "Balance: " + Coins.getPlayerCoins(player));
			}
			return false;
		}
		
		
		if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 3) {
				MessageManager.sendMessage((Player) sender, "Not enough arguments! Use /alliance coin for more information!");
				return false;
			} else {
				try {
					Coins.addPlayerCoins(args[1], Integer.parseInt(args[2]));
				} catch (Exception e) {
					MessageManager.sendMessage((Player) sender, "Given arguments are not correct!");
					return false;
				}
				MessageManager.sendMessage((Player) sender,
						"Succesfully added coins! New balance: " + Coins.getPlayerCoins(args[1]));
			}
			return false;
		}
		
		if (args[0].equalsIgnoreCase("set")) {
			if (args.length < 3) {
				MessageManager.sendMessage((Player) sender, "Not enough arguments! Use /coin for more information!");
				return false;
			} else {
				try {
					Coins.setPlayerCoins(args[1], Integer.parseInt(args[2]));
				} catch (Exception e) {
					MessageManager.sendMessage((Player) sender, "Given arguments are not correct!");
					return false;
				}
				MessageManager.sendMessage((Player) sender,
						"Succesfully added coins! New balance: " + Coins.getPlayerCoins(args[1]));
			}
			return false;
		}
		
		if (args[0].equalsIgnoreCase("pay")) {
			if (args.length < 3) {
				MessageManager.sendMessage((Player) sender, "Not enough arguments! Use /alliance coin for more information!");
				return false;
			} else {
				if (!(sender instanceof Player)) {
					MessageManager.sendMessage((Player) sender, "You need to be a player to do this command!");
					return false;
				}
				Player player = (Player) sender;
				try {
					if (Coins.removePlayerCoins(player, Integer.parseInt(args[2]))) {
						Coins.addPlayerCoins(args[1], Integer.parseInt(args[2]));
						MessageManager.sendMessage((Player) sender, "Succesfully payed coins! Your new balance: " + Coins.getPlayerCoins(player));
						if(PlayerManager.isPlayerOnline(args[1])){
							MessageManager.sendMessage(PlayerManager.getPlayer(args[1]),  sender.getName() + " gave you " + Integer.parseInt(args[2]) + " coins!");
						}
						return false;
					} else {
						MessageManager.sendMessage((Player) sender, "You don't have enough coins!");
						return false;
					}
				} catch (Exception e) {
					MessageManager.sendMessage((Player) sender, "Given arguments are not correct!");
					return false;
				}
			}
		}
		MessageManager.sendMessage((Player) sender, "Wrong arguments! use /coin for more information!");
		return false;
	}

}
