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
import me.Haeseke1.Alliances.Challenge.Type.Block_Breaking;
import me.Haeseke1.Alliances.Challenge.Type.Block_Placing;
import me.Haeseke1.Alliances.Challenge.Type.Distance_Travel;
import me.Haeseke1.Alliances.Challenge.Type.Enchanting;
import me.Haeseke1.Alliances.Challenge.Type.Fishing;
import me.Haeseke1.Alliances.Challenge.Type.Lost_Hearts;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Count;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Enderman;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Skeleton;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Time;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Wither;
import me.Haeseke1.Alliances.Challenge.Type.Mob_Killing_Zombie;
import me.Haeseke1.Alliances.Challenge.Type.Player_Kill;
import me.Haeseke1.Alliances.Challenge.Type.Time_On;
import me.Haeseke1.Alliances.Commands.Alli;
import me.Haeseke1.Alliances.Commands.Create.InventoryEvents;
import me.Haeseke1.Alliances.Exceptions.InvalidConfigTypeException;
import me.Haeseke1.Alliances.Outpost.OutpostEvents;
import me.Haeseke1.Alliances.Outpost.OutpostManager;
import me.Haeseke1.Alliances.Outpost.Timer;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

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
		registerSchedulers();
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
		pm.registerEvents(new me.Haeseke1.Alliances.Commands.Outpost.Create.InventoryEvents(), this);
		pm.registerEvents(new me.Haeseke1.Alliances.Commands.Join.InventoryEvents(), this);
		pm.registerEvents(new OutpostEvents(), this);
		pm.registerEvents(new regionSelect(), this);
		
		pm.registerEvents(new Block_Breaking(), this);
		pm.registerEvents(new Block_Placing(), this);
		pm.registerEvents(new Distance_Travel(), this);
		pm.registerEvents(new Enchanting(), this);
		pm.registerEvents(new Fishing(), this);
		pm.registerEvents(new Lost_Hearts(), this);
		pm.registerEvents(new Mob_Killing_Count(), this);
		pm.registerEvents(new Mob_Killing_Enderman(), this);
		pm.registerEvents(new Mob_Killing_Skeleton(), this);
		pm.registerEvents(new Mob_Killing_Time(), this);
		pm.registerEvents(new Mob_Killing_Wither(), this);
		pm.registerEvents(new Mob_Killing_Zombie(), this);
		pm.registerEvents(new Player_Kill(), this);
	}

	public void registerCommands() {
		getCommand("Alliances").setExecutor(new Alli());
	}
	
	public void registerSchedulers(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Timer(), 20, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Time_On(), 20, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Mob_Killing_Time(), 20, 20);
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
