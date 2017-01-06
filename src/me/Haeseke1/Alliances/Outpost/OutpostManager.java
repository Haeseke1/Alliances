package me.Haeseke1.Alliances.Outpost;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Alliance;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Outpost.Type.Blacksmith;
import me.Haeseke1.Alliances.Outpost.Type.Dock;
import me.Haeseke1.Alliances.Outpost.Type.Farm;
import me.Haeseke1.Alliances.Outpost.Type.God;
import me.Haeseke1.Alliances.Outpost.Type.Magic_Tower;
import me.Haeseke1.Alliances.Outpost.Type.Mine;
import me.Haeseke1.Alliances.Outpost.Type.Mob_Farm;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class OutpostManager {
	
	
	public static void giveReward(){
		for(Farm f : Farm.farms){
			if(f.owner != null){
				f.owner.addReward(chooseReward(Farm.rewards));
			}
		}
		for(Blacksmith b : Blacksmith.blacksmiths){
			if(b.owner != null){
				b.owner.addReward(chooseReward(Blacksmith.rewards));
			}
		}
		for(Dock d : Dock.docks){
			if(d.owner != null){
				d.owner.addReward(chooseReward(Dock.rewards));
			}
		}
		for(God g : God.gods){
			if(g.owner != null){
				g.owner.addReward(chooseReward(God.rewards));
			}
		}
		for(Magic_Tower mt : Magic_Tower.magic_towers){
			if(mt.owner != null){
				mt.owner.addReward(chooseReward(Magic_Tower.rewards));
			}
		}
		for(Mine m : Mine.mines){
			if(m.owner != null){
				m.owner.addReward(chooseReward(Mine.rewards));
			}
		}
		for(Mob_Farm mf : Mob_Farm.mob_farms){
			if(mf.owner != null){
				mf.owner.addReward(chooseReward(Mob_Farm.rewards));
			}
		}
	}
	
	public static void checkPlayers(){
		for(Player player : Bukkit.getOnlinePlayers()){
			Location loc = player.getLocation();
			Farm f = checkFarms(loc);
			if(f != null){
				f.inOutpost.add(player);
			}
			Blacksmith b = checkBlacksmiths(loc);
			if(b != null){
				b.inOutpost.add(player);
			}
			Dock d = checkDocks(loc);
			if(d != null){
				d.inOutpost.add(player);
			}
			God g = checkGods(loc);
			if(g != null){
				g.inOutpost.add(player);
			}
			Magic_Tower mt = checkMagic_Towers(loc);
			if(mt != null){
				mt.inOutpost.add(player);
			}
			Mine m = checkMines(loc);
			if(m != null){
				m.inOutpost.add(player);
			}
			Mob_Farm mf = checkMob_Farms(loc);
			if(mf != null){
				mf.inOutpost.add(player);
			}
		}
	}
	
	
	
	private static Farm checkFarms(Location b){
		for(Farm f : Farm.farms){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	private static Blacksmith checkBlacksmiths(Location b){
		for(Blacksmith f : Blacksmith.blacksmiths){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	
	private static Dock checkDocks(Location b){
		for(Dock f : Dock.docks){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	private static God checkGods(Location b){
		for(God f : God.gods){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	private static Magic_Tower checkMagic_Towers(Location b){
		for(Magic_Tower f : Magic_Tower.magic_towers){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	private static Mine checkMines(Location b){
		for(Mine f : Mine.mines){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	private static Mob_Farm checkMob_Farms(Location b){
		for(Mob_Farm f : Mob_Farm.mob_farms){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	public static boolean checkLocation(Location b){
		for(Farm f : Farm.farms){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(Blacksmith bs : Blacksmith.blacksmiths){
			if(bs.world.equals(b.getWorld())){
				if(bs.xmin <= b.getX() && bs.xmax >= b.getX() &&
						bs.zmin <= b.getZ() && bs.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(Dock d : Dock.docks){
			if(d.world.equals(b.getWorld())){
				if(d.xmin <= b.getX() && d.xmax >= b.getX() &&
						d.zmin <= b.getZ() && d.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(God g : God.gods){
			if(g.world.equals(b.getWorld())){
				if(g.xmin <= b.getX() && g.xmax >= b.getX() &&
						g.zmin <= b.getZ() && g.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(Magic_Tower mt : Magic_Tower.magic_towers){
			if(mt.world.equals(b.getWorld())){
				if(mt.xmin <= b.getX() && mt.xmax >= b.getX() &&
						mt.zmin <= b.getZ() && mt.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(Mine m : Mine.mines){
			if(m.world.equals(b.getWorld())){
				if(m.xmin <= b.getX() && m.xmax >= b.getX() &&
						m.zmin <= b.getZ() && m.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(Mob_Farm mf : Mob_Farm.mob_farms){
			if(mf.world.equals(b.getWorld())){
				if(mf.xmin <= b.getX() && mf.xmax >= b.getX() &&
						mf.zmin <= b.getZ() && mf.zmax >= b.getZ()){
					return true;
				}
			}
		}
		return false;
	}
	
	public static void updateTakeOver(){
		for(Farm f : Farm.farms){
			f.take_over();
		}
		for(Blacksmith b : Blacksmith.blacksmiths){
			b.take_over();
		}
		for(Dock d : Dock.docks){
			d.take_over();
		}
		for(God g : God.gods){
			g.take_over();
		}
		for(Magic_Tower mt : Magic_Tower.magic_towers){
			mt.take_over();
		}
		for(Mine m : Mine.mines){
			m.take_over();
		}
		for(Mob_Farm mf : Mob_Farm.mob_farms){
			mf.take_over();
		}
	}
	
	
	
	
	private static ItemStack chooseReward(HashMap<ItemStack,Integer> rewards){
		ItemStack reward = null;
		
		int som = 0;
		for(Entry<ItemStack,Integer> entry : rewards.entrySet()){
			som += entry.getValue();
		}
		
		Double random = Math.random() * som + 0.1;
		int lootnumber = 0;
		for(Entry<ItemStack,Integer> entry : rewards.entrySet()){
			lootnumber += entry.getValue();
			if(random < lootnumber){
				reward = entry.getKey();
				break;
			}
		}
		return reward;
	}
	
	public static void registerOutpost(){
		FileConfiguration file = Main.outpostConfig;
		if(file.contains("Blacksmith")){
			for(String s : file.getConfigurationSection("Blacksmith").getKeys(false)){
				String name = s;
				Alliance alli = null;
				if(file.contains("Blacksmith" + s + ".Owner")){
					try {
						alli = AllianceManager.getAlliance(ConfigManager.getStringFromConfig(file, "Blacksmith." + s + ".Owner"));
					} catch (EmptyStringException e) {
						e.printStackTrace();
					}
				}
				World world = null;
				try {
					world = Bukkit.getWorld(ConfigManager.getStringFromConfig(file, "Blacksmith." + s + ".World"));
				} catch (EmptyStringException e) {
					e.printStackTrace();
				}
				int xmin = 0;
				int xmax = 0;
				int zmin = 0;
				int zmax = 0;
				try {
					xmin = ConfigManager.getIntFromConfig(file, "Blacksmith." + s + ".Xmin");
					xmax = ConfigManager.getIntFromConfig(file, "Blacksmith." + s + ".Xmax");
					zmin = ConfigManager.getIntFromConfig(file, "Blacksmith." + s + ".Zmin");
					zmax = ConfigManager.getIntFromConfig(file, "Blacksmith." + s + ".Zmax");
				} catch (EmptyIntException e) {
					e.printStackTrace();
				}
				new Blacksmith(name,world,xmin,xmax,zmin,zmax,alli);
			}
		}
		if(file.contains("Dock")){
			for(String s : file.getConfigurationSection("Dock").getKeys(false)){
				String name = s;
				Alliance alli = null;
				if(file.contains("Dock" + s + ".Owner")){
					try {
						alli = AllianceManager.getAlliance(ConfigManager.getStringFromConfig(file, "Dock." + s + ".Owner"));
					} catch (EmptyStringException e) {
						e.printStackTrace();
					}
				}
				World world = null;
				try {
					world = Bukkit.getWorld(ConfigManager.getStringFromConfig(file, "Dock." + s + ".World"));
				} catch (EmptyStringException e) {
					e.printStackTrace();
				}
				int xmin = 0;
				int xmax = 0;
				int zmin = 0;
				int zmax = 0;
				try {
					xmin = ConfigManager.getIntFromConfig(file, "Dock." + s + ".Xmin");
					xmax = ConfigManager.getIntFromConfig(file, "Dock." + s + ".Xmax");
					zmin = ConfigManager.getIntFromConfig(file, "Dock." + s + ".Zmin");
					zmax = ConfigManager.getIntFromConfig(file, "Dock." + s + ".Zmax");
				} catch (EmptyIntException e) {
					e.printStackTrace();
				}
				new Dock(name,world,xmin,xmax,zmin,zmax,alli);
			}
		}
		if(file.contains("Farm")){
			for(String s : file.getConfigurationSection("Farm").getKeys(false)){
				String name = s;
				Alliance alli = null;
				if(file.contains("Farm." + s + ".Owner")){
					try {
						alli = AllianceManager.getAlliance(ConfigManager.getStringFromConfig(file, "Farm." + s + ".Owner"));
					} catch (EmptyStringException e) {
						e.printStackTrace();
					}
				}
				World world = null;
				try {
					world = Bukkit.getWorld(ConfigManager.getStringFromConfig(file, "Farm." + s + ".World"));
				} catch (EmptyStringException e) {
					e.printStackTrace();
				}
				int xmin = 0;
				int xmax = 0;
				int zmin = 0;
				int zmax = 0;
				try {
					xmin = ConfigManager.getIntFromConfig(file, "Farm." + s + ".Xmin");
					xmax = ConfigManager.getIntFromConfig(file, "Farm." + s + ".Xmax");
					zmin = ConfigManager.getIntFromConfig(file, "Farm." + s + ".Zmin");
					zmax = ConfigManager.getIntFromConfig(file, "Farm." + s + ".Zmax");
				} catch (EmptyIntException e) {
					e.printStackTrace();
				}
				new Farm(name,world,xmin,xmax,zmin,zmax,alli);
			}
		}
		if(file.contains("God")){
			for(String s : file.getConfigurationSection("God").getKeys(false)){
				String name = s;
				Alliance alli = null;
				if(file.contains("God." + s + ".Owner")){
					try {
						alli = AllianceManager.getAlliance(ConfigManager.getStringFromConfig(file, "God." + s + ".Owner"));
					} catch (EmptyStringException e) {
						e.printStackTrace();
					}
				}
				World world = null;
				try {
					world = Bukkit.getWorld(ConfigManager.getStringFromConfig(file, "God." + s + ".World"));
				} catch (EmptyStringException e) {
					e.printStackTrace();
				}
				int xmin = 0;
				int xmax = 0;
				int zmin = 0;
				int zmax = 0;
				try {
					xmin = ConfigManager.getIntFromConfig(file, "God." + s + ".Xmin");
					xmax = ConfigManager.getIntFromConfig(file, "God." + s + ".Xmax");
					zmin = ConfigManager.getIntFromConfig(file, "God." + s + ".Zmin");
					zmax = ConfigManager.getIntFromConfig(file, "God." + s + ".Zmax");
				} catch (EmptyIntException e) {
					e.printStackTrace();
				}
				new God(name,world,xmin,xmax,zmin,zmax,alli);
			}
		}
		if(file.contains("Magic_Tower")){
			for(String s : file.getConfigurationSection("Magic_Tower").getKeys(false)){
				String name = s;
				Alliance alli = null;
				if(file.contains("Magic_Tower." + s + ".Owner")){
					try {
						alli = AllianceManager.getAlliance(ConfigManager.getStringFromConfig(file, "Magic_Tower." + s + ".Owner"));
					} catch (EmptyStringException e) {
						e.printStackTrace();
					}
				}
				World world = null;
				try {
					world = Bukkit.getWorld(ConfigManager.getStringFromConfig(file, "Magic_Tower." + s + ".World"));
				} catch (EmptyStringException e) {
					e.printStackTrace();
				}
				int xmin = 0;
				int xmax = 0;
				int zmin = 0;
				int zmax = 0;
				try {
					xmin = ConfigManager.getIntFromConfig(file, "Magic_Tower." + s + ".Xmin");
					xmax = ConfigManager.getIntFromConfig(file, "Magic_Tower." + s + ".Xmax");
					zmin = ConfigManager.getIntFromConfig(file, "Magic_Tower." + s + ".Zmin");
					zmax = ConfigManager.getIntFromConfig(file, "Magic_Tower." + s + ".Zmax");
				} catch (EmptyIntException e) {
					e.printStackTrace();
				}
				new Magic_Tower(name,world,xmin,xmax,zmin,zmax,alli);
			}
		}
		if(file.contains("Mine")){
			for(String s : file.getConfigurationSection("Mine").getKeys(false)){
				String name = s;
				Alliance alli = null;
				if(file.contains("Mine." + s + ".Owner")){
					try {
						alli = AllianceManager.getAlliance(ConfigManager.getStringFromConfig(file, "Mine." + s + ".Owner"));
					} catch (EmptyStringException e) {
						e.printStackTrace();
					}
				}
				World world = null;
				try {
					world = Bukkit.getWorld(ConfigManager.getStringFromConfig(file, "Mine." + s + ".World"));
				} catch (EmptyStringException e) {
					e.printStackTrace();
				}
				int xmin = 0;
				int xmax = 0;
				int zmin = 0;
				int zmax = 0;
				try {
					xmin = ConfigManager.getIntFromConfig(file, "Mine." + s + ".Xmin");
					xmax = ConfigManager.getIntFromConfig(file, "Mine." + s + ".Xmax");
					zmin = ConfigManager.getIntFromConfig(file, "Mine." + s + ".Zmin");
					zmax = ConfigManager.getIntFromConfig(file, "Mine." + s + ".Zmax");
				} catch (EmptyIntException e) {
					e.printStackTrace();
				}
				new Mine(name,world,xmin,xmax,zmin,zmax,alli);
			}
		}
		if(file.contains("Mob_Farm")){
			for(String s : file.getConfigurationSection("Mob_Farm").getKeys(false)){
				String name = s;
				Alliance alli = null;
				if(file.contains("Mob_Farm." + s + ".Owner")){
					try {
						alli = AllianceManager.getAlliance(ConfigManager.getStringFromConfig(file, "Mob_Farm." + s + ".Owner"));
					} catch (EmptyStringException e) {
						e.printStackTrace();
					}
				}
				World world = null;
				try {
					world = Bukkit.getWorld(ConfigManager.getStringFromConfig(file, "Mob_Farm." + s + ".World"));
				} catch (EmptyStringException e) {
					e.printStackTrace();
				}
				int xmin = 0;
				int xmax = 0;
				int zmin = 0;
				int zmax = 0;
				try {
					xmin = ConfigManager.getIntFromConfig(file, "Mob_Farm." + s + ".Xmin");
					xmax = ConfigManager.getIntFromConfig(file, "Mob_Farm." + s + ".Xmax");
					zmin = ConfigManager.getIntFromConfig(file, "Mob_Farm." + s + ".Zmin");
					zmax = ConfigManager.getIntFromConfig(file, "Mob_Farm." + s + ".Zmax");
				} catch (EmptyIntException e) {
					e.printStackTrace();
				}
				new Mob_Farm(name,world,xmin,xmax,zmin,zmax,alli);
			}
		}
	}
	
	
	public static void saveOutpost(){
		FileConfiguration file = Main.outpostConfig;
		for(Blacksmith f : Blacksmith.blacksmiths){
			if(f.owner != null){
				file.set("Blacksmith." + f.name + ".Owner", f.owner);
			}
			file.set("Blacksmith." + f.name + ".World", f.world.getName());
			file.set("Blacksmith." + f.name + ".Xmin", f.xmin);
			file.set("Blacksmith." + f.name + ".Xmax", f.xmax);
			file.set("Blacksmith." + f.name + ".Zmin", f.zmin);
			file.set("Blacksmith." + f.name + ".Zmax", f.zmax);
		}
		for(Dock f : Dock.docks){
			if(f.owner != null){
				file.set("Dock." + f.name + ".Owner", f.owner);
			}
			file.set("Dock." + f.name + ".World", f.world.getName());
			file.set("Dock." + f.name + ".Xmin", f.xmin);
			file.set("Dock." + f.name + ".Xmax", f.xmax);
			file.set("Dock." + f.name + ".Zmin", f.zmin);
			file.set("Dock." + f.name + ".Zmax", f.zmax);
		}
		for(Farm f : Farm.farms){
			if(f.owner != null){
				file.set("Farm." + f.name + ".Owner", f.owner);
			}
			file.set("Farm." + f.name + ".World", f.world.getName());
			file.set("Farm." + f.name + ".Xmin", f.xmin);
			file.set("Farm." + f.name + ".Xmax", f.xmax);
			file.set("Farm." + f.name + ".Zmin", f.zmin);
			file.set("Farm." + f.name + ".Zmax", f.zmax);
		}
		for(God f : God.gods){
			if(f.owner != null){
				file.set("God." + f.name + ".Owner", f.owner);
			}
			file.set("God." + f.name + ".World", f.world.getName());
			file.set("God." + f.name + ".Xmin", f.xmin);
			file.set("God." + f.name + ".Xmax", f.xmax);
			file.set("God." + f.name + ".Zmin", f.zmin);
			file.set("God." + f.name + ".Zmax", f.zmax);
		}
		for(Magic_Tower f : Magic_Tower.magic_towers){
			if(f.owner != null){
				file.set("Magic_Tower." + f.name + ".Owner", f.owner);
			}
			file.set("Magic_Tower." + f.name + ".World", f.world.getName());
			file.set("Magic_Tower." + f.name + ".Xmin", f.xmin);
			file.set("Magic_Tower" + f.name + ".Xmax", f.xmax);
			file.set("Magic_Tower." + f.name + ".Zmin", f.zmin);
			file.set("Magic_Tower." + f.name + ".Zmax", f.zmax);
		}
		for(Mine f : Mine.mines){
			if(f.owner != null){
				file.set("Mine." + f.name + ".Owner", f.owner);
			}
			file.set("Mine." + f.name + ".World", f.world.getName());
			file.set("Mine." + f.name + ".Xmin", f.xmin);
			file.set("Mine." + f.name + ".Xmax", f.xmax);
			file.set("Mine." + f.name + ".Zmin", f.zmin);
			file.set("Mine." + f.name + ".Zmax", f.zmax);
		}
		for(Mob_Farm f : Mob_Farm.mob_farms){
			if(f.owner != null){
				file.set("Mob_Farm." + f.name + ".Owner", f.owner);
			}
			file.set("Mob_Farm." + f.name + ".World", f.world.getName());
			file.set("Mob_Farm." + f.name + ".Xmin", f.xmin);
			file.set("Mob_Farm." + f.name + ".Xmax", f.xmax);
			file.set("Mob_Farm." + f.name + ".Zmin", f.zmin);
			file.set("Mob_Farm." + f.name + ".Zmax", f.zmax);
		}
	}
	
	
}
