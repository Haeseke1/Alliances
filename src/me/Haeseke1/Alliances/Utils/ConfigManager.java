package me.Haeseke1.Alliances.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.Haeseke1.Alliances.Alliance.Type.Caith_Sith;
import me.Haeseke1.Alliances.Alliance.Type.Gnome;
import me.Haeseke1.Alliances.Alliance.Type.Imp;
import me.Haeseke1.Alliances.Alliance.Type.Leprechaun;
import me.Haeseke1.Alliances.Alliance.Type.Pooka;
import me.Haeseke1.Alliances.Alliance.Type.Salamander;
import me.Haeseke1.Alliances.Alliance.Type.Spriggan;
import me.Haeseke1.Alliances.Alliance.Type.Sylph;
import me.Haeseke1.Alliances.Alliance.Type.Undine;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyItemStackException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringListException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Outpost.Timer;
import me.Haeseke1.Alliances.Outpost.Type.Blacksmith;
import me.Haeseke1.Alliances.Outpost.Type.Dock;
import me.Haeseke1.Alliances.Outpost.Type.Farm;
import me.Haeseke1.Alliances.Outpost.Type.God;
import me.Haeseke1.Alliances.Outpost.Type.Magic_Tower;
import me.Haeseke1.Alliances.Outpost.Type.Mine;
import me.Haeseke1.Alliances.Outpost.Type.Mob_Farm;

public class ConfigManager {

	/*
	 * Retrieves a string from a config file
	 */
	public static String getStringFromConfig(FileConfiguration config, String path) throws EmptyStringException {
		if (config.getString(path) == null) {
			throw new EmptyStringException(path);
		}
		return config.getString(path);
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

	/*
	 * Register or reload config file
	 */
	public static void registerConfigFile(Main main) {
		main.reloadConfig();
		// fetch logo
		try {
			Main.cmdlogo = MessageManager.translateColorCode(ConfigManager.getStringFromConfig(Main.config, "Logo"));
		} catch (EmptyStringException e) {
			e.printStackTrace();
			Main.pm.disablePlugin(main);
			return;
		}
		try {
			MessageManager.alertColorCode = MessageManager
					.translateColorCode(getStringFromConfig(Main.config, "ColorCodes.alertMessages"));
			MessageManager.infoColorCode = MessageManager
					.translateColorCode(getStringFromConfig(Main.config, "ColorCodes.infoMessages"));
			MessageManager.remarkColorCode = MessageManager
					.translateColorCode(getStringFromConfig(Main.config, "ColorCodes.remarkMessages"));
		} catch (EmptyStringException e) {
			e.printStackTrace();
			Main.pm.disablePlugin(main);
		}

		try {
			Coins.defaultCoins = getIntFromConfig(Main.config, "Coins.StarterCoins");
			Caith_Sith.cost = getIntFromConfig(Main.config, "Coins.AllianceTypes.Cait Sith");
			Gnome.cost = getIntFromConfig(Main.config, "Coins.AllianceTypes.Gnome");
			Imp.cost = getIntFromConfig(Main.config, "Coins.AllianceTypes.Imp");
			Leprechaun.cost = getIntFromConfig(Main.config, "Coins.AllianceTypes.Leprechaun");
			Pooka.cost = getIntFromConfig(Main.config, "Coins.AllianceTypes.Pooka");
			Salamander.cost = getIntFromConfig(Main.config, "Coins.AllianceTypes.Salamander");
			Spriggan.cost = getIntFromConfig(Main.config, "Coins.AllianceTypes.Spriggan");
			Sylph.cost = getIntFromConfig(Main.config, "Coins.AllianceTypes.Sylph");
			Undine.cost = getIntFromConfig(Main.config, "Coins.AllianceTypes.Undine");
			
			Timer.rewardTime = getIntFromConfig(Main.config, "Outpost.Time_Per_Reward");
			Timer.take_overTime = getIntFromConfig(Main.config, "Outpost.Time_For_Take_Over");
		} catch (EmptyIntException e) {
			e.printStackTrace();
			Main.pm.disablePlugin(main);
		}
		
		try{
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Blacksmith").getKeys(false)){
				ItemStack i = getItemStackFromConfig(Main.config, "Outpost.Rewards.Blacksmith." + s);
				Blacksmith.rewards.put(i, getIntFromConfig(Main.config, "Outpost.Rewards.Blacksmith." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Dock").getKeys(false)){
				ItemStack i = getItemStackFromConfig(Main.config, "Outpost.Rewards.Dock." + s);
				Dock.rewards.put(i, getIntFromConfig(Main.config, "Outpost.Rewards.Dock." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Farm").getKeys(false)){
				ItemStack i = getItemStackFromConfig(Main.config, "Outpost.Rewards.Farm." + s);
				Farm.rewards.put(i, getIntFromConfig(Main.config, "Outpost.Rewards.Farm." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Blacksmith").getKeys(false)){
				ItemStack i = getItemStackFromConfig(Main.config, "Outpost.Rewards.Blacksmith." + s);
				God.rewards.put(i, getIntFromConfig(Main.config, "Outpost.Rewards.Blacksmith." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Magic_Tower").getKeys(false)){
				ItemStack i = getItemStackFromConfig(Main.config, "Outpost.Rewards.Magic_Tower." + s);
				Magic_Tower.rewards.put(i, getIntFromConfig(Main.config, "Outpost.Rewards.Magic_Tower." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Mine").getKeys(false)){
				ItemStack i = getItemStackFromConfig(Main.config, "Outpost.Rewards.Mine." + s);
				Mine.rewards.put(i, getIntFromConfig(Main.config, "Outpost.Rewards.Mine." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Mob_Farm").getKeys(false)){
				ItemStack i = getItemStackFromConfig(Main.config, "Outpost.Rewards.Mob_Farm." + s);
				Mob_Farm.rewards.put(i, getIntFromConfig(Main.config, "Outpost.Rewards.Mob_Farm." + s + ".Percentage"));
			}
		}catch(EmptyItemStackException e){
			e.printStackTrace();
		} catch (EmptyIntException e) {
			e.printStackTrace();
		}
		
		
		
		// create configs into plugin folder
		File dir = main.getDataFolder();
		if (!dir.exists()) {
			if (!dir.mkdir()) {
				MessageManager.sendAlertMessage("Couldn't create the plugin folder...");
			} else {
				main.saveDefaultConfig();
				MessageManager.sendRemarkMessage("The config is succesfully created!");
			}
		} else {
			MessageManager.sendInfoMessage("Configs are ready to go!");
		}

	}

	/*
	 * Save changes made to the config file
	 */
	public static void saveConfigFile(Main main) {
		main.saveDefaultConfig();
	}

	public static FileConfiguration getCustomConfig(File f) {
		FileConfiguration file = YamlConfiguration.loadConfiguration(f);
		Main.configFiles.put(f.getName(), file);
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
