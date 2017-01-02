package me.Haeseke1.Alliances.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;

public class ConfigManager {
	
	/*
	 * Retrieves a string from a config file
	 */
	public static String getStringFromConfig(FileConfiguration config, String path) throws EmptyStringException{
		if(config.getString(path) == null){ throw new EmptyStringException(path);}
		return config.getString(path);
	}
	
	/*
	 * Register or reload config file
	 */
	public static void registerConfigFile(Main main){
		main.reloadConfig();
		//fetch logo
		try {
			Main.cmdlogo = MessageManager.translateColorCode(ConfigManager.getStringFromConfig(Main.config, "Logo"));
		} catch (EmptyStringException e) {
			e.printStackTrace();
			Main.pm.disablePlugin(main);
			return;
		}
		//fetch colorcodes
		try {
			MessageManager.alertColorCode = MessageManager.translateColorCode(getStringFromConfig(Main.config, "ColorCodes.alertMessages"));
			MessageManager.infoColorCode = MessageManager.translateColorCode(getStringFromConfig(Main.config, "ColorCodes.infoMessages"));
			MessageManager.remarkColorCode = MessageManager.translateColorCode(getStringFromConfig(Main.config, "ColorCodes.remarkMessages"));
		} catch (EmptyStringException e) {
			e.printStackTrace();
			Main.pm.disablePlugin(main);
		}
		
		
		//create configs into plugin folder
		File dir = main.getDataFolder();
		if(!dir.exists()){
			if(!dir.mkdir()){
				MessageManager.sendAlertMessage("Couldn't create the plugin folder...");
			}else{
				main.saveDefaultConfig();
				MessageManager.sendRemarkMessage("The config is succesfully created!");
			}
		}else{
			MessageManager.sendInfoMessage("Configs are ready to go!");
		}
		
	}
    /*
     * Creates a config file 
     */
	public static FileConfiguration creatYamlConfig(String name, Main main) throws IOException{
	  if(name.contains(".yml")){
		File file = new File(main.getDataFolder(),name);
		if(file.exists()){
			MessageManager.sendRemarkMessage("The " + name + " file has been loaded");
			return YamlConfiguration.loadConfiguration(file);	
		}
		main.saveResource(name, false);
		FileConfiguration customConfig = YamlConfiguration.loadConfiguration(file);
		customConfig.setDefaults(customConfig);
		customConfig.options().copyDefaults(true);
		MessageManager.sendRemarkMessage("The " + name + " file has been created");
		return customConfig;	
	  }else{
		  return null;
	  }
	}
	
	/*
	 * Save changes made to the config file
	 */
	public static void saveConfigFile(Main main){
		main.saveDefaultConfig();
	}
	

}
