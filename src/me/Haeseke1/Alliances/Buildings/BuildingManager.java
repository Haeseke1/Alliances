package me.Haeseke1.Alliances.Buildings;


import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Buildings.Type.Storage.StorageManager;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class BuildingManager {

	
	public static boolean isMainBlock(Location loc){
		for(Building building : Building.buildings){
			if(building.mainBlock.getBlock().equals(loc.getBlock())){
				return true;
			}
		}
		return false;
	}
	
	public static Building getBuilding(Location loc){
		for(Building building : Building.buildings){
			if(building.mainBlock.getBlock().equals(loc.getBlock())){
				return building;
			}
		}
		return null;
	}
	
	
	public static void registerBuildings(){
		StorageManager.registerStorage();
		
		FileConfiguration file = Main.BuildingConfig;
		for(String key : file.getConfigurationSection("Buildings").getKeys(false)){
			Location loc = null;
			int y = 0;
			Chunk chunk = null;
			String type = null;
			try {
				loc = ConfigManager.getLocationFromConfig(file, "Buildings." + key + ".mainBlock");
				y = ConfigManager.getIntFromConfig(file, "Buildings." + key + ".y");
				String world = ConfigManager.getStringFromConfig(file, "Buildings." + key + ".chunk.world");
				type = ConfigManager.getStringFromConfig(file, "Buildings." + key + ".type");
				chunk = Bukkit.getWorld(world).getChunkAt(ConfigManager.getIntFromConfig(file, "Buildings." + key + ".chunk.x"), ConfigManager.getIntFromConfig(file, "Buildings." + key + ".chunk.z"));
			} catch (EmptyLocationException | EmptyIntException | EmptyStringException e) {
				e.printStackTrace();
			}
			new Building(loc, chunk, y, BuildingType.getType(type));
		}
	}
	
	public static void saveBuildings(){
		StorageManager.saveStorage();
		
		FileConfiguration file = Main.BuildingConfig;
		int i = 0;
		file.set("Buildings", null);
		for(Building b : Building.buildings){
			ConfigManager.setLocationFromConfig(file, "Buildings." + i + ".mainBlock", b.mainBlock);
			file.set("Buildings." + i + ".y", b.y);
			file.set("Buildings." + i + ".chunk.world", b.chunk.getWorld().getName());
			file.set("Buildings." + i + ".chunk.x", b.chunk.getX());
			file.set("Buildings." + i + ".chunk.z", b.chunk.getZ());
			file.set("Buildings." + i + ".type", b.type);
			i++;
		}
	}
	
	
}
