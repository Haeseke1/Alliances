package me.Haeseke1.Alliances.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Utils.ConfigManager;


public class Main extends JavaPlugin{
	
	public static FileConfiguration config;
	public static String cmdlogo;
	public static PluginManager pm = Bukkit.getPluginManager();
	
	@Override
	public void onEnable() {
		config = getConfig();
		try {
			cmdlogo = ConfigManager.getStringFromConfig(config, "Logo");
		} catch (EmptyStringException e) {
			e.printStackTrace();
			pm.disablePlugin(this);
			return;
		}
		System.out.println("[" + this.getName() + "] The plugin is doing fine... *-* The cake is a lie *-*");
	}
	


	@Override
	public void onDisable() {
		
	}
	
	public void registerEvents(){
		
	}
	
	public void registerCommands(){
		
	}
}
