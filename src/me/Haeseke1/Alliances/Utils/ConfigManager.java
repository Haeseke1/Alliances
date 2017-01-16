package me.Haeseke1.Alliances.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.Haeseke1.Alliances.Exceptions.EmptyBooleanException;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyItemStackException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringListException;
import me.Haeseke1.Alliances.Main.Main;

public class ConfigManager {

	public static String getStringFromConfig(FileConfiguration config, String path) throws EmptyStringException {
		if (config.getString(path) == null) {
			throw new EmptyStringException(path);
		}
		return config.getString(path);
	}

	public static Boolean getBooleanFromConfig(FileConfiguration config, String path) throws EmptyBooleanException {
		if (config.getString(path) == null) {
			throw new EmptyBooleanException(path);
		}
		return config.getBoolean(path);
	}
	
	public static Integer getIntFromConfig(FileConfiguration config, String path) throws EmptyIntException {
		if (!config.isInt(path)) {
			throw new EmptyIntException(path);
		}
		return config.getInt(path);
	}
	
	public static List<String> getStringListFromConfig(FileConfiguration config, String path) throws EmptyStringListException {
		if (config.getStringList(path) == null) {
			throw new EmptyStringListException(path);
		}
		return config.getStringList(path);
	}
		
	public static Location getLocationFromConfig(FileConfiguration config, String path) throws EmptyLocationException {
		if (config.getStringList(path) == null) {
			throw new EmptyLocationException(path);
		}
		try{
			Location loc = new Location(Bukkit.getWorld(config.getString(path + ".World")), config.getDouble(path + ".X"), config.getDouble(path + ".Y"), config.getDouble(path + ".Z"));
			return loc;
		}catch(Exception e){
			MessageManager.sendAlertMessage("Location can't be fetched from " + path);
			return null;
		}
	}
	
	public static void setLocationFromConfig(FileConfiguration config, String path, Location loc) {
		config.set(path + ".World", loc.getWorld().getName());
		config.set(path + ".X", loc.getX());
		config.set(path + ".Y", loc.getY());
		config.set(path + ".Z", loc.getZ());
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getItemStackFromConfig(FileConfiguration config, String path) throws EmptyItemStackException {
		if (config.getStringList(path) == null) {
			throw new EmptyItemStackException(path);
		}
		try{
			ItemStack i = new ItemStack(config.getInt(path + ".ID"));
			i.setAmount(config.getInt(path + ".Amount"));
			ItemMeta im = i.getItemMeta();
			if(config.contains(path + ".Displayname")){
				im.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(path + ".Displayname")));
			}
			if(config.contains(path + ".Lore")){
				im.setLore(config.getStringList(path + ".Lore"));
			}
			i.setItemMeta(im);
			MaterialData md = i.getData();
			md.setData((byte) config.getInt(path + ".Data"));
			i.setData(md);
			if(config.contains(path + ".Enchantments")){
				for(String ench : config.getStringList(path + ".Enchantments")){
					i.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(ench.split(",")[0])), Integer.parseInt(ench.split(",")[1]));
				}
			}
			return i;
		}catch(Exception e){
			MessageManager.sendAlertMessage("ItemStack can't be fetched from " + path);
			return null;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void setItemStackInConfig(FileConfiguration config, String path, ItemStack item){
		config.set(path + ".ID", item.getTypeId());
		config.set(path + ".Amount", item.getAmount());
		if(item.hasItemMeta()){
			if(item.getItemMeta().hasDisplayName()){
				config.set(path + ".Displayname", item.getItemMeta().getDisplayName());
			}
			if(item.getItemMeta().hasLore()){
				config.set(path + ".Lore", item.getItemMeta().getLore());
			}
		}
		config.set(path + ".Data", item.getData().getData());
		List<String> list = new ArrayList<String>();
		for(Entry<Enchantment,Integer> ench : item.getEnchantments().entrySet()){
			list.add(ench.getKey().getId() + "," + ench.getValue());
		}
		config.set(path + ".Enchantments", list);
	}
	
	public static FileConfiguration getCustomConfig(File f, Main main) {
		FileConfiguration file = YamlConfiguration.loadConfiguration(f);
		Reader defConfigStream;
		try {
			if(!f.exists()){
				defConfigStream = new InputStreamReader(main.getResource(f.getName()), "UTF8");
			    if (defConfigStream != null) {
			        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			        file = defConfig;
			    }
			}else{
				defConfigStream = new InputStreamReader(main.getResource(f.getName()), "UTF8");
			    if (defConfigStream != null) {
			        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			        file.setDefaults(defConfig);
			    }
			}
		} catch (UnsupportedEncodingException e) {
			Main.pm.disablePlugin(Main.plugin);
			e.printStackTrace();
		}
		Main.configFiles.put(f.getName(), file);
		Main.configFile.put(f.getName(), f);
		return file;
	}

	public static void saveCustomConfig(File f, FileConfiguration file) {
		if (f == null || file == null) {
			return;
		}
		try {
			file.save(f);
		} catch (IOException ex) {
			MessageManager.sendAlertMessage("Could not save " + file.getName() + "!");
		}
	}

}
