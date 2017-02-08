package me.Haeseke1.Alliances.Buildings.Builder;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Buildings.BuildingType;
import me.Haeseke1.Alliances.Buildings.DataManager;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

public class BuilderManager {
	
	
	public static void registerBuilders(){
		Material[][][] material = null;
		Byte[][][] data = null;
		int height = 0;
		Location mainBlock = null;
		String name = null;
		BuildingType type = null;
		File file = new File(Main.plugin.getDataFolder() + "/Building");
		if(!file.exists()){
			return;
		}
		for(File f : file.listFiles()){
			if(f.getName().contains("MAT.dat")){
				name = f.getName().replace("MAT.dat", "");
				material = (Material[][][]) DataManager.Load(new File(Main.plugin.getDataFolder() + "/Building", name + "MAT.dat"));
				data = (Byte[][][]) DataManager.Load(new File(Main.plugin.getDataFolder() + "/Building", name + "DAT.dat"));
				String[] s = ((String) DataManager.Load(new File(Main.plugin.getDataFolder() + "/Building", name + "MAIN.dat"))).split(",");
				mainBlock = new Location(Bukkit.getWorld(s[0]), Integer.parseInt(s[1]),Integer.parseInt(s[2]) ,Integer.parseInt(s[3]));
				height = (int) DataManager.Load(new File(Main.plugin.getDataFolder() + "/Building", name + "HEIG.dat"));
				type = (BuildingType) DataManager.Load(new File(Main.plugin.getDataFolder() + "/Building", name + "TYPE.dat"));
				new Builder(name,material,data,height,mainBlock,type);
			}
		}
	}
	
	public static void saveBuilders(){
		for(Builder builder : Builder.builders){
			builder.saveData();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static boolean createBuilder(Player player, String name, String buildingtype){
		if(!regionSelect.leftClick.containsKey(player) || !regionSelect.rightClick.containsKey(player)){
			MessageManager.sendMessage(player, ChatColor.RED + "You must select a region first!");
			return false;
		}
		if(nameExist(name)){
			MessageManager.sendMessage(player, ChatColor.RED + "This name is already used for another building!");
			return false;
		}
		BuildingType type = BuildingType.getType(buildingtype);
		if(type == null){
			MessageManager.sendMessage(player, ChatColor.RED + "This building type does not exist!");
			return false;
		}
		Location loc1 = regionSelect.leftClick.get(player);
		Location loc2 = regionSelect.rightClick.get(player);
		int xmin = 0;
		int zmin = 0;
		int xmax = 0;
		int zmax = 0;
		int ymin = 0;
		int ymax = 0;
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
		if(loc1.getBlockY() > loc2.getBlockY()){
			ymin = loc2.getBlockY();
			ymax = loc1.getBlockY();
		}else{
			ymin = loc1.getBlockY();
			ymax = loc2.getBlockY();
		}
		if(xmax - xmin != 15 || zmax - zmin != 15){
			MessageManager.sendMessage(player, ChatColor.RED + "Building must be 16x16 (size of a chunk)!");
			return false;
		}
		int obsidian = 0;
		Location mainBlock = null;
		Material[][][] material = new Material[16][ymax - ymin + 1][16];
		Byte[][][] data = new Byte[16][ymax - ymin + 1][16];
		for(int x = xmin; xmax >= x; x++){
			for(int y = ymin; ymax >= y; y++){
				for(int z = zmin; zmax >= z; z++){
					Block b = new Location(loc1.getWorld(),x,y,z).getBlock();
					material[x - xmin][y - ymin][z - zmin] = b.getType();
					data[x - xmin][y - ymin][z - zmin] = b.getData();
					if(b.getType().equals(Material.OBSIDIAN)){
						obsidian++;
						mainBlock = b.getLocation();
					}
				}
			}
		}
		if(obsidian > 1){
			MessageManager.sendMessage(player, ChatColor.RED + "There can only be 1 obsidian in the building! (main block)");
			return false;
		}
		if(obsidian == 0){
			MessageManager.sendMessage(player, ChatColor.RED + "There must be 1 obisdian block in the building! (main block)");
			return false;
		}
		mainBlock.setX(mainBlock.getX() - xmin);
		mainBlock.setY(mainBlock.getY() - ymin);
		mainBlock.setZ(mainBlock.getZ() - zmin);
		
		
		new Builder(name, material, data,ymax - ymin, mainBlock, type);
		return true;
	}
	
	
	
	
	public static boolean nameExist(String name){
		for(Builder b : Builder.builders){
			if(b.name.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	
	public static Builder getBuilder(String name){
		for(Builder b : Builder.builders){
			if(b.name.equalsIgnoreCase(name)){
				return b;
			}
		}
		return null;
	}
	
}
