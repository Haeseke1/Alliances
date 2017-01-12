package me.Haeseke1.Alliances.Commands.Arena.CreateArena;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Commands.Outpost.Create.InventoryEvents;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

public class mainCreateArena {
	
	
	public static HashMap<Player,String> createArena = new HashMap<Player,String>();
	
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("You need to be a player to do this command!");
			return;
		}
		Player player = (Player) sender;
		if(regionSelect.leftClick.containsKey(player) && regionSelect.rightClick.containsKey(player)){
			InventoryEvents.createInventory(player);
		}else{
			MessageManager.sendAlertMessage(player, "Select a region first!");
		}
		
		ItemStack item = new ItemStack(Material.WOOD_HOE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Arena selector");
		item.setItemMeta(im);
		MessageManager.sendInfoMessage(player, "Select first teamspawn!");
		createArena.put(player, args[2]);
	}
	
	
	
}
