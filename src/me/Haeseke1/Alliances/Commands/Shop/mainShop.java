package me.Haeseke1.Alliances.Commands.Shop;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Shop.SItem;
import me.Haeseke1.Alliances.Shop.Shop;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class mainShop {
	
	public static void onCommand(CommandSender sender, String[] args) {
		if (args.length == 1){
			sender.sendMessage(MessageManager.infoColorCode + "===== Shop =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/... shop create <Name> #Create a new shop");
			sender.sendMessage(MessageManager.infoColorCode + "/... shop additem <Name> <canBuy(true/false)> <canSell(true/false)> <buyPrice> <sellPrice> #add item in hand to shop");
			sender.sendMessage(MessageManager.infoColorCode + "/... shop addlocation <Name> #add location vendor to shop");
			return;
		}
		
		if(args[1].equalsIgnoreCase("create") && args.length >= 3){
			new Shop(args[2], new ArrayList<SItem>(), new ArrayList<Location>());
			MessageManager.sendRemarkMessage((Player) sender, "Shop created with name: " + args[2]);
			return;
		}
		
		if(args[1].equalsIgnoreCase("additem") && args.length >= 6){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return ;
			}
			Player player = (Player) sender;
			String name = args[2];
			Shop s = null;
			for(Shop shop : Shop.shops){
				if(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', shop.name)).equalsIgnoreCase(name)){
					s = shop;
				}
			}
			if(s == null){
				MessageManager.sendAlertMessage(player, "This shop does not exist!");
				return;
			}
			boolean buy = Boolean.parseBoolean(args[3]);
			boolean sell = Boolean.parseBoolean(args[4]);
			int buyV = 0;
			int sellV = 0;
			try{
				buyV = Integer.parseInt(args[5]);
				sellV = Integer.parseInt(args[6]);
			}catch(Exception e){
				MessageManager.sendAlertMessage(player, "Wrong arguments, not a number!");
				return;
			}
			SItem sitem = new SItem(player.getItemInHand(), buy, sell, buyV, sellV);
			s.addSItem(sitem);
			MessageManager.sendRemarkMessage((Player) sender, "Item added to the shop");
			return;
		}
		
		if(args[1].equalsIgnoreCase("addlocation") && args.length >= 3){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return ;
			}
			Player player = (Player) sender;
			Shop s = null;
			for(Shop shop : Shop.shops){
				if(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', shop.name)).equalsIgnoreCase(args[2])){
					s = shop;
				}
			}
			if(s == null){
				MessageManager.sendAlertMessage(player, "This shop does not exist!");
				return;
			}
			s.addVendor(player.getLocation());
			MessageManager.sendRemarkMessage((Player) sender, "added location to shop");
			return;
		}
	}
}
