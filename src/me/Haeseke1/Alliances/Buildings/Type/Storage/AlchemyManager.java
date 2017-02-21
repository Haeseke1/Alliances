package me.Haeseke1.Alliances.Buildings.Type.Storage;


import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class AlchemyManager {
	
	public static Alchemy createAlchemy(Building b){
		Alchemy s = new Alchemy(b.mainBlock, b.chunk, b.ymin, b.ymax);
		Alchemy.buildings.remove(b);
		Alchemy.Allbuildings.remove(b);
		return s;
	}
	
	public static void registerAlchemy(){
		FileConfiguration file = Main.BuildingConfig;
		if(file.contains("Alchemy")){
			for(String key : file.getConfigurationSection("Alchemy").getKeys(false)){
				Location loc = null;
				int ymin = 0;
				int ymax = 0;
				Chunk chunk = null;
				try {
					loc = ConfigManager.getLocationFromConfig(file, "Alchemy." + key + ".mainBlock");
					ymin = ConfigManager.getIntFromConfig(file, "Alchemy." + key + ".ymin");
					ymax = ConfigManager.getIntFromConfig(file, "Alchemy." + key + ".ymax");
					String world = ConfigManager.getStringFromConfig(file, "Alchemy." + key + ".chunk.world");
					chunk = Bukkit.getWorld(world).getChunkAt(ConfigManager.getIntFromConfig(file, "Alchemy." + key + ".chunk.x"), ConfigManager.getIntFromConfig(file, "Alchemy." + key + ".chunk.z"));
				} catch (EmptyLocationException | EmptyIntException | EmptyStringException e) {
					e.printStackTrace();
				}
				new Alchemy(loc, chunk, ymin,ymax);
			}
		}
	}
	
	public static void saveAlchemy(){
		FileConfiguration file = Main.BuildingConfig;
		int i = 0;
		file.set("Alchemy", null);
		for(Building b : Building.buildings){
			ConfigManager.setLocationFromConfig(file, "Alchemy." + i + ".mainBlock", b.mainBlock);
			file.set("Alchemy." + i + ".ymin", b.ymin);
			file.set("Alchemy." + i + ".ymax", b.ymax);
			file.set("Alchemy." + i + ".chunk.world", b.chunk.getWorld().getName());
			file.set("Alchemy." + i + ".chunk.x", b.chunk.getX());
			file.set("Alchemy." + i + ".chunk.z", b.chunk.getZ());
			i++;
		}
	}
	
}
