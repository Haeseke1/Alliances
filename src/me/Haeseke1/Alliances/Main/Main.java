package me.Haeseke1.Alliances.Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Haeseke1.Alliances.Exceptions.InvalidConfigTypeException;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;


public class Main extends JavaPlugin{
	
	public static FileConfiguration config;
	public static String cmdlogo;
	public static PluginManager pm = Bukkit.getPluginManager();
	public static List<FileConfiguration> configFiles;
	
	public static FileConfiguration coins;
	
	
	@SuppressWarnings("static-access")
	@Override
	public void onEnable() {
		this.config = getConfig();
		try {
			createConfigs();
		} catch (IOException e) {
            MessageManager.sendAlertMessage("There was a problem with loading in the config file");
            pm.disablePlugin(this);
			e.printStackTrace();
			return;
		}catch(InvalidConfigTypeException icte){
			icte.printStackTrace();
			MessageManager.sendAlertMessage("There was a problem in the code. Ask a dev for more information or download an earlier version of this plugin");
			pm.disablePlugin(this);
			return;
		}
		ConfigManager.registerConfigFile(this);
		saveAllCustomConfigs();
		MessageManager.sendRemarkMessage("The plugin is doing fine... *-* The cake is a lie *-*");
		MessageManager.sendAlertMessage("The plugin is doing fine... *-* The cake is a lie *-*");
		MessageManager.sendInfoMessage("The plugin is doing fine... *-* The cake is a lie *-*");
	}
	
	@Override
	public void onDisable() {
		ConfigManager.saveConfigFile(this);
		MessageManager.sendAlertMessage("The plugin has been shutted down! *-* The cake wasn't a lie thought *-*");
	}
	
	public void registerEvents(){
		
	}
	
	public void registerCommands(){
		
	}
	
	/*
	 * Creates all the needed configs of the code (JSON support not included)
	 */
	public void createConfigs() throws IOException, InvalidConfigTypeException{
	   coins = ConfigManager.creatYamlConfig("coins.yml", this);
	}
	
	public void saveAllCustomConfigs(){
		for(FileConfiguration config: configFiles){
			try {
				ConfigManager.saveCustomConfig(config, this);
			} catch (IOException e) {
				MessageManager.sendAlertMessage("Couldn't save the custom config");
				pm.disablePlugin(this);
				e.printStackTrace();
				return;
			}
		}
	}
	
}
