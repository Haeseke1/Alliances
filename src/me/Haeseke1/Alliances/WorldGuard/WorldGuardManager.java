package me.Haeseke1.Alliances.WorldGuard;

import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class WorldGuardManager {

	private static FileConfiguration config = Main.WorldGuardConfig;
	
	public static void setupWorldGuard(){
		int region_count = config.getConfigurationSection("Regions").getKeys(false).size();
		for(String region_number: config.getConfigurationSection("Regions").getKeys(false)){
			
		}
		if(region_count == 1){
			MessageManager.sendRemarkMessage(" &6WorldGuard &2" + region_count + " region registered!");
			return;
		}
		if(region_count == 0){
			MessageManager.sendAlertMessage(" &6WorldGuard &4no regions selected");
			return;
		}
		MessageManager.sendRemarkMessage(" &6WorldGuard &2" + region_count + " region registered!");
	}
	
}
