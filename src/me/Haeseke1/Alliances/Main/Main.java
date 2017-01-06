package me.Haeseke1.Alliances.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Commands.Alli;
import me.Haeseke1.Alliances.Commands.Create.InventoryEvents;
import me.Haeseke1.Alliances.Exceptions.InvalidConfigTypeException;
import me.Haeseke1.Alliances.Outpost.OutpostEvents;
import me.Haeseke1.Alliances.Outpost.OutpostManager;
import me.Haeseke1.Alliances.Outpost.Timer;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Main extends JavaPlugin {

	public static FileConfiguration config;
	public static String cmdlogo;
	public static PluginManager pm = Bukkit.getPluginManager();

	public static HashMap<String, FileConfiguration> configFiles = new HashMap<String, FileConfiguration>();
	public static List<Alliance> alliances = new ArrayList<Alliance>();

	public static FileConfiguration coinsConfig;
	public static FileConfiguration alliancesConfig;
	public static FileConfiguration outpostConfig;

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
		} catch (InvalidConfigTypeException icte) {
			icte.printStackTrace();
			MessageManager.sendAlertMessage(
					"There was a problem in the code. Ask a dev for more information or download an earlier version of this plugin");
			pm.disablePlugin(this);
			return;
		}
		ConfigManager.registerConfigFile(this);
		registerCommands();
		registerEvents();
		MessageManager.sendRemarkMessage("The plugin is doing fine... *-* The cake is a lie *-*");
		MessageManager.sendAlertMessage("The plugin is doing fine... *-* The cake is a lie *-*");
		MessageManager.sendInfoMessage("The plugin is doing fine... *-* The cake is a lie *-*");
	}

	@Override
	public void onDisable() {
		ConfigManager.saveConfigFile(this);
		saveAllCustomConfigs();
		MessageManager.sendAlertMessage("The plugin has been shutted down! *-* The cake wasn't a lie thought *-*");
	}

	public void registerEvents() {
		pm.registerEvents(new InventoryEvents(), this);
		pm.registerEvents(new OutpostEvents(), this);
	}

	public void registerCommands() {
		getCommand("Alliances").setExecutor(new Alli());
	}
	
	public void registerSchedulers(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Timer(), 20, 20);
	}

	/*
	 * Creates all the needed configs of the code (JSON support not included)
	 */
	public void createConfigs() throws IOException, InvalidConfigTypeException {
		coinsConfig = ConfigManager.getCustomConfig(new File(getDataFolder(), "coins.yml"));
		alliancesConfig = ConfigManager.getCustomConfig(new File(getDataFolder(), "alliances.yml"));
		outpostConfig = ConfigManager.getCustomConfig(new File(getDataFolder(), "outpost.yml"));
		AllianceManager.registerAlliance();
		OutpostManager.registerOutpost();
	}

	public void saveAllCustomConfigs() {
		AllianceManager.saveAlliance();
		OutpostManager.saveOutpost();
		OutpostManager.saveRewards();
		for (Entry<String, FileConfiguration> entry : configFiles.entrySet()) {
			ConfigManager.saveCustomConfig(new File(getDataFolder(), entry.getKey()), entry.getValue());
		}
	}

}
