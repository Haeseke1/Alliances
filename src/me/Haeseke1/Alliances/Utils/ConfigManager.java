package me.Haeseke1.Alliances.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;

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

	public static Integer getIntFromConfig(FileConfiguration config, String path) throws EmptyStringException {
		if (config.getString(path) == null) {
			throw new EmptyStringException(path);
		}
		return config.getInt(path);
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
		// fetch colorcodes
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

		// fetch coin information
		try {
			Coins.defaultCoins = getIntFromConfig(Main.config, "Coins.StarterCoins");
		} catch (EmptyStringException e) {
			e.printStackTrace();
			Main.pm.disablePlugin(main);
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
