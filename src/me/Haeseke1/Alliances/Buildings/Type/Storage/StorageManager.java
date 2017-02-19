package me.Haeseke1.Alliances.Buildings.Type.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyItemStackException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import net.md_5.bungee.api.ChatColor;

public class StorageManager {
	
	
	public static Storage createStorage(Building b){
		Storage s = new Storage(b.mainBlock, b.chunk, b.ymin, b.ymax);
		Storage.buildings.remove(b);
		Storage.Allbuildings.remove(b);
		return s;
	}
	
	public static ItemStack addLore(ItemStack item, int amount){
		if(item == null){
			return null;
		}
		ItemStack newItem = new ItemStack(item);
		ItemMeta im = newItem.getItemMeta();
		if(im == null){
			return null;
		}
		List<String> lore = new ArrayList<String>();
		if(im.hasLore()){
			lore = im.getLore();
			lore.add(ChatColor.GOLD + "Amount: " + amount);
		}else{
			lore.add(ChatColor.GOLD + "Amount: " + amount);
		}
		im.setLore(lore);
		newItem.setItemMeta(im);
		return newItem;
	}
	
	public static ItemStack removeLore(ItemStack item){
		if(item == null || !item.hasItemMeta()){
			return null;
		}
		ItemMeta im = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		if(im.hasLore()){
			lore = im.getLore();
			if(lore.get(lore.size() -1).contains("Amount:")){
				lore.remove(lore.size() - 1);
			}
		}
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static boolean hasLore(ItemStack item) {
		if (item == null) {
			return false;
		}
		ItemMeta im = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		if (im.hasLore()) {
			lore = im.getLore();
			if (lore.get(lore.size() - 1).contains("Amount:")) {
				return true;
			}
		}
		return false;
	}
	
	
	public static void registerStorage(){
		FileConfiguration file = Main.BuildingConfig;
		if(!file.contains("Storages")){
			return;
		}
		for(String key : file.getConfigurationSection("Storages").getKeys(false)){
			Location loc = null;
			int ymin = 0;
			int ymax = 0;
			int level = 0;
			HashMap<ItemStack, Integer> items = new HashMap<>();
			Chunk chunk = null;
			try {
				loc = ConfigManager.getLocationFromConfig(file, "Storages." + key + ".mainBlock");
				ymin = ConfigManager.getIntFromConfig(file, "Storages." + key + ".ymin");
				ymax = ConfigManager.getIntFromConfig(file, "Storages." + key + ".ymax");
				level = ConfigManager.getIntFromConfig(file, "Storages." + key + ".level");
				if(file.contains("Storages." + key + ".items")){
					for(String key2 : file.getConfigurationSection("Storages." + key + ".items").getKeys(false)){
						items.put(ConfigManager.getItemStackFromConfig(file, "Storages." + key + ".items." + key2), ConfigManager.getIntFromConfig(file, "Storages." + key + ".items." + key2 + ".ramount"));
					}
				}
				String world = ConfigManager.getStringFromConfig(file, "Storages." + key + ".chunk.world");
				chunk = Bukkit.getWorld(world).getChunkAt(ConfigManager.getIntFromConfig(file, "Storages." + key + ".chunk.x"), ConfigManager.getIntFromConfig(file, "Storages." + key + ".chunk.z"));
			} catch (EmptyLocationException | EmptyIntException | EmptyItemStackException | EmptyStringException e) {
				e.printStackTrace();
			}
			new Storage(loc, chunk, ymin, ymax, items, level);
		}
	}
	
	public static void saveStorage(){
		FileConfiguration file = Main.BuildingConfig;
		int i = 0;
		file.set("Storages", null);
		for(Storage s : Storage.storages){
			ConfigManager.setLocationFromConfig(file, "Storages." + i + ".mainBlock", s.mainBlock);
			int y = 0;
			for(ItemStack item : s.items.keySet()){
				ConfigManager.setItemStackInConfig(file, "Storages." + i + ".items." + y, item);
				file.set("Storages." + i + ".items." + y + ".ramount", s.items.get(item));
				y++;
			}
			file.set("Storages." + i + ".ymin", s.ymin);
			file.set("Storages." + i + ".ymax", s.ymax);
			file.set("Storages." + i + ".chunk.world", s.chunk.getWorld().getName());
			file.set("Storages." + i + ".chunk.x", s.chunk.getX());
			file.set("Storages." + i + ".chunk.z", s.chunk.getZ());
			file.set("Storages." + i + ".level", s.level);
			i++;
		}
	}
	
	
	
	
	
	
}
