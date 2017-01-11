package me.Haeseke1.Alliances.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
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

	@SuppressWarnings("deprecation")
	public static void saveShops() {
		FileConfiguration file = Main.shopConfig;
		for(String s : file.getKeys(false)){
			file.set(s, null);
		}
		for(Shop s : Shop.shops){
			int i = 0;
			for(SItem sitem : s.shopItems){
				file.set(s.name + ".Items." + i + ".ID", sitem.item.getTypeId());
				file.set(s.name + ".Items." + i + ".Data", sitem.item.getData().getData());
				file.set(s.name + ".Items." + i + ".Amount", sitem.item.getAmount());
				if(sitem.item.hasItemMeta()){
					if(sitem.item.getItemMeta().hasDisplayName()){
						file.set(s.name + ".Items." + i + ".DisplayName", sitem.item.getItemMeta().getDisplayName());
					}
					if(sitem.item.getItemMeta().hasLore()){
						file.set(s.name + ".Items." + i + ".Lore", sitem.item.getItemMeta().getLore());
					}
				}
				Map<Enchantment,Integer> map = sitem.item.getEnchantments();
				List<String> list = new ArrayList<String>();
				for(Entry<Enchantment,Integer> ench : map.entrySet()){
					list.add(ench.getKey().getId() + "," + ench.getValue());
				}
				file.set(s.name + ".Items." + i + ".Enchantments", list);
				file.set(s.name + ".Items." + i + ".Buy", sitem.buy);
				file.set(s.name + ".Items." + i + ".Sell", sitem.sell);
				file.set(s.name + ".Items." + i + ".Buy_Price", sitem.buyV);
				file.set(s.name + ".Items." + i + ".Sell_Price", sitem.sellV);
				i++;
			}
			i = 0;
			for(Villager villager : s.vendors){
				Location loc = villager.getLocation();
				file.set(s.name + ".Locations." + i + ".World", loc.getWorld().getName());
				file.set(s.name + ".Locations." + i + ".X", loc.getX());
				file.set(s.name + ".Locations." + i + ".Y", loc.getY());
				file.set(s.name + ".Locations." + i + ".Z", loc.getZ());
				i++;
			}
			file.set(s.name + ".Name", s.name);
		}
		
		
		
	}

}
