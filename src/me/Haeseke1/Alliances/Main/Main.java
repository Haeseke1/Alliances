package me.Haeseke1.Alliances.Main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;


public class Main extends JavaPlugin{
	
	public static FileConfiguration config;
	public static String cmdlogo;
	public static PluginManager pm = Bukkit.getPluginManager();
	
	@SuppressWarnings("static-access")
	@Override
	public void onEnable() {
		this.config = getConfig();
		ConfigManager.registerConfigFile(this);
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
}
