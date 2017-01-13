package me.Haeseke1.Alliances.Main;

import java.io.File;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

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
import me.Haeseke1.Alliances.Outpost.Timer;
import me.Haeseke1.Alliances.Outpost.Type.Blacksmith;
import me.Haeseke1.Alliances.Outpost.Type.Dock;
import me.Haeseke1.Alliances.Outpost.Type.Farm;
import me.Haeseke1.Alliances.Outpost.Type.God;
import me.Haeseke1.Alliances.Outpost.Type.Magic_Tower;
import me.Haeseke1.Alliances.Outpost.Type.Mine;
import me.Haeseke1.Alliances.Outpost.Type.Mob_Farm;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Config {
	
	public static void registerConfigFile(Main main) {
		main.reloadConfig();
		try {
			Main.cmdlogo = MessageManager.translateColorCode(ConfigManager.getStringFromConfig(Main.config, "Logo"));
		} catch (EmptyStringException e) {
			e.printStackTrace();
			Main.pm.disablePlugin(main);
			return;
		}
		try {
			MessageManager.alertColorCode = MessageManager
					.translateColorCode(ConfigManager.getStringFromConfig(Main.config, "ColorCodes.alertMessages"));
			MessageManager.infoColorCode = MessageManager
					.translateColorCode(ConfigManager.getStringFromConfig(Main.config, "ColorCodes.infoMessages"));
			MessageManager.remarkColorCode = MessageManager
					.translateColorCode(ConfigManager.getStringFromConfig(Main.config, "ColorCodes.remarkMessages"));
		} catch (EmptyStringException e) {
			e.printStackTrace();
			Main.pm.disablePlugin(main);
		}

		try {
			Coins.defaultCoins = ConfigManager.getIntFromConfig(Main.config, "Coins.StarterCoins");
			Caith_Sith.cost = ConfigManager.getIntFromConfig(Main.config, "Coins.AllianceTypes.Cait Sith");
			Gnome.cost = ConfigManager.getIntFromConfig(Main.config, "Coins.AllianceTypes.Gnome");
			Imp.cost = ConfigManager.getIntFromConfig(Main.config, "Coins.AllianceTypes.Imp");
			Leprechaun.cost = ConfigManager.getIntFromConfig(Main.config, "Coins.AllianceTypes.Leprechaun");
			Pooka.cost = ConfigManager.getIntFromConfig(Main.config, "Coins.AllianceTypes.Pooka");
			Salamander.cost = ConfigManager.getIntFromConfig(Main.config, "Coins.AllianceTypes.Salamander");
			Spriggan.cost = ConfigManager.getIntFromConfig(Main.config, "Coins.AllianceTypes.Spriggan");
			Sylph.cost = ConfigManager.getIntFromConfig(Main.config, "Coins.AllianceTypes.Sylph");
			Undine.cost = ConfigManager.getIntFromConfig(Main.config, "Coins.AllianceTypes.Undine");
			
			Timer.rewardTime = ConfigManager.getIntFromConfig(Main.config, "Outpost.Time_Per_Reward");
			Timer.take_overTime = ConfigManager.getIntFromConfig(Main.config, "Outpost.Time_For_Take_Over");
		} catch (EmptyIntException e) {
			e.printStackTrace();
			Main.pm.disablePlugin(main);
		}
		
		try{
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Blacksmith").getKeys(false)){
				ItemStack i = ConfigManager.getItemStackFromConfig(Main.config, "Outpost.Rewards.Blacksmith." + s);
				Blacksmith.rewards.put(i, ConfigManager.getIntFromConfig(Main.config, "Outpost.Rewards.Blacksmith." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Dock").getKeys(false)){
				ItemStack i = ConfigManager.getItemStackFromConfig(Main.config, "Outpost.Rewards.Dock." + s);
				Dock.rewards.put(i, ConfigManager.getIntFromConfig(Main.config, "Outpost.Rewards.Dock." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Farm").getKeys(false)){
				ItemStack i = ConfigManager.getItemStackFromConfig(Main.config, "Outpost.Rewards.Farm." + s);
				Farm.rewards.put(i, ConfigManager.getIntFromConfig(Main.config, "Outpost.Rewards.Farm." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Blacksmith").getKeys(false)){
				ItemStack i = ConfigManager.getItemStackFromConfig(Main.config, "Outpost.Rewards.Blacksmith." + s);
				God.rewards.put(i, ConfigManager.getIntFromConfig(Main.config, "Outpost.Rewards.Blacksmith." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Magic_Tower").getKeys(false)){
				ItemStack i = ConfigManager.getItemStackFromConfig(Main.config, "Outpost.Rewards.Magic_Tower." + s);
				Magic_Tower.rewards.put(i, ConfigManager.getIntFromConfig(Main.config, "Outpost.Rewards.Magic_Tower." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Mine").getKeys(false)){
				ItemStack i = ConfigManager.getItemStackFromConfig(Main.config, "Outpost.Rewards.Mine." + s);
				Mine.rewards.put(i, ConfigManager.getIntFromConfig(Main.config, "Outpost.Rewards.Mine." + s + ".Percentage"));
			}
			for(String s : Main.config.getConfigurationSection("Outpost.Rewards.Mob_Farm").getKeys(false)){
				ItemStack i = ConfigManager.getItemStackFromConfig(Main.config, "Outpost.Rewards.Mob_Farm." + s);
				Mob_Farm.rewards.put(i, ConfigManager.getIntFromConfig(Main.config, "Outpost.Rewards.Mob_Farm." + s + ".Percentage"));
			}
		}catch(EmptyItemStackException e){
			e.printStackTrace();
		} catch (EmptyIntException e) {
			e.printStackTrace();
		}
		
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

	public static void saveConfigFile(Main main) {
		FileConfiguration config = Main.config;
		config.set("Outpost.Rewards.Blacksmith", null);
		int i = 0;
		for(Entry<ItemStack,Integer> entry : Blacksmith.rewards.entrySet()){
			ConfigManager.setItemStackInConfig(config, "Outpost.Rewards.Blacksmith." + i, entry.getKey());
			config.set("Outpost.Rewards.Blacksmith." + i + ".Percentage", entry.getValue());
			i++;
		}
		
		config.set("Outpost.Rewards.Dock.", null);
		i = 0;
		for(Entry<ItemStack,Integer> entry : Dock.rewards.entrySet()){
			ConfigManager.setItemStackInConfig(config, "Outpost.Rewards.Dock." + i, entry.getKey());
			config.set("Outpost.Rewards.Dock." + i + ".Percentage", entry.getValue());
			i++;
		}
		
		config.set("Outpost.Rewards.Farm", null);
		i = 0;
		for(Entry<ItemStack,Integer> entry : Farm.rewards.entrySet()){
			ConfigManager.setItemStackInConfig(config, "Outpost.Rewards.Farm." + i, entry.getKey());
			config.set("Outpost.Rewards.Farm." + i + ".Percentage", entry.getValue());
			i++;
		}
		
		config.set("Outpost.Rewards.God", null);
		i = 0;
		for(Entry<ItemStack,Integer> entry : God.rewards.entrySet()){
			ConfigManager.setItemStackInConfig(config, "Outpost.Rewards.God." + i, entry.getKey());
			config.set("Outpost.Rewards.God." + i + ".Percentage", entry.getValue());
			i++;
		}
		
		config.set("Outpost.Rewards.Magic_Tower", null);
		i = 0;
		for(Entry<ItemStack,Integer> entry : Magic_Tower.rewards.entrySet()){
			ConfigManager.setItemStackInConfig(config, "Outpost.Rewards.Magic_Tower." + i, entry.getKey());
			config.set("Outpost.Rewards.Magic_Tower." + i + ".Percentage", entry.getValue());
			i++;
		}
		
		config.set("Outpost.Rewards.Mine", null);
		i = 0;
		for(Entry<ItemStack,Integer> entry : Mine.rewards.entrySet()){
			ConfigManager.setItemStackInConfig(config, "Outpost.Rewards.Mine." + i, entry.getKey());
			config.set("Outpost.Rewards.Mine." + i + ".Percentage", entry.getValue());
			i++;
		}
		
		config.set("Outpost.Rewards.Mob_Farm", null);
		i = 0;
		for(Entry<ItemStack,Integer> entry : Mine.rewards.entrySet()){
			ConfigManager.setItemStackInConfig(config, "Outpost.Rewards.Mob_Farm." + i, entry.getKey());
			config.set("Outpost.Rewards.Mob_Farm." + i + ".Percentage", entry.getValue());
			i++;
		}
		main.saveDefaultConfig();
	}

}
