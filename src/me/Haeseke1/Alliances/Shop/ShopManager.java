package me.Haeseke1.Alliances.Shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Villager;

import me.Haeseke1.Alliances.Exceptions.EmptyBooleanException;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyItemStackException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class ShopManager {
	
	
	
	public static void despawnVendors(){
		for(Shop s : Shop.shops){
			s.despawnVendors();
		}
	}
	
	public static boolean shopExist(String name){
		for(Shop shop : Shop.shops){
			if(shop.name.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public static Shop getShop(String name){
		for(Shop shop : Shop.shops){
			if(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', shop.name)).equalsIgnoreCase(name)){
				return shop;
			}
		}
		return null;
	}
	

	public static void registerShops() {
		FileConfiguration file = Main.shopConfig;
		for(String s : file.getKeys(false)){
			List<Location> locations = new ArrayList<Location>();
			if(file.contains(s + ".Locations")){
				for(String loc : file.getConfigurationSection(s + ".Locations").getKeys(false)){
					try {
						locations.add(ConfigManager.getLocationFromConfig(file, s + ".Locations." + loc));
					} catch (EmptyLocationException e) {
						e.printStackTrace();
					}
				}
			}
			List<SItem> sitems = new ArrayList<SItem>();
			if(file.contains(s + ".Items")){
				for(String item : file.getConfigurationSection(s + ".Items").getKeys(false)){
					try {
						sitems.add(new SItem(ConfigManager.getItemStackFromConfig(file, s + ".Items." + item), 
								ConfigManager.getBooleanFromConfig(file,  s + ".Items." + item + ".Buy"), 
								ConfigManager.getBooleanFromConfig(file,  s + ".Items." + item + ".Sell"),
								ConfigManager.getIntFromConfig(file,  s + ".Items." + item + ".Buy_Price"), 
								ConfigManager.getIntFromConfig(file,  s + ".Items." + item + ".Sell_Price")));
					} catch (EmptyItemStackException | EmptyBooleanException | EmptyIntException e) {
						e.printStackTrace();
					}
				}
			}
			String name = "";
			try {
				name = ConfigManager.getStringFromConfig(file, s + ".Name");
			} catch (EmptyStringException e) {
				e.printStackTrace();
			}
			new Shop(name, sitems, locations);
		}
	}

	public static void saveShops() {
		FileConfiguration file = Main.shopConfig;
		for(String s : file.getKeys(false)){
			file.set(s, null);
		}
		for(Shop s : Shop.shops){
			int i = 0;
			for(SItem sitem : s.shopItems){
				ConfigManager.setItemStackInConfig(file, s.name + ".Items." + i, sitem.item);
				file.set(s.name + ".Items." + i + ".Buy", sitem.buy);
				file.set(s.name + ".Items." + i + ".Sell", sitem.sell);
				file.set(s.name + ".Items." + i + ".Buy_Price", sitem.buyV);
				file.set(s.name + ".Items." + i + ".Sell_Price", sitem.sellV);
				i++;
			}
			i = 0;
			for(Villager villager : s.vendors){
				Location loc = villager.getLocation();
				ConfigManager.setLocationFromConfig(file, s.name + ".Locations." + i, loc);
				i++;
			}
			file.set(s.name + ".Name", s.name);
		}
		
		
		
	}

}
