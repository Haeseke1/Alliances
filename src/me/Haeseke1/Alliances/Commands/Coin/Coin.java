package me.Haeseke1.Alliances.Commands.Coin;

import org.bukkit.command.CommandSender;

import me.Haeseke1.Alliances.Utils.MessageManager;

public class Coin {
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Coins =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... coin balance #Watch your balance");
			sender.sendMessage(MessageManager.infoColorCode + "/... coin add <Player> <Amount> #give player coins");
			return ;
		}
		if(args[1].equalsIgnoreCase("balance")){
			
			return;
		}
		if(args[1].equalsIgnoreCase("add")){
			
			return;
		}
	}

}
