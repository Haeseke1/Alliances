package me.Haeseke1.Alliances.Crates.Commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Crates.Crate;
import me.Haeseke1.Alliances.Crates.CrateManager;
import me.Haeseke1.Alliances.Crates.Key;
import me.Haeseke1.Alliances.Crates.KeyManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.PlayerManager;

public class CrateC implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 0) {
			sender.sendMessage(MessageManager.infoColorCode + "========== Crates ==========");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/crates createCrate <Name> #Create a new crate");
			sender.sendMessage(MessageManager.infoColorCode + "/crates createKey <Name>  #Create a new key");
			sender.sendMessage(
					MessageManager.infoColorCode + "/crates setKey <CrateName> <KeyName> #Set a key for some crate");
			sender.sendMessage(MessageManager.infoColorCode
					+ "/crates addReward <CrateName> <Luck>  #Adds a reward to a specifiek crate");
			sender.sendMessage(MessageManager.infoColorCode
					+ "/crates addLocation <CrateName>  #Adds location on the chest you are standing on");
			sender.sendMessage(MessageManager.infoColorCode + "/crates key <KeyName>  #Get a key");
			sender.sendMessage(MessageManager.infoColorCode + "/crates keyall <KeyName>  #Give a key to all players");
			sender.sendMessage(MessageManager.infoColorCode + "/crates removeholo <CrateName>  #Removes holo's near you");
			return true;
		}
		if (args[0].equalsIgnoreCase("createcrate") && args.length > 1) {
			if(CrateManager.getCrate(args[1]) != null){
				MessageManager.sendMessage(sender, ChatColor.RED + "Crate already exists!");
				return false;
			}
			new Crate(args[1], new ArrayList<Location>(), new HashMap<ItemStack, Integer>(), null);
			MessageManager.sendMessage(sender, ChatColor.DARK_GREEN + "Crate added succesfully!");
			return true;
		}
		if (args[0].equalsIgnoreCase("createkey") && args.length > 1) {
			if(KeyManager.getKey(args[1]) != null){
				MessageManager.sendMessage(sender, ChatColor.RED + "Key already exists!");
				return false;
			}
			new Key(args[1]);
			MessageManager.sendMessage(sender, ChatColor.DARK_GREEN + "Key added succesfully!");
			return true;
		}

		if (args[0].equalsIgnoreCase("setkey") && args.length > 2) {
			Crate crate = CrateManager.getCrate(args[1]);
			Key key = KeyManager.getKey(args[2]);
			if (crate == null) {
				MessageManager.sendMessage(sender, ChatColor.RED + "This crate does not exist!");
				return false;
			}
			if (key == null) {
				MessageManager.sendMessage(sender, ChatColor.RED + "This key does not exist!");
				return false;
			}
			crate.setKey(key);
			MessageManager.sendMessage(sender, ChatColor.DARK_GREEN + "Key changed for crate!");
			return true;
		}

		if (args[0].equalsIgnoreCase("addreward") && args.length > 2) {
			if (!(sender instanceof Player)) {
				MessageManager.sendMessage(sender, ChatColor.RED + "You need to be a player to do this command!");
				return false;
			}
			Player player = (Player) sender;
			try {
				Crate crate = CrateManager.getCrate(args[1]);
				int luck = Integer.parseInt(args[2]);
				if (crate == null) {
					MessageManager.sendMessage(sender, ChatColor.RED + "This crate does not exist!");
					return false;
				}
				crate.addReward(player.getItemInHand(), luck);
				MessageManager.sendMessage(sender, ChatColor.DARK_GREEN + "Item added to crate!");
				return true;
			} catch (Exception e) {
				MessageManager.sendMessage(sender, ChatColor.RED + "This is not a number!");
				return false;
			}
		}

		if (args[0].equalsIgnoreCase("addlocation") && args.length > 1) {
			if (!(sender instanceof Player)) {
				MessageManager.sendMessage(sender, ChatColor.RED + "You need to be a player to do this command!");
				return false;
			}
			Player player = (Player) sender;
			if (player.getLocation().getBlock().getType() != Material.CHEST) {
				MessageManager.sendMessage(sender, ChatColor.RED + "You need to stand on a chest!");
				return false;
			}
			Crate crate = CrateManager.getCrate(args[1]);
			if (crate == null) {
				MessageManager.sendMessage(sender, ChatColor.RED + "This crate does not exist!");
				return false;
			}
			crate.addLocation(player.getLocation().getBlock().getLocation());
			MessageManager.sendMessage(sender, ChatColor.DARK_GREEN + "Location added to crate!");
			return true;
		}

		if (args[0].equalsIgnoreCase("key") && args.length > 1) {
			Key key = KeyManager.getKey(args[1]);
			if (key == null) {
				MessageManager.sendMessage(sender, ChatColor.RED + "This key does not exist!");
				return false;
			}
			if (args.length > 2) {
				if (!PlayerManager.isPlayerOnline(args[2])) {
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[2]);
				ItemStack item = key.getItemStack();
				if((getter.getInventory().firstEmpty() == -1)){
					getter.getWorld().dropItem(getter.getLocation(), item);
				}else{
					getter.getInventory().setItem(getter.getInventory().firstEmpty(),item);
				}
				MessageManager.sendMessage(sender, ChatColor.GREEN + "You gave " + getter.getName() + " a "
						+ item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				MessageManager.sendMessage(getter,
						ChatColor.GREEN + "You got a " + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				return false;
			}
			if (!(sender instanceof Player)) {
				String message = "This command needs to be executed by a player";
				MessageManager.sendAlertMessage(message);
				return false;
			}
			Player player = (Player) sender;
			ItemStack item = key.getItemStack();
			if((player.getInventory().firstEmpty() == -1)){
				player.getWorld().dropItem(player.getLocation(), item);
			}else{
				player.getInventory().setItem(player.getInventory().firstEmpty(),item);
			}
			MessageManager.sendMessage(player,
					ChatColor.GREEN + "You got a " + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}

		if (args[0].equalsIgnoreCase("keyall") && args.length > 1) {
			Key key = KeyManager.getKey(args[1]);
			if (key == null) {
				MessageManager.sendMessage(sender, ChatColor.RED + "This key does not exist!");
				return false;
			}
			ItemStack item = key.getItemStack();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if((player.getInventory().firstEmpty() == -1)){
					player.getWorld().dropItem(player.getLocation(), item);
				}else{
					player.getInventory().setItem(player.getInventory().firstEmpty(),item);
				}
				MessageManager.sendMessage(player,
						ChatColor.GREEN + "You got a " + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			}
			MessageManager.sendMessage(sender, ChatColor.GREEN + "Done!");
			return false;
		}
		
		
		if (args[0].equalsIgnoreCase("removeHolo") && args.length > 1) {
			if (!(sender instanceof Player)) {
				String message = "This command needs to be executed by a player";
				MessageManager.sendAlertMessage(message);
				return false;
			}
			Player player = (Player) sender;
			Crate crate = CrateManager.getCrate(args[1]);
			if (crate == null) {
				MessageManager.sendMessage(sender, ChatColor.RED + "This crate does not exist!");
				return false;
			}
			crate.removeArmorStand(player.getLocation());
			MessageManager.sendMessage(sender, ChatColor.GREEN + "Removed holo's!");
			return false;
		}
		MessageManager.sendMessage(sender, ChatColor.RED + "Wrong argument do: /crate");
		return false;
	}
	
}
