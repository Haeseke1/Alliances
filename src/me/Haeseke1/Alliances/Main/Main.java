package me.Haeseke1.Alliances.Main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;


public class Main extends JavaPlugin{
	
	public static FileConfiguration config;
	public static String cmdlogo;
	public static PluginManager pm = Bukkit.getPluginManager();
	
	public static FileConfiguration coins;
	
	
	@SuppressWarnings("static-access")
	@Override
	public void onEnable() {
		this.config = getConfig();
		ConfigManager.registerConfigFile(this);
		try {
			createConfigs();
		} catch (IOException e) {
            MessageManager.sendAlertMessage("There was a problem with loading in the config file");
            pm.disablePlugin(this);
			e.printStackTrace();
		}
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
	
	public void createConfigs() throws IOException{
		coins = ConfigManager.creatYamlConfig("coins.yml", this);
	}
	
}
