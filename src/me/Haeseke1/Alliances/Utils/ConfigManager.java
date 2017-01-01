package me.Haeseke1.Alliances.Utils;

import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Exceptions.EmptyStringException;

public class ConfigManager {
	
	/*
	 * Retrieves a string from a config file
	 */
	public static String getStringFromConfig(FileConfiguration config, String path) throws EmptyStringException{
		if(config.getString(path) == null){ throw new EmptyStringException(path);}
		return config.getString(path);
	}
}
