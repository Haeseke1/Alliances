package me.Haeseke1.Alliances.Shop.Commands;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Shop.SItem;
import me.Haeseke1.Alliances.Shop.Shop;
import me.Haeseke1.Alliances.Shop.ShopManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class ShopC implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MessageManager.infoColorCode + "===== Shop =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/shop create <Name> #Create a new shop");
			sender.sendMessage(MessageManager.infoColorCode
					+ "/shop additem <Name> <canBuy(true/false)> <canSell(true/false)> <buyPrice> <sellPrice> #add item in hand to shop");
			sender.sendMessage(MessageManager.infoColorCode + "/shop addlocation <Name> #add location vendor to shop");
			return false;
		}

		if (!(sender instanceof Player)) {
			String message = "This command needs to be executed by a player";
			MessageManager.sendAlertMessage(message);
			return false;
		}
		Player player = (Player) sender;

		String wrong_arg = "&cWrong argument do: /shop";
		if (player.hasPermission("Alliances.shop.*")) {
			if (args[0].equalsIgnoreCase("create") && args.length >= 2) {
				if (ShopManager.shopExist(args[1])) {
					String message = "&6%shopname%&6&c does already exists";
					message = message.replace("%shop_name%", args[1]);
					MessageManager.sendMessage((Player) sender, message);
					return false;
				}
				new Shop(args[1], new ArrayList<SItem>(), new ArrayList<Location>());
				String message = "&2You've successfully created a shop: &6%shopname%";
				message = message.replace("%shop_name%", args[1]);
				MessageManager.sendMessage((Player) sender, message);
				return false;
			}

			if (args[0].equalsIgnoreCase("additem") && args.length >= 5) {
				String name = args[1];
				Shop s = ShopManager.getShop(name);
				if (s == null) {
					MessageManager.sendMessage(player, wrong_arg);
					return false;
				}
				boolean buy = Boolean.parseBoolean(args[2]);
				boolean sell = Boolean.parseBoolean(args[3]);
				int buyV = 0;
				int sellV = 0;
				try {
					buyV = Integer.parseInt(args[4]);
					sellV = Integer.parseInt(args[5]);
				} catch (Exception e) {
					MessageManager.sendMessage(player, wrong_arg);
					return false;
				}
				SItem sitem = new SItem(player.getItemInHand(), buy, sell, buyV, sellV);
				s.addSItem(sitem);
				String message = "&2You've added an item to &6%shopname%";
				message = message.replace("%shop_name%", args[1]);
				MessageManager.sendMessage((Player) sender, message);
				return false;
			}

			if (args[0].equalsIgnoreCase("addlocation") && args.length >= 2) {
				String name = args[1];
				Shop s = ShopManager.getShop(name);
				if (s == null) {
					MessageManager.sendMessage(player, wrong_arg);
					return false;
				}
				s.addVendor(player.getLocation());
				String message = "&2You've added an location for &6%shopname%";
				message = message.replace("%shop_name%", args[1]);
				MessageManager.sendMessage((Player) sender, message);
				return false;
			}
			MessageManager.sendMessage(player, wrong_arg);
			return false;
		}
		return false;
	}
}
