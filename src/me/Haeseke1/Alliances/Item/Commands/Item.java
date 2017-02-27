package me.Haeseke1.Alliances.Item.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Item.Buildings.Storage.Storage_Level;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Arrow_Tank;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Creeper_Armor;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Drunk;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Fire_Imp;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Flame_Of_Hell;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Golden;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Legend_Of_Zeus;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Tank;
import me.Haeseke1.Alliances.Item.Weapons.Armor.Void_Armor;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Blade_Of_Zeus;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Double_Strike;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Excalibur;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Fatal_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Heaven_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Karma_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Mob_Slayer;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Night_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Soul_Stealer;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Speed_Blade;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.Swift_Blade;
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
		
		if(args[0].equalsIgnoreCase("sword7") && args.length > 1){
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
				ItemStack item = Swift_Blade.getItem(tier);
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
			ItemStack item = Swift_Blade.getItem(tier);
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword8") && args.length > 1){
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
				ItemStack item = Karma_Blade.getItem(tier);
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
			ItemStack item = Karma_Blade.getItem(tier);
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword9") && args.length > 1){
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
				ItemStack item = Double_Strike.getItem(tier);
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
			ItemStack item = Double_Strike.getItem(tier);
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword10") && args.length > 1){
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
				ItemStack item = Speed_Blade.getItem(tier);
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
			ItemStack item = Speed_Blade.getItem(tier);
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword11") && args.length > 1){
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
				ItemStack item = Heaven_Blade.getItem(tier);
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
			ItemStack item = Heaven_Blade.getItem(tier);
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword12") && args.length > 1){
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
				ItemStack item = Mob_Slayer.getItem(tier);
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
			ItemStack item = Mob_Slayer.getItem(tier);
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("sword13")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[2])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[2]);
				ItemStack item = Excalibur.getItem();
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
			ItemStack item = Excalibur.getItem();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		
		if(args[0].equalsIgnoreCase("armor1")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item = Arrow_Tank.getHelmet();
				getter.getInventory().addItem(item);
				item = Arrow_Tank.getChestplate();
				getter.getInventory().addItem(item);
				item = Arrow_Tank.getLeggings();
				getter.getInventory().addItem(item);
				item = Arrow_Tank.getBoots();
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
			ItemStack item = Arrow_Tank.getHelmet();
			player.getInventory().addItem(item);
			item = Arrow_Tank.getChestplate();
			player.getInventory().addItem(item);
			item = Arrow_Tank.getLeggings();
			player.getInventory().addItem(item);
			item = Arrow_Tank.getBoots();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("armor2")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item = Tank.getHelmet();
				getter.getInventory().addItem(item);
				item = Tank.getChestplate();
				getter.getInventory().addItem(item);
				item = Tank.getLeggings();
				getter.getInventory().addItem(item);
				item = Tank.getBoots();
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
			ItemStack item = Tank.getHelmet();
			player.getInventory().addItem(item);
			item = Tank.getChestplate();
			player.getInventory().addItem(item);
			item = Tank.getLeggings();
			player.getInventory().addItem(item);
			item = Tank.getBoots();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("armor3")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item = Legend_Of_Zeus.getHelmet();
				getter.getInventory().addItem(item);
				item = Legend_Of_Zeus.getChestplate();
				getter.getInventory().addItem(item);
				item = Legend_Of_Zeus.getLeggings();
				getter.getInventory().addItem(item);
				item = Legend_Of_Zeus.getBoots();
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
			ItemStack item = Legend_Of_Zeus.getHelmet();
			player.getInventory().addItem(item);
			item = Legend_Of_Zeus.getChestplate();
			player.getInventory().addItem(item);
			item = Legend_Of_Zeus.getLeggings();
			player.getInventory().addItem(item);
			item = Legend_Of_Zeus.getBoots();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("armor4")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item = Flame_Of_Hell.getHelmet();
				getter.getInventory().addItem(item);
				item = Flame_Of_Hell.getChestplate();
				getter.getInventory().addItem(item);
				item = Flame_Of_Hell.getLeggings();
				getter.getInventory().addItem(item);
				item = Flame_Of_Hell.getBoots();
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
			ItemStack item = Flame_Of_Hell.getHelmet();
			player.getInventory().addItem(item);
			item = Flame_Of_Hell.getChestplate();
			player.getInventory().addItem(item);
			item = Flame_Of_Hell.getLeggings();
			player.getInventory().addItem(item);
			item = Flame_Of_Hell.getBoots();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("armor5")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item = Golden.getHelmet();
				getter.getInventory().addItem(item);
				item = Golden.getChestplate();
				getter.getInventory().addItem(item);
				item = Golden.getLeggings();
				getter.getInventory().addItem(item);
				item = Golden.getBoots();
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
			ItemStack item = Golden.getHelmet();
			player.getInventory().addItem(item);
			item = Golden.getChestplate();
			player.getInventory().addItem(item);
			item = Golden.getLeggings();
			player.getInventory().addItem(item);
			item = Golden.getBoots();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("armor6")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item = Void_Armor.getHelmet();
				getter.getInventory().addItem(item);
				item = Void_Armor.getChestplate();
				getter.getInventory().addItem(item);
				item = Void_Armor.getLeggings();
				getter.getInventory().addItem(item);
				item = Void_Armor.getBoots();
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
			ItemStack item = Void_Armor.getHelmet();
			player.getInventory().addItem(item);
			item = Void_Armor.getChestplate();
			player.getInventory().addItem(item);
			item = Void_Armor.getLeggings();
			player.getInventory().addItem(item);
			item = Void_Armor.getBoots();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("armor7")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item = Creeper_Armor.getHelmet();
				getter.getInventory().addItem(item);
				item = Creeper_Armor.getChestplate();
				getter.getInventory().addItem(item);
				item = Creeper_Armor.getLeggings();
				getter.getInventory().addItem(item);
				item = Creeper_Armor.getBoots();
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
			ItemStack item = Creeper_Armor.getHelmet();
			player.getInventory().addItem(item);
			item = Creeper_Armor.getChestplate();
			player.getInventory().addItem(item);
			item = Creeper_Armor.getLeggings();
			player.getInventory().addItem(item);
			item = Creeper_Armor.getBoots();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("armor8")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item = Fire_Imp.getHelmet();
				getter.getInventory().addItem(item);
				item = Fire_Imp.getChestplate();
				getter.getInventory().addItem(item);
				item = Fire_Imp.getLeggings();
				getter.getInventory().addItem(item);
				item = Fire_Imp.getBoots();
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
			ItemStack item = Fire_Imp.getHelmet();
			player.getInventory().addItem(item);
			item = Fire_Imp.getChestplate();
			player.getInventory().addItem(item);
			item = Fire_Imp.getLeggings();
			player.getInventory().addItem(item);
			item = Fire_Imp.getBoots();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		
		if(args[0].equalsIgnoreCase("armor9")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item = Drunk.getHelmet();
				getter.getInventory().addItem(item);
				item = Drunk.getChestplate();
				getter.getInventory().addItem(item);
				item = Drunk.getLeggings();
				getter.getInventory().addItem(item);
				item = Drunk.getBoots();
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
			ItemStack item = Drunk.getHelmet();
			player.getInventory().addItem(item);
			item = Drunk.getChestplate();
			player.getInventory().addItem(item);
			item = Drunk.getLeggings();
			player.getInventory().addItem(item);
			item = Drunk.getBoots();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("armor10")){
			if(args.length > 1){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendMessage(sender, "&cThis player is not online!");
					return false;
				}
				Player getter = PlayerManager.getPlayer(args[1]);
				ItemStack item = Flame_Of_Hell.getHelmet();
				getter.getInventory().addItem(item);
				item = Flame_Of_Hell.getChestplate();
				getter.getInventory().addItem(item);
				item = Flame_Of_Hell.getLeggings();
				getter.getInventory().addItem(item);
				item = Flame_Of_Hell.getBoots();
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
			ItemStack item = Flame_Of_Hell.getHelmet();
			player.getInventory().addItem(item);
			item = Flame_Of_Hell.getChestplate();
			player.getInventory().addItem(item);
			item = Flame_Of_Hell.getLeggings();
			player.getInventory().addItem(item);
			item = Flame_Of_Hell.getBoots();
			player.getInventory().addItem(item);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You got a " + ChatColor.GOLD + item.getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
			return false;
		}
		
		
		return false;
	}
}
