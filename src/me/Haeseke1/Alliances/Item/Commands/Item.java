package me.Haeseke1.Alliances.Item.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Item.Outpost_Compass;
import me.Haeseke1.Alliances.Item.Buildings.Storage.Storage_Level;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Blade_Of_Zeus;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Fatal_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Night_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Soul_Stealer;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Warrior_Sword;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Wither_Blade;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.PlayerManager;
import net.md_5.bungee.api.ChatColor;

public class Item implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0 && sender.hasPermission("Alliances.items.*")){
			sender.sendMessage(MessageManager.infoColorCode + "===== Items =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/item compass [playername] #Create a new compass outpost");
			sender.sendMessage(MessageManager.infoColorCode + "/item storageupgrade <Tier> [playername] #Create a new compass outpost");
			sender.sendMessage(MessageManager.infoColorCode + "/item sword[1-..]  #Spawn all the swords");
			return false;
		}

		if (!(sender instanceof Player)) {
			if(args[0].equalsIgnoreCase("storageupgrade") && args.length > 1){
				int tier = 0;
				switch(args[1].toLowerCase()){
				case "2":
					tier = 2;
					break;
				case "3":
					tier = 3;
					break;
				case "inf":
					tier = 0;
					break;
				default:
					MessageManager.sendAlertMessage("This tier doesn't exists");
					return false;
				}
				if(args.length > 2){
					if(!PlayerManager.isPlayerOnline(args[2])){
						MessageManager.sendAlertMessage("This player isn't online");
						return false;
					}
					Player getter = PlayerManager.getPlayer(args[2]);
					ItemStack item = Storage_Level.getItem(tier);
					getter.getInventory().addItem(item);
					MessageManager.sendRemarkMessage("Successfully gave " + ChatColor.GOLD + getter.getName() + ChatColor.GREEN + " a " + item.getItemMeta().getDisplayName());
					MessageManager.sendMessage(getter, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
					return false;
				}
				MessageManager.sendAlertMessage("The console can't receive any in-game items");
				return false;
			}
			String message = "This command needs to be executed by a player";
			MessageManager.sendAlertMessage(message);
			return false;
		}
		Player player = (Player) sender;
		

		if(!sender.hasPermission("Alliances.items.*")){
			MessageManager.sendMessage(sender, "&cWrong argument do: /pve");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("compass")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender,"&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				getter.getInventory().addItem(Outpost_Compass.getItem(getter));
				MessageManager.sendMessage(sender, ChatColor.GREEN + "You gave " + getter.getName() + " a " + ChatColor.GOLD + "Outpost Compass" + ChatColor.GREEN + "!");
				MessageManager.sendMessage(getter, ChatColor.GREEN + "You got a " + ChatColor.GOLD + "Outpost Compass" + ChatColor.GREEN + "!");
				return false;
			}
			if (!(sender instanceof Player)) {
				String message = "This command needs to be executed by a player";
				MessageManager.sendAlertMessage(message);
				return false;
			}
			player.getInventory().addItem(Outpost_Compass.getItem(player));
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + "Outpost Compass" + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("storageupgrade") && args.length > 1){
			int tier = 0;
			switch(args[1].toLowerCase()){
			case "2":
				tier = 2;
				break;
			case "3":
				tier = 3;
				break;
			case "inf":
				tier = 0;
				break;
			default:
				MessageManager.sendMessage(sender, "&cThis tier does not exist!");
				return false;
			}
			if(args.length > 2){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[2]);
				ItemStack item = Storage_Level.getItem(tier);
				getter.getInventory().addItem(item);
				MessageManager.sendMessage(sender, ChatColor.GREEN + "You gave " + getter.getName() + " a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				MessageManager.sendMessage(getter, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				return false;
			}
			if (!(sender instanceof Player)) {
				String message = "This command needs to be executed by a player";
				MessageManager.sendAlertMessage(message);
				return false;
			}
			ItemStack item = Storage_Level.getItem(tier);
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword1")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				getter.getInventory().addItem(Warrior_Sword.getItem());
				MessageManager.sendMessage(sender, ChatColor.GREEN + "You gave " + getter.getName() + " a " + ChatColor.GOLD + "Sword1" + ChatColor.GREEN + "!");
				MessageManager.sendMessage(getter, ChatColor.GREEN + "You got a " + ChatColor.GOLD + "Sword1" + ChatColor.GREEN + "!");
				return false;
			}
			if (!(sender instanceof Player)) {
				String message = "This command needs to be executed by a player";
				MessageManager.sendAlertMessage(message);
				return false;
			}
			player.getInventory().addItem(Warrior_Sword.getItem());
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + "Sword1" + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword2")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item  = Night_Blade.getItem();
				getter.getInventory().addItem(item);
				MessageManager.sendMessage(sender, ChatColor.GREEN + "You gave " + getter.getName() + " a " + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				MessageManager.sendMessage(getter, ChatColor.GREEN + "You got a " + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				return false;
			}
			if (!(sender instanceof Player)) {
				String message = "This command needs to be executed by a player";
				MessageManager.sendAlertMessage(message);
				return false;
			}
			ItemStack item  = Night_Blade.getItem();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword3") && args.length > 1){
			int tier = 0;
			try{
				tier = Integer.parseInt(args[1]);
			}catch(Exception e){
				MessageManager.sendMessage(sender, ChatColor.RED + "This is not a number!");
				return false;
			}
			
			if(args.length > 2){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[2]);
				ItemStack item = Wither_Blade.getItem(tier);
				getter.getInventory().addItem(item);
				MessageManager.sendMessage(sender, ChatColor.GREEN + "You gave " + getter.getName() + " a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				MessageManager.sendMessage(getter, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				return false;
			}
			if (!(sender instanceof Player)) {
				String message = "This command needs to be executed by a player";
				MessageManager.sendAlertMessage(message);
				return false;
			}
			ItemStack item = Wither_Blade.getItem(tier);
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword4") && args.length > 1){
			int tier = 0;
			try{
				tier = Integer.parseInt(args[1]);
			}catch(Exception e){
				MessageManager.sendMessage(sender, ChatColor.RED + "This is not a number!");
				return false;
			}
			
			if(args.length > 2){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[2]);
				ItemStack item = Fatal_Blade.getItem(tier);
				getter.getInventory().addItem(item);
				MessageManager.sendMessage(sender, ChatColor.GREEN + "You gave " + getter.getName() + " a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				MessageManager.sendMessage(getter, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				return false;
			}
			if (!(sender instanceof Player)) {
				String message = "This command needs to be executed by a player";
				MessageManager.sendAlertMessage(message);
				return false;
			}
			ItemStack item = Fatal_Blade.getItem(tier);
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword5") && args.length > 1){
			int tier = 0;
			try{
				tier = Integer.parseInt(args[1]);
			}catch(Exception e){
				MessageManager.sendMessage(sender, ChatColor.RED + "This is not a number!");
				return false;
			}
			
			if(args.length > 2){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[2]);
				ItemStack item = Soul_Stealer.getItem(tier);
				getter.getInventory().addItem(item);
				MessageManager.sendMessage(sender, ChatColor.GREEN + "You gave " + getter.getName() + " a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				MessageManager.sendMessage(getter, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				return false;
			}
			if (!(sender instanceof Player)) {
				String message = "This command needs to be executed by a player";
				MessageManager.sendAlertMessage(message);
				return false;
			}
			ItemStack item = Soul_Stealer.getItem(tier);
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword6")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[2]);
				ItemStack item = Blade_Of_Zeus.getItem();
				getter.getInventory().addItem(item);
				MessageManager.sendMessage(sender, ChatColor.GREEN + "You gave " + getter.getName() + " a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				MessageManager.sendMessage(getter, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
				return false;
			}
			if (!(sender instanceof Player)) {
				String message = "This command needs to be executed by a player";
				MessageManager.sendAlertMessage(message);
				return false;
			}
			ItemStack item = Blade_Of_Zeus.getItem();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		
		return false;
	}
}
