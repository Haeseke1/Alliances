package me.Haeseke1.Alliances.Main;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Alliance.Type.Caith_Sith;
import me.Haeseke1.Alliances.Alliance.Type.Gnome;
import me.Haeseke1.Alliances.Alliance.Type.Imp;
import me.Haeseke1.Alliances.Alliance.Type.Leprechaun;
import me.Haeseke1.Alliances.Alliance.Type.Pooka;
import me.Haeseke1.Alliances.Alliance.Type.Salamander;
import me.Haeseke1.Alliances.Alliance.Type.Spriggan;
import me.Haeseke1.Alliances.Alliance.Type.Sylph;
import me.Haeseke1.Alliances.Alliance.Type.Undine;
import me.Haeseke1.Alliances.Chat.ChatEvent;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Exceptions.EmptyBooleanException;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Outpost.Outpost;
import me.Haeseke1.Alliances.Town.TownManager;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Config {
	
	public static void registerConfigFile(Main main) {
		main.reloadConfig();
		try {
			Main.cmdlogo = MessageManager.translateColorCode(ConfigManager.getStringFromConfig(Main.config, "Logo"));
		} catch (EmptyStringException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			MessageManager.alertColorCode = MessageManager
					.translateColorCode(ConfigManager.getStringFromConfig(Main.config, "ColorCodes.alertMessages"));
			MessageManager.infoColorCode = MessageManager
					.translateColorCode(ConfigManager.getStringFromConfig(Main.config, "ColorCodes.infoMessages"));
			MessageManager.remarkColorCode = MessageManager
					.translateColorCode(ConfigManager.getStringFromConfig(Main.config, "ColorCodes.remarkMessages"));
			ChatEvent.format = ConfigManager.getStringFromConfig(Main.config, "Chat.Message");
			
			SQL.SQL = ConfigManager.getBooleanFromConfig(Main.config, "SQL.enable");
			SQL.ip = ConfigManager.getStringFromConfig(Main.config, "SQL.ip");
			SQL.username = ConfigManager.getStringFromConfig(Main.config, "SQL.username");
			SQL.password = ConfigManager.getStringFromConfig(Main.config, "SQL.password");
			SQL.dbName = ConfigManager.getStringFromConfig(Main.config, "SQL.dbName");
			
		} catch (EmptyStringException | EmptyBooleanException e) {
			e.printStackTrace();
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
			
			Outpost.rewardTime = ConfigManager.getIntFromConfig(Main.config, "Outpost.Time_Per_Reward");
			Outpost.Coin_Reward = ConfigManager.getIntFromConfig(Main.config, "Outpost.Coin_Reward");
						
			TownManager.Town_Create_Payment = ConfigManager.getIntFromConfig(Main.config, "Coins.Town_Starter_Cost");
			TownManager.Town_Claim_Payment = ConfigManager.getIntFromConfig(Main.config, "Coins.Town_Claim_Cost");
			TownManager.Claim_Limit = ConfigManager.getIntFromConfig(Main.config, "Town.Town_Claim_Limit");
			
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
		FileConfiguration config = main.getConfig();
		try {
			config.save(main.getDataFolder() + File.separator + "config.yml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
