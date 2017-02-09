package me.Haeseke1.Alliances.Buildings;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Buildings.Type.Storage.Storage;
import me.Haeseke1.Alliances.Buildings.Type.Storage.StorageManager;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Town.Town;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class BuildingManager {

	
	public static boolean isMainBlock(Location loc){
		for(Building building : Building.Allbuildings){
			if(building.mainBlock.getBlock().equals(loc.getBlock())){
				return true;
			}
		}
		return false;
	}
	
	public static Building getBuilding(Location loc){
		for(Building building : Building.Allbuildings){
			if(building.mainBlock.getBlock().equals(loc.getBlock())){
				return building;
			}
		}
		return null;
	}
	
	
	public static boolean hasBuilding(Chunk chunk){
		for(Building b : Building.Allbuildings){
			if(b.chunk.equals(chunk)){
				return true;
			}
		}
		return false;
	}
	
	public static Building getBuilding(Chunk chunk){
		for(Building b : Building.Allbuildings){
			if(b.chunk.equals(chunk)){
				return b;
			}
		}
		return null;
	}
	
	
	
	public static void registerBuildings(){
		Building.Allbuildings = new ArrayList<Building>();
		Building.buildings = new ArrayList<Building>();
		Storage.storages = new ArrayList<Storage>();
		
		StorageManager.registerStorage();
		
		FileConfiguration file = Main.BuildingConfig;
		if(!file.contains("Buildings")){
			return;
		}
		for(String key : file.getConfigurationSection("Buildings").getKeys(false)){
			Location loc = null;
			int ymin = 0;
			int ymax = 0;
			Chunk chunk = null;
			String type = null;
			try {
				loc = ConfigManager.getLocationFromConfig(file, "Buildings." + key + ".mainBlock");
				ymin = ConfigManager.getIntFromConfig(file, "Buildings." + key + ".ymin");
				ymax = ConfigManager.getIntFromConfig(file, "Buildings." + key + ".ymax");
				String world = ConfigManager.getStringFromConfig(file, "Buildings." + key + ".chunk.world");
				type = ConfigManager.getStringFromConfig(file, "Buildings." + key + ".type");
				chunk = Bukkit.getWorld(world).getChunkAt(ConfigManager.getIntFromConfig(file, "Buildings." + key + ".chunk.x"), ConfigManager.getIntFromConfig(file, "Buildings." + key + ".chunk.z"));
			} catch (EmptyLocationException | EmptyIntException | EmptyStringException e) {
				e.printStackTrace();
			}
			new Building(loc, chunk, ymin,ymax, BuildingType.getType(type), false);
		}
		Town.unclaimable.clear();
		for(String key : file.getConfigurationSection("Unclaimable").getKeys(false)){
			String world = null;
			try {
				world = ConfigManager.getStringFromConfig(file, "Unclaimable." + key + "." + key +  ".world");
				Town.unclaimable.add(Bukkit.getWorld(world).getChunkAt(ConfigManager.getIntFromConfig(file, "Unclaimable." + key + ".x"), ConfigManager.getIntFromConfig(file, "Unclaimable." + key + "." + key +  ".z")));
			} catch (EmptyStringException | EmptyIntException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void saveBuildings(){
		StorageManager.saveStorage();
		
		FileConfiguration file = Main.BuildingConfig;
		int i = 0;
		file.set("Buildings", null);
		for(Building b : Building.buildings){
			ConfigManager.setLocationFromConfig(file, "Buildings." + i + ".mainBlock", b.mainBlock);
			file.set("Buildings." + i + ".ymin", b.ymin);
			file.set("Buildings." + i + ".ymax", b.ymax);
			file.set("Buildings." + i + ".chunk.world", b.chunk.getWorld().getName());
			file.set("Buildings." + i + ".chunk.x", b.chunk.getX());
			file.set("Buildings." + i + ".chunk.z", b.chunk.getZ());
			file.set("Buildings." + i + ".type", b.type.toString());
			i++;
		}
		file.set("Unclaimable", null);
		i = 0;
		for(Chunk chunk : Town.unclaimable){
			file.set("Unclaimable." + i + ".world", chunk.getWorld().getName());
			file.set("Unclaimable." + i + ".x", chunk.getX());
			file.set("Unclaimable." + i + ".z", chunk.getZ());
			i++;
		}
		
	}
	
	
}
