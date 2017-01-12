package me.Haeseke1.Alliances.Commands;

import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Commands.Admin.Admin;
import me.Haeseke1.Alliances.Commands.Arena.Arena;
import me.Haeseke1.Alliances.Commands.Challenges.mainChallenges;
import me.Haeseke1.Alliances.Commands.Coin.Coin;
import me.Haeseke1.Alliances.Commands.Create.mainCreate;
import me.Haeseke1.Alliances.Commands.Join.mainJoin;
import me.Haeseke1.Alliances.Commands.Member.Member;
import me.Haeseke1.Alliances.Commands.Outpost.Outpost;
import me.Haeseke1.Alliances.Commands.Owner.Owner;
import me.Haeseke1.Alliances.Commands.Region.region;
import me.Haeseke1.Alliances.Commands.Shop.mainShop;
import me.Haeseke1.Alliances.Main.Alliance;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Alli implements CommandExecutor {
	
	public static HashMap<Player,List<Alliance>> invited = new HashMap<Player,List<Alliance>>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MessageManager.infoColorCode + "===== Alliances =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances coin #Get list of coin commands");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances challenges #Get list of challenge commands");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances create #Create a new alliance");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances outpost #Get list of outpost commands");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances join #join a alliance that invited you");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances owner #Get a list of commands for a owner");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances admin #Get a list of commands for a owner");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances shop #Get a list of commands for shops");
			return false;
		}

		if (args[0].equalsIgnoreCase("coin")) {
			Coin.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("create")){
			mainCreate.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("outpost")){
			Outpost.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("region")){
			region.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("join")){
			mainJoin.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("Owner")){
			Owner.onCommand(sender,args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("Admin")){
			Admin.onCommand(sender,args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("member")){
			Member.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("shop")){
			mainShop.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("challenges")){
			mainChallenges.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("arena")){
			Arena.onCommand(sender, args);
			return true;
		}
		
		
		MessageManager.sendAlertMessage((Player) sender, "Unknown command use /alliances for more help!");
		return false;
	}

}
