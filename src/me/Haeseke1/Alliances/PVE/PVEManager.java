package me.Haeseke1.Alliances.PVE;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class PVEManager {
	
	
	
	public static void registerPVE() {
		FileConfiguration file = Main.PVEConfig;
		if(!file.contains("PVE.Main.Lobby")){
			return;
		}
		try {
			new PVE(ConfigManager.getLocationWithYaw_PitchFromConfig(file, "PVE.Main.Lobby"));
		} catch (EmptyLocationException e) {
			e.printStackTrace();
		}
		
		if(!file.contains("PVE.Arenas")){
			return;
		}
		for(String s : file.getConfigurationSection("PVE.Arenas").getKeys(false)){
			String name = s;		
			try {
				World world = Bukkit.getWorld(ConfigManager.getStringFromConfig(file, "PVE.Arenas." + s + ".World"));
				int xmin = ConfigManager.getIntFromConfig(file, "PVE.Arenas." + s + ".Xmin");
				int xmax = ConfigManager.getIntFromConfig(file, "PVE.Arenas." + s + ".Xmax");
				int zmin = ConfigManager.getIntFromConfig(file, "PVE.Arenas." + s + ".Zmin");
				int zmax = ConfigManager.getIntFromConfig(file, "PVE.Arenas." + s + ".Zmax");
				List<Location> PlayerSpawns = new ArrayList<>();
				List<Location> MobSpawns = new ArrayList<>();
				if(file.contains("PVE.Arenas." + s + ".PlayerSpawns")){
					for(String loc : file.getConfigurationSection("PVE.Arenas." + s + ".PlayerSpawns").getKeys(false)){
						PlayerSpawns.add(ConfigManager.getLocationWithYaw_PitchFromConfig(file, "PVE.Arenas." + s + ".PlayerSpawns." + loc));
					}
				}
				if(file.contains("PVE.Arenas." + s + ".MobSpawns")){
					for(String loc : file.getConfigurationSection("PVE.Arenas." + s + ".MobSpawns").getKeys(false)){
						MobSpawns.add(ConfigManager.getLocationWithYaw_PitchFromConfig(file, "PVE.Arenas." + s + ".MobSpawns." + loc));
					}
				}
				new Arena(name, world, xmin, xmax, zmin, zmax, PlayerSpawns, MobSpawns);
			} catch (EmptyIntException | EmptyStringException | EmptyLocationException e1) {
				
			}
		}
	}

	public static void  disablePVE(){
		if(PVE.main == null) return;
		if(PVE.main.arenas == null) return;
		for(Arena arena : PVE.main.arenas){
			arena.forceStopArena();
		}
		if(Group.groups == null) return;
		for(Group group : Group.groups){
			group.disband();
		}
	}
	
	public static void savePVE() {
		FileConfiguration file = Main.PVEConfig;
		if(PVE.main == null){
			return;
		}
		for(String s : file.getKeys(false)){
			file.set(s, null);
		}
		ConfigManager.setLocationWithYaw_PitchFromConfig(file, "PVE.Main.Lobby", PVE.main.lobby);
		if(!PVE.main.arenas.isEmpty()){
			for(Arena arena : PVE.main.arenas){
				file.set("PVE.Arenas." + arena.name + ".World", arena.world.getName());
				file.set("PVE.Arenas." + arena.name + ".Xmin", arena.xmin);
				file.set("PVE.Arenas." + arena.name + ".Xmax", arena.xmax);
				file.set("PVE.Arenas." + arena.name + ".Zmin", arena.zmin);
				file.set("PVE.Arenas." + arena.name + ".Zmax", arena.zmax);
				int i = 0;
				for(Location loc : arena.playerSpawns){
					ConfigManager.setLocationWithYaw_PitchFromConfig(file, "PVE.Arenas." + arena.name + ".PlayerSpawns." + i, loc);
					i++;
				}
				i = 0;
				for(Location loc : arena.mobSpawns){
					ConfigManager.setLocationWithYaw_PitchFromConfig(file, "PVE.Arenas." + arena.name + ".MobSpawns." + i, loc);
					i++;
				}
			}
		}
	}
	
	

}
