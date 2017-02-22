package me.Haeseke1.Alliances.Outpost;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class OutpostManager {
	
	
	public static int Reward_Exp;
	
	
	public static boolean checkLocation(Location loc1, Location loc2){
		int xmin = 0;
		int xmax = 0;
		int zmin = 0;
		int zmax = 0;
		if(loc1.getBlockX() > loc2.getBlockX()){
			xmin = loc2.getBlockX();
			xmax = loc1.getBlockX();
		}else{
			xmin = loc1.getBlockX();
			xmax = loc2.getBlockX();
		}
		if(loc1.getBlockZ() > loc2.getBlockZ()){
			zmin = loc2.getBlockZ();
			zmax = loc1.getBlockZ();
		}else{
			zmin = loc1.getBlockZ();
			zmax = loc2.getBlockZ();
		}
		for(Outpost f : Outpost.outposts){
			if((((f.xmin <= xmin && f.xmax >= xmin) || (f.xmin <= xmax && f.xmax >= xmax)) &&
					((f.zmin <= zmin && f.zmax >= zmin) || (f.zmin <= zmax && f.zmax >= zmax))) ||
					(((xmin <= f.xmin && xmax >= f.xmin) || (xmin <= f.xmax && xmax >= f.xmax)) &&
					((zmin <= f.zmin && zmax >= f.zmin) || (zmin <= f.zmax && zmax >= f.zmax)))){
				return false;
			}
		}
		return true;
	}
	
	
	public static boolean checkLocation(Location b){
		for(Outpost f : Outpost.outposts){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return true;
				}
			}
		}
		return false;
	}
	
	public static Outpost getOutpost(Location b){
		for(Outpost f : Outpost.outposts){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	public static void registerOutpost(){
		Outpost.outposts.clear();
		FileConfiguration file = Main.outpostConfig;
		if(file.contains("Outposts")){
			for(String s : file.getConfigurationSection("Outposts").getKeys(false)){
				String name = s;
				Alliance alli = null;
				if(file.contains("Outposts." + s + ".Owner")){
					try {
						alli = AllianceManager.getAlliance(ConfigManager.getStringFromConfig(file, "Outposts." + s + ".Owner"));
					} catch (EmptyStringException e) {
						e.printStackTrace();
					}
				}
				World world = null;
				try {
					world = Bukkit.getWorld(ConfigManager.getStringFromConfig(file, "Outposts." + s + ".World"));
				} catch (EmptyStringException e) {
					e.printStackTrace();
				}
				int xmin = 0;
				int xmax = 0;
				int zmin = 0;
				int zmax = 0;
				try {
					xmin = ConfigManager.getIntFromConfig(file, "Outposts." + s + ".Xmin");
					xmax = ConfigManager.getIntFromConfig(file, "Outposts." + s + ".Xmax");
					zmin = ConfigManager.getIntFromConfig(file, "Outposts." + s + ".Zmin");
					zmax = ConfigManager.getIntFromConfig(file, "Outposts." + s + ".Zmax");
				} catch (EmptyIntException e) {
					e.printStackTrace();
				}
				new Outpost(name,world,xmin,xmax,zmin,zmax,alli);
			}
		}
	}
	
	public static void saveOutpost(){
		FileConfiguration file = Main.outpostConfig;
		for(Outpost f : Outpost.outposts){
			if(f.owner != null){
				file.set("Outposts." + f.name + ".Owner", f.owner.getName());
			}
			file.set("Outposts." + f.name + ".World", f.world.getName());
			file.set("Outposts." + f.name + ".Xmin", f.xmin);
			file.set("Outposts." + f.name + ".Xmax", f.xmax);
			file.set("Outposts." + f.name + ".Zmin", f.zmin);
			file.set("Outposts." + f.name + ".Zmax", f.zmax);
		}
	}
	
	
}
