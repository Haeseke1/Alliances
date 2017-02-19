package me.Haeseke1.Alliances.Crates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyItemStackException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import net.md_5.bungee.api.ChatColor;

public class CrateManager {
	
	public static Crate getCrate(String name){
		for(Crate crate : Crate.crates){
			if(ChatColor.stripColor(crate.name).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name)))){
				return crate;
			}
		}
		return null;
	}
	
	public static void registerCrate(){
		Crate.crates = new ArrayList<Crate>();
		KeyManager.registerKey();
		FileConfiguration file = Main.CratesConfig;
		if(!file.contains("Crates")){
			return;
		}
		for(String s : file.getConfigurationSection("Crates").getKeys(false)){
			String name = s;
			List<Location> locs = new ArrayList<Location>();
			if(file.contains("Crates." + s + ".Location")){
				for(String locS : file.getConfigurationSection("Crates." + s + ".Location").getKeys(false)){
					try {
						locs.add(ConfigManager.getLocationFromConfig(file, "Crates." + s + ".Location." + locS));
					} catch (EmptyLocationException e) {
						e.printStackTrace();
					}
				}
			}
			
			HashMap<ItemStack,Integer> rewards = new HashMap<ItemStack,Integer>();
			if(file.contains("Crates." + s + ".Rewards")){
				for(String itemS : file.getConfigurationSection("Crates." + s + ".Rewards").getKeys(false)){
					try {
						rewards.put(ConfigManager.getItemStackFromConfig(file, "Crates." + s + ".Rewards." + itemS), ConfigManager.getIntFromConfig(file, "Crates." + s + ".Rewards." + itemS + ".Percentage"));
					} catch (EmptyItemStackException | EmptyIntException e) {
						e.printStackTrace();
					}
				}
			}
			Key key = null;
			if(file.contains("Crates." + s + ".Key")){
				try {
					key = KeyManager.getKey(ConfigManager.getStringFromConfig(file, "Crates." + s + ".Key"));
				} catch (EmptyStringException e) {
					e.printStackTrace();
				}
			}
			new Crate(name, locs, rewards, key);
		}
	}
	
	
	public static void saveCrate(){
		KeyManager.saveKey();
		FileConfiguration file = Main.CratesConfig;
		for(Crate crate : Crate.crates){
			if(crate.key != null){
				file.set("Crates." + crate.name + ".Key", crate.key.name);
			}
			int i = 0;
			for(Location loc : crate.locs){
				ConfigManager.setLocationFromConfig(file, "Crates." + crate.name + ".Location." + i, loc);
				i++;
			}
			
			i = 0;
			for(Entry<ItemStack,Integer> entry : crate.rewards.entrySet()){
				ConfigManager.setItemStackInConfig(file, "Crates." + crate.name + ".Rewards." + i, entry.getKey());
				file.set("Crates." + crate.name + ".Rewards." + i + ".Percentage", entry.getValue());
				i++;
			}
		}
	}
}
